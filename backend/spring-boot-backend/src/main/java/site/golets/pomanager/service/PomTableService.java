package site.golets.pomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.golets.pomanager.model.PomTable;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PomTableService {

    @Value("${POMANAGER_SCAN_ROOT_PATH:}")
    private String scanRootPath;

    @Autowired
    private PomReaderService pomReaderService;

    public PomTable getPomTable() {
        PomTable pomTable = new PomTable();

        Map<String, Map<String, String>> packagesToProperties = pomReaderService.scanFileSystem(scanRootPath).stream()
                .peek(m -> log.info("Parsing model: {}", m))
                .collect(Collectors.toMap(m -> m.getArtifactId() + ":" + m.getVersion(), this::getPropertiesMap));

        for (Map.Entry<String, Map<String, String>> packageToProperties : packagesToProperties.entrySet()) {
            for (Map.Entry<String, String> propertyToVersion : packageToProperties.getValue().entrySet()) {
                pomTable.addPomTableRecord(packageToProperties.getKey(), propertyToVersion.getKey(), propertyToVersion.getValue());
            }
        }

        return pomTable;
    }

    private Map<String, String> getPropertiesMap(Model model) {
        return model.getProperties().stringPropertyNames().stream()
                .filter(n -> n.endsWith(".version"))
                .collect(Collectors.toMap(
                        Function.identity(),
                        n -> model.getProperties().getProperty(n))
                );
    }

}
