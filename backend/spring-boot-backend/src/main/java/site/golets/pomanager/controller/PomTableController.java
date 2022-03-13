package site.golets.pomanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.golets.pomanager.model.PomTable;
import site.golets.pomanager.service.PomTableService;

@RestController
@RequestMapping("/api/pomtable")
@AllArgsConstructor
public class PomTableController {

    private PomTableService service;

    @GetMapping
    public PomTable getPomTable(){
        return service.getPomTable();
    }

}
