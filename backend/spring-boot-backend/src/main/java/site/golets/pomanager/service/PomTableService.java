package site.golets.pomanager.service;

import org.springframework.stereotype.Service;
import site.golets.pomanager.model.PomTable;

@Service
public class PomTableService {

    public PomTable getPomTable(){
        PomTable pomTable =  new PomTable();

        pomTable.addPomTableRecord("package-identifier-one", "package-version-one", "1.5.11-SNAPSHOT");
        pomTable.addPomTableRecord("package-identifier-one", "package-version-two", "1.5.12-SNAPSHOT");
        pomTable.addPomTableRecord("package-identifier-one", "package-version-three", "1.5.13-SNAPSHOT");

        pomTable.addPomTableRecord("package-identifier-two", "package-version-one", "1.6.11-SNAPSHOT");
        pomTable.addPomTableRecord("package-identifier-two", "package-version-two", "1.6.12-SNAPSHOT");
        pomTable.addPomTableRecord("package-identifier-two", "package-version-three", "1.6.13-SNAPSHOT");

        pomTable.addPomTableRecord("package-identifier-three", "package-version-one", "1.6.11-SNAPSHOT");
        pomTable.addPomTableRecord("package-identifier-three", "package-version-two", "1.6.12-SNAPSHOT");
        pomTable.addPomTableRecord("package-identifier-three", "package-version-three", "1.6.13-SNAPSHOT");

        return pomTable;

    }

}
