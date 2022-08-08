package site.golets.pomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.golets.pomanager.dto.PropertyUpdateDto;
import site.golets.pomanager.model.PomPackage;
import site.golets.pomanager.model.PomTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
@Slf4j
public class PomTableService {

    @Value("${POMANAGER_SCAN_ROOT_PATH:}")
    private String scanRootPath;

    @Autowired
    private PomReaderService pomReaderService;

    @Autowired
    public PomPackageFactory pomPackageFactory;

    private final PomTable pomTable = new PomTable();

    public PomTable getPomTable() {
        Map<Model, Map<String, String>> packagesToProperties = pomReaderService.scan(scanRootPath)
                .parallelStream()
                .peek(m -> log.info("Parsing model: {}", m))
                .collect(Collectors.toMap(identity(), this::getPropertiesMap));

        for (Map.Entry<Model, Map<String, String>> packageToProperties : packagesToProperties.entrySet()) {
            PomPackage pomPackage = pomPackageFactory.create(packageToProperties.getKey());
            for (Map.Entry<String, String> propertyToVersion : packageToProperties.getValue().entrySet()) {
                pomTable.addPomTableRecord(pomPackage, propertyToVersion.getKey(), propertyToVersion.getValue());
            }
        }

        return pomTable;
    }

    public List<String> packageNamesToPaths(List<String> packageNames) {
        return packageNames.stream()
                .map(n -> pomTable.getPomPackageNameMap().get(n)
                        .getModel()
                        .getProjectDirectory()
                        .getAbsolutePath())
                .collect(Collectors.toList());
    }

    public void updateProperty(PropertyUpdateDto propertyUpdateDto) {
        pomTable.updateProperty(
                propertyUpdateDto.getPackageName(),
                propertyUpdateDto.getPropertyName(),
                propertyUpdateDto.getNewValue());

        PomPackage pomPackage = pomTable.getPomPackageNameMap().get(propertyUpdateDto.getPackageName());

        this.pomReaderService.updateProperty(
                pomPackage.getModel().getPomFile().toPath(),
                propertyUpdateDto.getPropertyName(),
                propertyUpdateDto.getNewValue());

        log.info("Property [{}] was updated for package [{}] with new value [{}]",
                propertyUpdateDto.getPropertyName(),
                propertyUpdateDto.getPackageName(),
                propertyUpdateDto.getNewValue());
    }

    private Map<String, String> getPropertiesMap(Model model) {
        return model.getProperties().stringPropertyNames().stream()
                .filter(n -> n.contains("version"))
                .collect(Collectors.toMap(
                        identity(),
                        n -> model.getProperties().getProperty(n))
                );
    }

}
