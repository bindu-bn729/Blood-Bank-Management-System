package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodStockRepository bloodStockRepository;

    @GetMapping("/add")
    public String showAddDonorPage(Model model) {
        model.addAttribute("donor", new Donor());
        return "addDonor";
    }

    @PostMapping("/add")
    public String addDonor(@ModelAttribute Donor donor) {

        donorRepository.save(donor);

        String bloodGroup = donor.getBloodGroup().toUpperCase();

        BloodStock stock = bloodStockRepository.findByBloodGroup(bloodGroup);

        if (stock != null) {

            stock.setUnits(stock.getUnits() + 1);
            bloodStockRepository.save(stock);

        } else {

            BloodStock newStock = new BloodStock();
            newStock.setBloodGroup(bloodGroup);
            newStock.setUnits(1);
            bloodStockRepository.save(newStock);
        }

        return "redirect:/donor/list";
    }

    @GetMapping("/list")
    public String donorList(Model model) {

        model.addAttribute("donors", donorRepository.findAll());
        return "donorList";
    }

    @GetMapping("/delete/{id}")
    public String deleteDonor(@PathVariable int id) {

        donorRepository.deleteById(id);
        return "redirect:/donor/list";
    }
}