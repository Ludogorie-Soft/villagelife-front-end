package com.ludogoriesoft.villagelifefrontend.advanced;

import com.ludogoriesoft.villagelifefrontend.enums.Children;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {
    private List<String> selectedObjects;
    private List<String> selectedLivingConditions;
    private Children selectedChildrenCountResult;

}
