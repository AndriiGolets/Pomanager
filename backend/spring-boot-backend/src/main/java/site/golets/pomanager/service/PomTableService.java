package site.golets.pomanager.service;

import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.golets.pomanager.model.PomTable;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PomTableService {

    @Autowired
    private PomReaderService pomReaderService;

    public PomTable getPomTable() {
        PomTable pomTable = new PomTable();

        Map<String, Map<String, String>> packagesToProperties = pomReaderService.scanFileSystem("C:\\Users\\yaroslav\\IdeaProjects\\VersionHell").stream()
                .collect(Collectors.toMap(
                        Model::getArtifactId,
                        m -> m.getProperties().stringPropertyNames().stream()
                                .filter(n -> n.endsWith(".version"))
                                .collect(Collectors.toMap(Function.identity(), n -> m.getProperties().getProperty(n)))
                ));

        for (Map.Entry<String, Map<String, String>> packageToProperties : packagesToProperties.entrySet()) {
            for (Map.Entry<String, String> propertyToVersion : packageToProperties.getValue().entrySet()) {
                pomTable.addPomTableRecord(packageToProperties.getKey(), propertyToVersion.getKey(), propertyToVersion.getValue());
            }
        }

        return pomTable;
    }

}
