package pl.jedralski.CarService.controller;


import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.Combustion;
import pl.jedralski.CarService.service.CombustionService;
import pl.jedralski.CarService.service.UserService;
import pl.jedralski.CarService.util.UserUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/combustion")
public class CombustionController {

    @Autowired
    UserService userService;
    @Autowired
    CombustionService combustionService;

    @RequestMapping("")
    public String main(Model model, Authentication authentication) throws DatabaseException {
        String username = authentication.getName();
        model.addAttribute("username", username);

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (combustionService.checkUserCombustion(userService.findUserIDByUsername(username))) {
            List<Combustion> combustionList = new ArrayList<>();
            for (Combustion combustion : combustionService.findAllDataDesc(userService.findUserIDByUsername(username))) {
                combustionList.add(new Combustion(combustion.getDate(), combustion.getFuelLitres(), combustion.getKilometersTraveled(), combustion.getLiterPrice(), combustion.getCombustionValue(), combustion.getRefuelingPrice()));
            }
            model.addAttribute("checkUserCombustion", 1);
            model.addAttribute("combustionList", combustionList);
            model.addAttribute("summaryCombustion", DoubleRounder.round(combustionService.summaryCombustion(userService.findUserIDByUsername(username)), 2));
            return "combustion";
        } else {
            model.addAttribute("checkUserCombustion", 0);
            return "combustion";
        }
    }

    @RequestMapping("/add-combustion")
    public String addCombustion(Model model, Authentication authentication) throws DatabaseException {
        String username = authentication.getName();
        model.addAttribute("username", username);

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (combustionService.checkUserCombustion(userService.findUserIDByUsername(username))) {
            List<Combustion> combustionList = new ArrayList<>();
            for (Combustion combustion : combustionService.findAllDataDesc(userService.findUserIDByUsername(username))) {
                combustionList.add(new Combustion(combustion.getDate(), combustion.getFuelLitres(), combustion.getKilometersTraveled(), combustion.getLiterPrice(), combustion.getCombustionValue(), combustion.getRefuelingPrice()));
            }
            model.addAttribute("checkUserCombustion", 1);
            model.addAttribute("combustionList", combustionList);
            model.addAttribute("summaryCombustion", DoubleRounder.round(combustionService.summaryCombustion(userService.findUserIDByUsername(username)), 2));
            return "add-combustion";
        } else {
            model.addAttribute("checkUserCombustion", 0);
            return "add-combustion";
        }
    }

    @PostMapping("/add-combustion")
    public String addCombustionPost(@RequestParam("refueling-date") String refuelingDate, @RequestParam("fuel-litres") String fuelLitres, @RequestParam("kilometers-traveled") String kilometersTraveled, @RequestParam("liter-price") String literPrice, RedirectAttributes attributes, Authentication authentication) throws InputException, DatabaseException, ParseException {

        double combustionValue = DoubleRounder.round(((Double.parseDouble(fuelLitres) * 100) / Double.parseDouble(kilometersTraveled)), 2);
        double refuelingPrice = DoubleRounder.round((Double.parseDouble(fuelLitres) * Double.parseDouble(literPrice)), 2);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(refuelingDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        combustionService.addCombustion(userService.findUserIDByUsername(authentication.getName()), sqlDate, Double.parseDouble(fuelLitres), Double.parseDouble(kilometersTraveled), Double.parseDouble(literPrice), combustionValue, refuelingPrice);
        attributes.addFlashAttribute("message", 1);
        return "redirect:/combustion";
    }

    @RequestMapping("/delete-combustion")
    public String deleteCombustion(Model model, Authentication authentication) throws DatabaseException {
        String username = authentication.getName();
        model.addAttribute("username", username);

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (combustionService.checkUserCombustion(userService.findUserIDByUsername(username))) {
            List<Combustion> combustionList = new ArrayList<>();
            for (Combustion combustion : combustionService.findAllDataDesc(userService.findUserIDByUsername(username))) {
                combustionList.add(new Combustion(combustion.getDate(), combustion.getFuelLitres(), combustion.getKilometersTraveled(), combustion.getLiterPrice(), combustion.getCombustionValue(), combustion.getRefuelingPrice()));
            }
            model.addAttribute("combustionList", combustionList);
            model.addAttribute("summaryCombustion", DoubleRounder.round(combustionService.summaryCombustion(userService.findUserIDByUsername(username)), 2));
            return "delete-combustion";
        } else {
            return "delete-combustion";
        }
    }

    @PostMapping("/delete-combustion")
    public String deleteCombustionPost(@RequestParam("delete-combustion-date") String refuelingDate, RedirectAttributes attributes, Authentication authentication) throws InputException, DatabaseException, ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(refuelingDate);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime()); sqlDate.getMonth();

        combustionService.deleteCombustion(sqlDate, userService.findUserIDByUsername(authentication.getName()));
        attributes.addFlashAttribute("message", 2);
        return "redirect:/combustion";
    }
}