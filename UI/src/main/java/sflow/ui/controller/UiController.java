package sflow.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
    @GetMapping("/restrict")
    public String restrictPage() {
        return "restrict";
    }

    @GetMapping("/database")
    public String databasePage() {
        return "database";
    }
}
