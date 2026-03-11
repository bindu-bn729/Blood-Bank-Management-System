package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blood")
public class BloodStockController {

    @Autowired
    private BloodStockRepository bloodStockRepository;

    @GetMapping("/issue")
    public String issuePage() {
        return "issueBlood";
    }

    @PostMapping("/issue")
    public String issueBlood(@RequestParam String bloodGroup,
                             @RequestParam int units,
                             Model model) {

        bloodGroup = bloodGroup.toUpperCase();

        BloodStock stock = bloodStockRepository.findByBloodGroup(bloodGroup);

        if (stock != null && stock.getUnits() >= units) {

            stock.setUnits(stock.getUnits() - units);
            bloodStockRepository.save(stock);

            model.addAttribute("message", "Blood issued successfully");

        } else {

            model.addAttribute("message", "Not enough blood available");
        }

        return "issueBlood";
    }

    @GetMapping("/view")
    public String viewBlood(Model model) {

        model.addAttribute("blood", bloodStockRepository.findAll());
        return "viewBlood";
    }
}