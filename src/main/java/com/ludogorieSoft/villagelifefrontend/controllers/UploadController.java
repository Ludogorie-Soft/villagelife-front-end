package com.ludogorieSoft.villagelifefrontend.controllers;

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

import javax.transaction.Transactional;
import java.io.IOException;
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

    private static final String UPLOAD_VIEW = "upload";
    private static final String UPLOAD_SUCCESS = "uploadSuccess";
    private static final String UPLOAD_ERROR = "uploadError";

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);



    @GetMapping()
    public String uploadForm(Model model) {
        model.addAttribute(UPLOAD_SUCCESS, false);
        model.addAttribute(UPLOAD_ERROR, false);
        model.addAttribute("subscription", new SubscriptionDTO());
        return UPLOAD_VIEW;
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

            for (int rowIndex = 1; rowIndex <= totalVillages; rowIndex++) {

                Row row = sheet.getRow(rowIndex);
                Cell cell = row.getCell(2);

                String key = getExistingVillageKey(cell);
                if (key == null) continue;

                String[] parts;

                VillageDTO village = new VillageDTO();

                setVillageIdBasedOnKeyOrCreateNewVillage(existingVillagesMap, key, village);

                int lastCellNum = row.getLastCellNum();

                VillageAnswerQuestionDTO villageAnswerQuestion = new VillageAnswerQuestionDTO();
                PopulationDTO population = new PopulationDTO();
                PopulatedAssertionDTO populatedAssertion;
                VillagePopulationAssertionDTO villagePopulationAssertion = new VillagePopulationAssertionDTO();


                for (int i = 0; i < lastCellNum; i++) {
                    cell = row.getCell(i);
                    if (cell != null) {
                        String value = cell.getStringCellValue();
                        village.setId(village.getId());
                        if (i == 0) {
                            village.setDateUpload(null);
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
                                    Long groundCategoryId = groundCategories.get(j).getId();


                                    VillageGroundCategoryDTO newVillageGroundCategory = new VillageGroundCategoryDTO();
                                    newVillageGroundCategory.setVillageId(village.getId());
                                    newVillageGroundCategory.setGroundCategoryId(groundCategoryId);
                                    newVillageGroundCategory.setStatus(true);

                                    if (!villageGroundCategoryClient.isVillageExists(village.getId())) {
                                        villageGroundCategoryClient.createVillageGroundCategories(newVillageGroundCategory);
                                    } else {
                                        villageGroundCategoryClient.updateVillageGroundCategory(village.getId(), newVillageGroundCategory);
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
                            Long newPopulationID;

                            newPopulationID = getNewPopulationID(village);

                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueNumberOfPopulation = valueCell.getStringCellValue().trim();
                            boolean populationFound = false;

                            for (NumberOfPopulation numberOfPopulation : NumberOfPopulation.values()) {
                                if (numberOfPopulation.getName().equalsIgnoreCase(valueNumberOfPopulation)) {
                                    population.setNumberOfPopulation(numberOfPopulation);
                                    String[] populationRange = valueNumberOfPopulation.split(" - ");
                                    if (populationRange.length >= 2) {
                                        String numberString = populationRange[1].trim().split(" ")[0];

                                        populationFound = isPopulationFound(village, populationFound, numberString);
                                    }
                                    break;
                                }
                            }
                            if (!populationFound) {
                                if (valueNumberOfPopulation.equalsIgnoreCase("над 2000 човека")) {
//                                    village.setPopulationCount(2000);
                                } else if (valueNumberOfPopulation.equalsIgnoreCase("до 10 човека")) {
//                                    village.setPopulationCount(10);
                                }
                            }
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

                            populationClient.updatePopulation(newPopulationID, population);
                            population.setId(newPopulationID);
//                            village.setPopulationDTO(population);

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
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }
                        }
                    }
                }

                existingVillagesMap.put(key, village);
                villageClient.updateVillage(village.getId(), village);
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

    private static boolean isPopulationFound(VillageDTO village, boolean populationFound, String numberString) {
        try {
            int populationCount = Integer.parseInt(numberString);
//            village.setPopulationCount(populationCount);
            populationFound = true;
        } catch (NumberFormatException e) {
            logger.error("Невалиден брой жители: ", e);
        }
        return populationFound;
    }

    private Long getNewPopulationID(VillageDTO village) {
        Long newPopulationID;
        try {
            populationClient.findPopulationByVillageNameAndRegion(village.getName(), village.getRegion());
            newPopulationID = village.getId();

        } catch (FeignException.BadRequest e) {
            newPopulationID = populationClient.createPopulationWhitNullValues();
        }
        return newPopulationID;
    }


    private void setVillageIdBasedOnKeyOrCreateNewVillage(Map<String, VillageDTO> existingVillagesMap, String key, VillageDTO village) {
        if (existingVillagesMap.containsKey(key)) {
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

