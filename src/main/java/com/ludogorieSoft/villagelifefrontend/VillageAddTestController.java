package com.ludogorieSoft.villagelifefrontend;

import com.ludogorieSoft.villagelifefrontend.models.Administrator;
import com.ludogorieSoft.villagelifefrontend.models.Population;
import com.ludogorieSoft.villagelifefrontend.models.Village;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ludogorieSoft.villagelifefrontend.enums.Children.Over50Years;
import static com.ludogorieSoft.villagelifefrontend.enums.Foreigners.IDontKnow;
import static com.ludogorieSoft.villagelifefrontend.enums.NumberOfPopulation.From11To50People;
import static com.ludogorieSoft.villagelifefrontend.enums.Residents.From6To10Percent;

@Controller
@RequestMapping("village")
public class VillageAddTestController {
    private final Population population=new Population(1L,From11To50People,From6To10Percent,Over50Years,IDontKnow);
    private final Administrator administrator=new Administrator(1L,"TestAdmin","mail@bg.com","user","pass","80991247128",LocalDateTime.of(2,1,5,1,1,31));


    @GetMapping("/add")
    //http://localhost:8087/village/add
    public String addVillage(Model model) {
        model.addAttribute("village",new Village());
        return "Village";
    }

    @GetMapping ("/show")
    //http://localhost:8087/village/show
    public String stringVillage( Model model) {
        Village village=new Village(2L,"SELO1",population, LocalDateTime.of(2,1,5,1,1,31),true,administrator,LocalDateTime.of(1,2,3,4,5,6,7));
        Village village2=new Village(2L,"SELO2",population, LocalDateTime.of(2,1,5,1,1,31),true,administrator,LocalDateTime.of(1,2,3,4,5,6,7));

        List<Village> villageList=new ArrayList<>();
        villageList.add(village);
        villageList.add(village2);
        model.addAttribute("villages",villageList);
        return "Show";
    }


}
