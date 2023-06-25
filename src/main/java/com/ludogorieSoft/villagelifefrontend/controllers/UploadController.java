package com.ludogoriesoft.villagelifefrontend.controllers;
import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import com.ludogoriesoft.villagelifefrontend.enums.*;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/uploadController")
@AllArgsConstructor
public class UploadController {
    private final RegionClient regionClient;
    private final VillageClient villageClient;
    private final ObjectVillageClient objectVillageClient;
    private final VillageLivingConditionClient villageLivingConditionClient;
    private final GroundCategoryClient groundCategoryClient;
    private final VillageGroundCategoryClient villageGroundCategoryClient;
    private final VillageAnswerQuestionClient villageAnswerQuestionClient;
    private final QuestionClient questionClient;
    private final PopulationClient populationClient;
    private final EthnicityClient ethnicityClient;
    private final VillageEthnicityClient villageEthnicityClient;
    private final PopulatedAssertionClient populatedAssertionClient;
    private final VillagePopulationAssertionClient villagePopulationAssertionClient;


    @GetMapping
    public String uploadForm(Model model) {
        model.addAttribute("uploadSuccess", false);
        model.addAttribute("uploadError", false);
        return "upload";
    }
    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (!file.getOriginalFilename().endsWith(".xlsx")) {
                model.addAttribute("uploadSuccess", false);
                model.addAttribute("uploadError", true);
                return "upload";
            }
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            VillageDTO village = new VillageDTO();
            ObjectVillageDTO objectVillage = new ObjectVillageDTO();
            VillageLivingConditionDTO villageLivingCondition = new VillageLivingConditionDTO();
            VillageGroundCategoryDTO villageGroundCategory = new VillageGroundCategoryDTO();
            VillageAnswerQuestionDTO villageAnswerQuestion = new VillageAnswerQuestionDTO();
            PopulationDTO population = new PopulationDTO();
            EthnicityVillageDTO ethnicityVillage = new EthnicityVillageDTO();
            PopulatedAssertionDTO populatedAssertion = new PopulatedAssertionDTO();
            VillagePopulationAssertionDTO villagePopulationAssertion = new VillagePopulationAssertionDTO();



            for (int rowIndex = 1; rowIndex <=771 ; rowIndex++) {
                Long newVillageID = villageClient.createVillageWithNullValues();
                Long newPopulationID=populationClient.createPopulationWhitNullValues();
                Row row = sheet.getRow(rowIndex);
                int lastCellNum = row.getLastCellNum();
                for (int i = 0; i < lastCellNum; i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        String value = cell.getStringCellValue();
                        village.setId(newVillageID);
                        if (i == 0) {
                            village.setDateUpload(null);
                        } else if (i == 2) {
                            if (value != null) {
                                village.setName(value);
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
                                objectVillage.setVillageId(newVillageID);
                                objectVillage.setObjectAroundVillageId(objectAroundVillageID);
                                Cell valueCell = sheet.getRow(1).getCell(innerIndex);
                                if (valueCell != null) {
                                    String valueWhile = valueCell.getStringCellValue();
                                    for (Distance distance : Distance.values()) {
                                        if (distance.getName().equalsIgnoreCase(valueWhile)) {
                                            objectVillage.setDistance(distance);
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
                                villageLivingCondition.setVillageId(newVillageID);
                                villageLivingCondition.setLivingConditionId(livingConditionID);
                                Cell valueCell = sheet.getRow(1).getCell(i1);
                                if (valueCell != null) {
                                    String valueWhile = valueCell.getStringCellValue();
                                    for (Consents consents : Consents.values()) {
                                        if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                            villageLivingCondition.setConsents(consents);
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
                            villageGroundCategory.setVillageId(newVillageID);
                            List<GroundCategoryDTO> groundCategories = groundCategoryClient.getAllGroundCategories();
                            if (!groundCategories.isEmpty()) {
                                Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                                String valueWhile = valueCell.getStringCellValue();
                                for (int j = 0; j < groundCategories.size(); j++) {
                                    if (groundCategories.get(j).getGroundCategoryName().equalsIgnoreCase(valueWhile)) {
                                        villageGroundCategory.setGroundCategoryId((long) (j + 1));
                                        villageGroundCategoryClient.createVillageGroundCategories(villageGroundCategory);
                                        break;
                                    }
                                }
                            }
                        } else if (i == 27) {
                            villageAnswerQuestion.setVillageId(newVillageID);
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            villageAnswerQuestion.setAnswer(valueWhile);
                            villageAnswerQuestion.setQuestionId(questionClient.getQuestionById(3L).getId());
                            villageAnswerQuestionClient.createVillageAnswerQuestion(villageAnswerQuestion);

                        } else if (i == 28) {
                            for (int k = 0; k < 5; k++) {
                                villageLivingCondition.setVillageId(newVillageID);
                                villageLivingCondition.setLivingConditionId((long) (9 + k));
                                Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                                if (valueCell != null) {
                                    String valueWhile = valueCell.getStringCellValue();
                                    for (Consents consents : Consents.values()) {
                                        if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                            villageLivingCondition.setConsents(consents);
                                            villageLivingConditionClient.createVillageLivingConditions(villageLivingCondition);
                                            break;
                                        }
                                    }
                                }
                            }
                        } else if (i == 33) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueNumberOfPopulation = valueCell.getStringCellValue().trim();
                            boolean populationFound = false;

                            for (NumberOfPopulation numberOfPopulation : NumberOfPopulation.values()) {
                                if (numberOfPopulation.getName().equalsIgnoreCase(valueNumberOfPopulation)) {
                                    population.setNumberOfPopulation(numberOfPopulation);
                                    String[] populationRange = valueNumberOfPopulation.split(" - ");

                                    if (populationRange.length >= 2) {
                                        String numberString = populationRange[1].trim().split(" ")[0];

                                        try {
                                            int populationCount = Integer.parseInt(numberString);
                                            village.setPopulationCount(populationCount);
                                            populationFound = true;
                                        } catch (NumberFormatException e) {
                                            System.err.println("Невалиден брой жители: " + e);
                                        }
                                    }
                                    break;
                                }
                            }
                            if (!populationFound) {
                                if (valueNumberOfPopulation.equalsIgnoreCase("над 2000 човека")){
                                    village.setPopulationCount(2000);
                                } else if (valueNumberOfPopulation.equalsIgnoreCase("до 10 човека")) {
                                    village.setPopulationCount(10);
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

                           populationClient.updatePopulation(newPopulationID,population);
                            population.setId(newPopulationID);
                            village.setPopulationDTO(population);

                        } else if (i == 37) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueNumberOfPopulation = valueCell.getStringCellValue();
                            List<EthnicityDTO> ethnicityDTOList = ethnicityClient.getAllEthnicities();
                            String[] parts = valueNumberOfPopulation.split("\\s+");
                            for (String part : parts) {
                                for (EthnicityDTO ethnicityDTO : ethnicityDTOList) {
                                    if (ethnicityDTO.getEthnicityName().equalsIgnoreCase(part)) {
                                        ethnicityVillage.setVillageId(newVillageID);
                                        ethnicityVillage.setEthnicityId(ethnicityDTO.getId());
                                        villageEthnicityClient.createEthnicityVillage(ethnicityVillage);
                                    }
                                }
                            }
                        } else if (i == 38) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            populatedAssertion = populatedAssertionClient.getPopulatedAssertionById(1L);
                            villagePopulationAssertion.setPopulatedAssertionId(populatedAssertion.getId());
                            villagePopulationAssertion.setVillageId(newVillageID);
                            for (Consents consents : Consents.values()) {
                                if (consents.getName().equalsIgnoreCase(valueWhile)) {
                                    villagePopulationAssertion.setAnswer(consents);
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }
                        } else if (i == 42) {
                            Cell valueCell = sheet.getRow(rowIndex).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            populatedAssertion = populatedAssertionClient.getPopulatedAssertionById(2L);
                            villagePopulationAssertion.setPopulatedAssertionId(populatedAssertion.getId());
                            villagePopulationAssertion.setVillageId(newVillageID);
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
                            villagePopulationAssertion.setVillageId(newVillageID);
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
                            villagePopulationAssertion.setVillageId(newVillageID);
                            for (Consents consents : Consents.values()) {
                                if (consents.getName().equalsIgnoreCase(valueWhile)) {

                                    villagePopulationAssertion.setAnswer(consents);
                                    villagePopulationAssertionClient.createVillagePopulationAssertion(villagePopulationAssertion);
                                }
                            }
                        }
                    }
                }
                villageClient.updateVillage(newVillageID,village);
            }
            model.addAttribute("objectVillage", objectVillage);
            model.addAttribute("village", village);
            model.addAttribute("uploadSuccess", true);
            model.addAttribute("uploadError", false);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("uploadSuccess", false);
            model.addAttribute("uploadError", true);
        }

        return "upload";
    }


}
