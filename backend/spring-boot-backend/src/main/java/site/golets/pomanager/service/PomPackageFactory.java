package site.golets.pomanager.service;

import org.apache.maven.model.Model;
import org.springframework.stereotype.Component;
import site.golets.pomanager.model.PomPackage;

@Component
public class PomPackageFactory {

    public PomPackage create(Model model) {
        PomPackage pomPackage = new PomPackage();
        pomPackage.setName(model.getName());
        return pomPackage;
    }

}
