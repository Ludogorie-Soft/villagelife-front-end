package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.*;
import com.ludogoriesoft.villagelifefrontend.dtos.*;
import com.ludogoriesoft.villagelifefrontend.enums.Consents;
import com.ludogoriesoft.villagelifefrontend.enums.Distance;
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
@RequestMapping("/upload")
@AllArgsConstructor
public class UploadController {
    private final VillageClient villageClient;
    private final ObjectVillageClient objectVillageClient;
    private final VillageLivingConditionClient villageLivingConditionClient;
    private final GroundCategoryClient groundCategoryClient;
    private final VillageGroundCategoryClient villageGroundCategoryClient;

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

            Row row = sheet.getRow(1);
            int lastCellNum = row.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    String value = cell.getStringCellValue();
                    if (i == 0) {
                        village.setDateUpload(null);
                    } else if (i == 2) {
                        if (value != null) {
                            village.setName(value);
                        }
                    } else if (i==3) {
                        long objectAroundVillageID = 1;
                        int innerIndex = i;
                        while (innerIndex <= 16) {
                            objectVillage.setVillageId(1L);
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
                        int i1=i;
                        long livingConditionID = 1;
                        while (j <= 7) {
                            villageLivingCondition.setVillageId(1L);
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
                        villageGroundCategory.setVillageId(1L);
                        List<GroundCategoryDTO> groundCategories = groundCategoryClient.getAllGroundCategories();
                        if (!groundCategories.isEmpty()) {
                            Cell valueCell = sheet.getRow(1).getCell(i);
                            String valueWhile = valueCell.getStringCellValue();
                            for (int j = 0; j < groundCategories.size(); j++) {
                                if (groundCategories.get(j).getGroundCategoryName().equalsIgnoreCase(valueWhile)) {
                                    villageGroundCategory.setGroundCategoryId((long) (j + 1));
                                    villageGroundCategoryClient.createVillageGroundCategories(villageGroundCategory);
                                    break;
                                }
                            }
                        }
                    }
                }
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
