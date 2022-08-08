package site.golets.pomanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.golets.pomanager.dto.PropertyUpdateDto;
import site.golets.pomanager.model.PomTable;
import site.golets.pomanager.service.PomTableService;

@RestController
@RequestMapping("/api/pom-table")
@AllArgsConstructor
public class PomTableController {

    private PomTableService pomTableService;

    @GetMapping
    public PomTable getPomTable(){
        return pomTableService.getPomTable();
    }

    @PostMapping("/update-property")
    public void updateProperty(@RequestBody PropertyUpdateDto propertyUpdateDto) {
        pomTableService.updateProperty(propertyUpdateDto);
    }

}
