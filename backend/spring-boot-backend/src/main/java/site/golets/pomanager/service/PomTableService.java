package site.golets.pomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.golets.pomanager.model.PomPackage;
import site.golets.pomanager.model.PomTable;
import site.golets.pomanager.service.impl.FileSystemPomReaderServiceImpl;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
@Slf4j
public class PomTableService {

    @Value("${POMANAGER_SCAN_ROOT_PATH:}")
    private String scanRootPath;

    @Autowired
    private FileSystemPomReaderServiceImpl fileSystemPomReaderServiceImpl;

    @Autowired
    public PomPackageFactory pomPackageFactory;

    public PomTable getPomTable() {
        PomTable pomTable = new PomTable();

        Map<Model, Map<String, String>> packagesToProperties = fileSystemPomReaderServiceImpl.scan(scanRootPath).stream()
                .peek(m -> log.info("Parsing model: {}", m))
                .collect(Collectors.toMap(identity(), this::getPropertiesMap));

        for (Map.Entry<Model, Map<String, String>> packageToProperties : packagesToProperties.entrySet()) {
            for (Map.Entry<String, String> propertyToVersion : packageToProperties.getValue().entrySet()) {
                PomPackage pomPackage = pomPackageFactory.create(packageToProperties.getKey());
                pomTable.addPomTableRecord(pomPackage, propertyToVersion.getKey(), propertyToVersion.getValue());
            }
        }

        return pomTable;
    }

    private Map<String, String> getPropertiesMap(Model model) {
        return model.getProperties().stringPropertyNames().stream()
                .filter(n -> n.endsWith(".version"))
                .collect(Collectors.toMap(
                        identity(),
                        n -> model.getProperties().getProperty(n))
                );
    }

}
