package site.golets.pomanager.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class PomTable {

    private Map<PomPackage, Map<PomProperty, PomPropertyValue>> pomTableMap = new LinkedHashMap<>();

}
