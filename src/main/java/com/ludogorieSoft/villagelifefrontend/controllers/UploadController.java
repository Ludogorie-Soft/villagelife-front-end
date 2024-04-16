package com.ludogorieSoft.villagelifefrontend.controllers;

import com.ludogorieSoft.villagelifefrontend.auth.AuthClient;
import com.ludogorieSoft.villagelifefrontend.config.*;
import com.ludogorieSoft.villagelifefrontend.dtos.*;
import com.ludogorieSoft.villagelifefrontend.enums.*;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/uploadFile")
@AllArgsConstructor
public class UploadController {


    private final VillageClient villageClient;
    private final PopulationClient populationClient;
    private final RegionClient regionClient;
    private final ObjectVillageClient objectVillageClient;
    private final VillageLivingConditionClient villageLivingConditionClient;
    private final GroundCategoryClient groundCategoryClient;
    private final VillageGroundCategoryClient villageGroundCategoryClient;
    private final QuestionClient questionClient;
    private final VillageAnswerQuestionClient villageAnswerQuestionClient;
    private final EthnicityClient ethnicityClient;
    private final VillageEthnicityClient villageEthnicityClient;
    private final PopulatedAssertionClient populatedAssertionClient;
    private final VillagePopulationAssertionClient villagePopulationAssertionClient;
    private final AuthClient authClient;
    private static final String UPLOAD_VIEW = "upload";
    private static final String UPLOAD_SUCCESS = "uploadSuccess";
    private static final String UPLOAD_ERROR = "uploadError";

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);


    @GetMapping()
    public String uploadForm(Model model, HttpSession session) {
        String token = (String) session.getAttribute("admin");
        authClient.authorizeAdminToken("Bearer " + token);
        model.addAttribute(UPLOAD_SUCCESS, false);
        model.addAttribute(UPLOAD_ERROR, false);
        model.addAttribute("subscription", new SubscriptionDTO());
        return UPLOAD_VIEW;
    }

    @GetMapping("/uploadImages")
    public String uploadImages(Model model, HttpSession session) {
        //villageImageClient.uploadImages();
        return "redirect:/uploadFile";
    }

    @PostMapping()
    @Transactional
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                model.addAttribute(UPLOAD_SUCCESS, false);
                model.addAttribute(UPLOAD_ERROR, true);
                return UPLOAD_VIEW;
            }
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            int totalVillages = sheet.getLastRowNum();
            model.addAttribute("totalVillages", totalVillages);
            int uploadedVillagesCount = 0;

            Map<String, VillageDTO> existingVillagesMap = new HashMap<>();
            Map<String, Integer> villageOccurrencesMap = new HashMap<>();

            for (int rowIndex = 1; rowIndex <= totalVillages; rowIndex++) {

                Row row = sheet.getRow(rowIndex);
                Cell cell = row.getCell(2);

                String key = getExistingVillageKey(cell);//this return name + regionName
                if (key == null) continue;

                if (villageOccurrencesMap.containsKey(key)) {
                    int occurrences = villageOccurrencesMap.get(key);
                    villageOccurrencesMap.put(key, occurrences + 1);
                } else {
                    villageOccurrencesMap.put(key, 1);
                }

                String[] parts;

                VillageDTO village = new VillageDTO();

                setVillageIdBasedOnKeyOrCreateNewVillage(existingVillagesMap, key, village);

                int lastCellNum = row.getLastCellNum();

                VillageAnswerQuestionDTO villageAnswerQuestion = new VillageAnswerQuestionDTO();
                PopulatedAssertionDTO populatedAssertion;
                VillagePopulationAssertionDTO villagePopulationAssertion = new VillagePopulationAssertionDTO();


                for (int i = 0; i < lastCellNum; i++) {
                    cell = row.getCell(i);
                    if (cell != null) {
                        String value = cell.getStringCellValue();
                        village.setId(village.getId());
                        village.setDateUpload(LocalDateTime.now());

                        if (i == 0) {
                            LocalDateTime currentDateTime = LocalDateTime.now();
                            village.setDateUpload(currentDateTime);

                            if (villageOccurrencesMap.containsKey(key)) {
                                int occurrences = villageOccurrencesMap.get(key);
                                village.setApprovedResponsesCount(occurrences);
                            }

                        } else if (i == 2) {
                            if (value != null) {
                                String processedVillageName = VillageNameProcessor.processVillageName(value);
                                village.setName(processedVillageName);

                                List<RegionDTO> regionList = regionClient.getAllRegions();
                                boolean foundMatch = false;
                                for (RegionDTO region : regionList) {
                                    if (value.contains(region.getRegionName())) {
                                        village.setRegion(region.getRegionName());
                                        foundMatch = true;
                                        break;
                                    }
                                }
                                if (!foundMatch) {
                                    village.setRegion("Ямбол");
                                }
                            }

                        } else if (i == 3) {
                            long objectAroundVillageID = 1;
                            int innerIndex = i;
                            while (innerIndex <= 16) {
                                Cell valueCell = sheet.getRow(rowIndex).getCell(innerIndex);
                                if (valueCell != null) {
                                    String valueWhile = valueCell.getStringCellValue();
                                    for (Distance distance : Distance.values()) {
                                        if (distance.getName().equalsIgnoreCase(valueWhile)) {
                                            ObjectVillageDTO objectVillage = new ObjectVillageDTO();
                                            objectVillage.setVillageId(village.getId());
                                            objectVillage.setObjectAroundVillageId(objectAroundVillageID);
                                            objectVillage.setDistance(distance);
                                            objectVillage.setStatus(true);
                                            objectVillageClient.createObjectVillage(objectVillage);
                                            break;
                                        }
                                    }
                                }
                                innerIndex++;
                                objectAroundVillageID++;
                            }
                        } else if (i == 18) {
                            int j = 0;
                            int i1 = i;
                            long livingConditionID = 1;
                            while (j <= 7) {
                                Cell valueCell = sheet.getRow(rowIndex).getCell(i1);
                                if (valueCell != null) {
                                    String valueWhile = valueCell.getStringCellValue();
                                    for (Consents consents : Consents.values()) {
                                        if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                            VillageLivingConditionDTO villageLivingCondition = new VillageLivingConditionDTO();
                                            villageLivingCondition.setVillageId(village.getId());
                                            villageLivingCondition.setLivingConditionId(livingConditionID);
                                            villageLivingCondition.setConsents(consents);
                                            villageLivingCondition.setStatus(true);
                                            villageLivingConditionClient.createVillageLivingConditions(villageLivingCondition);
                                            break;
                                        }
                                    }
                                }
                                i1++;
                                j++;
                                livingConditionID++;
                            }
                        } else if (i == 26) {
                            List<GroundCategoryDTO> groundCategories = groundCategoryClient.getAllGroundCategories();
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();

                            for (int j = 0; j < groundCategories.size(); j++) {
                                if (groundCategories.get(j).getGroundCategoryName().equalsIgnoreCase(valueWhile)) {

                                    Long villageId = village.getId();
                                    Long groundCategoryId = groundCategories.get(j).getId();

                                    if (!villageGroundCategoryClient.checkExistence(villageId, groundCategoryId)) {
                                        VillageGroundCategoryDTO villageGroundCategory = new VillageGroundCategoryDTO();
                                        villageGroundCategory.setVillageId(villageId);
                                        villageGroundCategory.setGroundCategoryId(groundCategoryId);
                                        villageGroundCategory.setStatus(true);
                                        villageGroundCategoryClient.createVillageGroundCategories(villageGroundCategory);
                                    }
                                    break;
                                }
                            }

                        } else if (i == 27) {
                            villageAnswerQuestion.setVillageId(village.getId());
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            villageAnswerQuestion.setAnswer(valueWhile);
                            villageAnswerQuestion.setQuestionId(questionClient.getQuestionById(3L).getId());
                            villageAnswerQuestion.setStatus(true);
                            villageAnswerQuestionClient.createVillageAnswerQuestion(villageAnswerQuestion);

                        } else if (i == 28) {
                            for (int k = 0; k < 5; k++) {
                                Cell valueCell = sheet.getRow(rowIndex).getCell(i + k);
                                if (valueCell != null) {
                                    String valueWhile = valueCell.getStringCellValue();
                                    for (Consents consents : Consents.values()) {
                                        if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                            VillageLivingConditionDTO villageLivingCondition = new VillageLivingConditionDTO();
                                            villageLivingCondition.setVillageId(village.getId());
                                            villageLivingCondition.setLivingConditionId((long) (9 + k));
                                            villageLivingCondition.setConsents(consents);
                                            villageLivingCondition.setStatus(true);
                                            villageLivingConditionClient.createVillageLivingConditions(villageLivingCondition);
                                            break;
                                        }
                                    }
                                }
                            }

                        } else if (i == 33) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueNumberOfPopulation = valueCell.getStringCellValue().trim();
                            PopulationDTO population = new PopulationDTO();
                            population.setVillageId(village.getId());
                            population.setStatus(true);
                            boolean populationFound = false;

                            populationFound = isPopulationFound(population, populationFound, valueNumberOfPopulation);
                            i++;
                            String valueResident = sheet.getRow(rowIndex).getCell(i).getStringCellValue();
                            for (Residents residents : Residents.values()) {
                                if (residents.getName().equalsIgnoreCase(valueResident)) {
                                    population.setResidents(residents);
                                    break;
                                }
                            }
                            i++;
                            String valueChildren = sheet.getRow(rowIndex).getCell(i).getStringCellValue();
                            for (Children children : Children.values()) {
                                if (children.getName().equalsIgnoreCase(valueChildren)) {
                                    population.setChildren(children);
                                    break;
                                }
                            }
                            i++;
                            String valueForeigners = sheet.getRow(rowIndex).getCell(i).getStringCellValue();
                            for (Foreigners foreigners : Foreigners.values()) {
                                if (foreigners.getName().equalsIgnoreCase(valueForeigners)) {
                                    population.setForeigners(foreigners);
                                    break;
                                }
                            }

                            populationClient.createPopulation(population);

                        } else if (i == 37) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueNumberOfPopulation = valueCell.getStringCellValue();
                            List<EthnicityDTO> ethnicityDTOList = ethnicityClient.getAllEthnicities();
                            parts = valueNumberOfPopulation.split("\\s+");
                            for (String part : parts) {
                                for (EthnicityDTO ethnicityDTO : ethnicityDTOList) {
                                    if (ethnicityDTO.getEthnicityName().equalsIgnoreCase(part)) {
                                        Long villageId = village.getId();
                                        Long ethnicityId = ethnicityDTO.getId();

                                        if (!villageEthnicityClient.checkExistence(villageId, ethnicityId)) {
                                            EthnicityVillageDTO ethnicityVillage = new EthnicityVillageDTO();
                                            ethnicityVillage.setVillageId(villageId);
                                            ethnicityVillage.setEthnicityId(ethnicityId);
                                            ethnicityVillage.setStatus(true);
                                            villageEthnicityClient.createEthnicityVillage(ethnicityVillage);
                                        }
                                    }
                                }
                            }
                        } else if (i == 38) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            populatedAssertion = populatedAssertionClient.getPopulatedAssertionById(1L);
                            villagePopulationAssertion.setPopulatedAssertionId(populatedAssertion.getId());
                            villagePopulationAssertion.setVillageId(village.getId());
                            for (Consents consents : Consents.values()) {
                                if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                    villagePopulationAssertion.setAnswer(consents);
                                    villagePopulationAssertion.setStatus(true);
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }
                        } else if (i == 42) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            populatedAssertion = populatedAssertionClient.getPopulatedAssertionById(2L);
                            villagePopulationAssertion.setPopulatedAssertionId(populatedAssertion.getId());
                            villagePopulationAssertion.setVillageId(village.getId());
                            for (Consents consents : Consents.values()) {
                                if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                    villagePopulationAssertion.setAnswer(consents);
                                    villagePopulationAssertion.setStatus(true);
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }

                        } else if (i == 43) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            populatedAssertion = populatedAssertionClient.getPopulatedAssertionById(3L);
                            villagePopulationAssertion.setPopulatedAssertionId(populatedAssertion.getId());
                            villagePopulationAssertion.setVillageId(village.getId());
                            for (Consents consents : Consents.values()) {
                                if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                    villagePopulationAssertion.setAnswer(consents);
                                    villagePopulationAssertion.setStatus(true);
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }
                        } else if (i == 45) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            populatedAssertion = populatedAssertionClient.getPopulatedAssertionById(4L);
                            villagePopulationAssertion.setPopulatedAssertionId(populatedAssertion.getId());
                            villagePopulationAssertion.setVillageId(village.getId());
                            for (Consents consents : Consents.values()) {
                                if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                    villagePopulationAssertion.setAnswer(consents);
                                    villagePopulationAssertion.setStatus(true);
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }
                        } else if (i == 46) {
                            villageAnswerQuestion.setVillageId(village.getId());
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            villageAnswerQuestion.setAnswer(valueWhile);
                            villageAnswerQuestion.setQuestionId(questionClient.getQuestionById(6L).getId());
                            villageAnswerQuestion.setStatus(true);
                            villageAnswerQuestionClient.createVillageAnswerQuestion(villageAnswerQuestion);

                        } else if (i == 47) {
                            villageAnswerQuestion.setVillageId(village.getId());
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            villageAnswerQuestion.setAnswer(valueWhile);
                            villageAnswerQuestion.setQuestionId(questionClient.getQuestionById(7L).getId());
                            villageAnswerQuestion.setStatus(true);
                            villageAnswerQuestionClient.createVillageAnswerQuestion(villageAnswerQuestion);

                        }
                    }
                }

                existingVillagesMap.put(key, village);
                villageClient.updateVillage(village.getId(), village);
                villageClient.increaseApprovedResponsesCount(village.getId());

                uploadedVillagesCount++;
                model.addAttribute("uploadedVillagesCount", uploadedVillagesCount);
                model.addAttribute("totalUniqueVillages", existingVillagesMap.size());

            }
            model.addAttribute("addedVillageNumber", totalVillages);
            model.addAttribute(UPLOAD_SUCCESS, true);
            model.addAttribute(UPLOAD_ERROR, false);

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute(UPLOAD_SUCCESS, false);
            model.addAttribute(UPLOAD_ERROR, true);

        }

        model.addAttribute("subscription", new SubscriptionDTO());
        return UPLOAD_VIEW;
    }

    private static boolean isPopulationFound(PopulationDTO population, boolean populationFound, String numberString) {
        try {
            int populationCount = Integer.parseInt(numberString);
            population.setPopulationCount(populationCount);
            populationFound = true;
        } catch (NumberFormatException e) {
            logger.error("Невалиден брой жители: ", e);
        }
        return populationFound;
    }


    private void setVillageIdBasedOnKeyOrCreateNewVillage(Map<String, VillageDTO> existingVillagesMap, String key, VillageDTO village) {
        VillageDTO villageDTO = villageClient.findVillageByNameAndRegion(key);
        if (villageDTO != null) {
            village.setId(villageDTO.getId());
        } else if (existingVillagesMap.containsKey(key)) {
            VillageDTO existingVillage = existingVillagesMap.get(key);
            Long existingVillageId = existingVillage.getId();
            village.setId(existingVillageId);

        } else {
            Long newVillageID = villageClient.createVillageWithNullValues();

            village.setId(newVillageID);
        }
    }


    private static String getExistingVillageKey(Cell cell) {
        if (cell == null) {
            return null;
        }

        String combinedValue = cell.getStringCellValue();

        String[] parts = combinedValue.split(",\\s*");

        String name = parts[0].trim();

        String regionName;
        if (parts.length > 1) {
            regionName = parts[parts.length - 1].trim();
            regionName = regionName.replaceAll("^(?:обл\\.?|област)\\s+", "");
        } else {
            name = parts[0].trim();
            regionName = "Ямбол";
        }

        name = VillageNameProcessor.processVillageName(name);
        return name + ", " + regionName;
    }


}

