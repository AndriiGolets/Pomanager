package site.golets.pomanager.model;

import lombok.Data;
import org.apache.maven.model.Model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Data
public class PomTable {

    private Map<String, Map<String, PomPropertyValue>> pomTableMap = new TreeMap<>();
    private Map<String, PomPackage> pomPackageNameMap = new TreeMap<>();
    private Map<String, PomProperty> pomPropertyNameMap = new TreeMap<>();

    public void addPomTableRecord(String packageName, String property, String value) {
        addPomTableRecord(new PomPackage().setName(packageName), new PomProperty().setName(property), new PomPropertyValue().setPropertyName(value));
    }

    public void addPomTableRecord(PomPackage pomPackage, String property, String value) {
        addPomTableRecord(pomPackage, new PomProperty().setName(property), new PomPropertyValue().setPropertyName(value));
    }

    public void addPomTableRecord(PomPackage packageName, PomProperty property, PomPropertyValue value) {
        Map<String, PomPropertyValue> pomPropertyMap = pomTableMap.computeIfAbsent(packageName.getName(), k -> new LinkedHashMap<>());
        pomPropertyMap.put(property.getName(), value);
        pomPackageNameMap.put(packageName.getName(), packageName);
        pomPropertyNameMap.put(property.getName(), property);
    }

    public void updateProperty(String packageName, String propertyName, String newValue) {
        Map<String, PomPropertyValue> propertyValueMap = this.pomTableMap.get(packageName);
        propertyValueMap.merge(propertyName, new PomPropertyValue().setPropertyName(newValue),
                (v1, v2) -> v2);
        PomPackage pomPackage = this.pomPackageNameMap.get(packageName);
        Model model = pomPackage.getModel();
        model.getProperties().setProperty(propertyName, newValue);
    }

}
