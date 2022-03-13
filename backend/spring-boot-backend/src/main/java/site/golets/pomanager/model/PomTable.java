package site.golets.pomanager.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class PomTable {

    private Map<String, Map<String, PomPropertyValue>> pomTableMap = new LinkedHashMap<>();
    private Map<String, PomPackage> pomPackageNameMap = new LinkedHashMap<>();
    private Map<String, PomProperty> pomPropertyNameMap = new LinkedHashMap<>();

    public void addPomTableRecord(String pomPackage, String pomProperty, String pomValue) {
        addPomTableRecord(new PomPackage().setName(pomPackage), new PomProperty().setName(pomProperty), new PomPropertyValue().setValue(pomValue));
    }

    public void addPomTableRecord(PomPackage pomPackage, PomProperty pomProperty, PomPropertyValue pomPropertyValue) {
        Map<String, PomPropertyValue> pomPropertyMap = pomTableMap.computeIfAbsent(pomPackage.getName(), k -> new LinkedHashMap<>());
        pomPropertyMap.put(pomProperty.getName(), pomPropertyValue);
        pomPropertyNameMap.put(pomProperty.getName(), pomProperty);
        pomPackageNameMap.put(pomPackage.getName(), pomPackage);
    }


}
