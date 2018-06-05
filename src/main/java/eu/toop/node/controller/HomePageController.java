package eu.toop.node.controller;

import eu.toop.node.CountryConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomePageController {
    @RequestMapping("/")
    public String homePage(Map<String, Object> model) {
        CountryConfiguration cc = new CountryConfiguration();

        model.put("providerServices", cc.providerServices());
        return "home";
    }
}
