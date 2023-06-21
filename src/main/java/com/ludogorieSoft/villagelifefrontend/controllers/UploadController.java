package com.ludogoriesoft.villagelifefrontend.controllers;

import com.ludogoriesoft.villagelifefrontend.config.ObjectVillageClient;
import com.ludogoriesoft.villagelifefrontend.config.VillageClient;
import com.ludogoriesoft.villagelifefrontend.dtos.ObjectVillageDTO;
import com.ludogoriesoft.villagelifefrontend.dtos.VillageDTO;
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

@Controller
@RequestMapping("/upload")
@AllArgsConstructor
public class UploadController {
    private final VillageClient villageClient;
    private final ObjectVillageClient objectVillageClient;

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
            int villageNextId = villageClient.getAllVillages().size() + 1;//ИД на следващо село в БД

            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheetAt(0);
            VillageDTO village = new VillageDTO();
            ObjectVillageDTO objectVillage = new ObjectVillageDTO();


            Row row = sheet.getRow(1);
            int lastCellNum = row.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    String value = cell.getStringCellValue();
                    if (i == 0) {
                        village.setDateUpload(null);
                    } else if (i == 2) {
                        if (value!=null){
                            village.setName(value);
                        }
                    } else if (i == 3) {
                        long objectAroundVillageID=1;
                        while (i <= 16) {
                            objectVillage.setVillageId((long) 1);
                            objectVillage.setObjectAroundVillageId(objectAroundVillageID);
                            Cell valueCell = sheet.getRow(1).getCell(i);
                            if (valueCell != null) {
                                String valueWhile = valueCell.getStringCellValue();
                                for (Distance distance : Distance.values()) {
                                    if (distance.getName().equalsIgnoreCase(valueWhile)) {
                                        System.out.println("Value: " + valueWhile);
                                        objectVillage.setDistance(distance);
                                        objectVillageClient.createObjectVillage(objectVillage);
                                        break;
                                    }
                                }
                            }
                            i++;
                            objectAroundVillageID++;
                        }
                    }else if (i==17){
                        model.addAttribute("text", "USPQ BERKI");
                    }
                }
            }
            model.addAttribute("objectVillage", objectVillage);
            model.addAttribute("village", village);
            model.addAttribute("uploadSuccess", true);
            model.addAttribute("uploadError", false);
        } catch (
                IOException e) {
            e.printStackTrace();
            model.addAttribute("uploadSuccess", false);
            model.addAttribute("uploadError", true);
        }

        return "upload";
    }
}
