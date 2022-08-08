package site.golets.pomanager.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.golets.pomanager.service.GitManagementService;
import site.golets.pomanager.service.PomTableService;

import java.util.List;

@RestController
@RequestMapping("/api/git-management")
@AllArgsConstructor
public class RepositoryController {

    private GitManagementService gitManagementService;

    private PomTableService pomTableService;

    @GetMapping("/checkout-and-pull")
    public boolean checkoutAndPull(@RequestParam String branchName, @RequestParam List<String> packageNames) {
        return gitManagementService.checkoutAndPull(pomTableService.packageNamesToPaths(packageNames), branchName, true);
    }

}
