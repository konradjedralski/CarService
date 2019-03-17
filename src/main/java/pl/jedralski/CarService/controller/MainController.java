package pl.jedralski.CarService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.service.CarService;
import pl.jedralski.CarService.service.UserService;
import pl.jedralski.CarService.util.UserUtils;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String main(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (carService.findUserCar(userService.findUserIDByUsername(authentication.getName()))) {
            model.addAttribute("checkUserCar", 1);
            model.addAttribute("carData", carService.findAllDataUserCar(userService.findUserIDByUsername(authentication.getName())));
        } else {
            model.addAttribute("checkUserCar", 0);
        }
        return "main";
    }

    @RequestMapping("/add-user-car")
    public String addUserCar(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (carService.findUserCar(userService.findUserIDByUsername(authentication.getName()))) {
            model.addAttribute("checkUserCar", 1);
            model.addAttribute("carData", carService.findAllDataUserCar(userService.findUserIDByUsername(authentication.getName())));
        } else {
            model.addAttribute("checkUserCar", 0);
        }
        List<String> makeList = new ArrayList<>();
        List<String> modelList = new ArrayList<>();
        List<Integer> yearList = new ArrayList<>();

        for (String make : carService.findMake()) {
            makeList.add(new String(make));
        }
        for (String modelCar : carService.findModel()) {
            modelList.add(new String(modelCar));
        }
        for (int year : carService.findYear()) {
            yearList.add(new Integer(year));
        }
        model.addAttribute("makeList", makeList);
        model.addAttribute("modelList", modelList);
        model.addAttribute("yearList", yearList);

        return "add-user-car";
    }

    @PostMapping("/add-user-car")
    public String addUserCarPost(@RequestParam(required = false, name = "combobox-make") String make, @RequestParam(required = false, name = "combobox-model") String model, @RequestParam(required = false, name = "combobox-year") String year, RedirectAttributes attributes, Authentication authentication) throws InputException, DatabaseException {

        if (carService.findID(Integer.parseInt(year), make, model) != null) {
            carService.addUserCar(userService.findUserIDByUsername(authentication.getName()), carService.findID(Integer.parseInt(year), make, model));
            attributes.addFlashAttribute("message", 1);
            return "redirect:/";
        }else {
            attributes.addFlashAttribute("message", 1);
            return "redirect:/add-user-car";
        }

    }

    @RequestMapping("/update-user-car")
    public String updateUserCar(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (carService.findUserCar(userService.findUserIDByUsername(authentication.getName()))) {
            model.addAttribute("checkUserCar", 1);
            model.addAttribute("carData", carService.findAllDataUserCar(userService.findUserIDByUsername(authentication.getName())));
        } else {
            model.addAttribute("checkUserCar", 0);
        }
        List<String> makeList = new ArrayList<>();
        List<String> modelList = new ArrayList<>();
        List<Integer> yearList = new ArrayList<>();

        for (String make : carService.findMake()) {
            makeList.add(new String(make));
        }
        for (String modelCar : carService.findModel()) {
            modelList.add(new String(modelCar));
        }
        for (int year : carService.findYear()) {
            yearList.add(new Integer(year));
        }
        model.addAttribute("makeList", makeList);
        model.addAttribute("modelList", modelList);
        model.addAttribute("yearList", yearList);
        return "update-user-car";
    }

    @PostMapping("/update-user-car")
    public String updateUserCarPost(@RequestParam(required = false, name = "combobox-make") String make, @RequestParam(required = false, name = "combobox-model") String model, @RequestParam(required = false, name = "combobox-year") String year, RedirectAttributes attributes, Authentication authentication) throws InputException, DatabaseException {

        if (carService.findID(Integer.parseInt(year), make, model) != null) {
            carService.deleteUserCar(userService.findUserIDByUsername(authentication.getName()));
            carService.addUserCar(userService.findUserIDByUsername(authentication.getName()), carService.findID(Integer.parseInt(year), make, model));
            attributes.addFlashAttribute("message", 1);
            return "redirect:/";
        }else {
            attributes.addFlashAttribute("message", 1);
            return "redirect:/update-user-car";
        }

    }
    @RequestMapping("/change-car-photo")
    public String updateCarPhoto(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        if (carService.findUserCar(userService.findUserIDByUsername(authentication.getName()))) {
            model.addAttribute("checkUserCar", 1);
            model.addAttribute("carData", carService.findAllDataUserCar(userService.findUserIDByUsername(authentication.getName())));
        } else {
            model.addAttribute("checkUserCar", 0);
        }
        return "change-car-photo";
    }

    @PostMapping("/change-car-photo")
    public String updateCarPhotoPost(@RequestParam("photo") String photo, RedirectAttributes attributes, Model model, Authentication authentication) throws InputException, DatabaseException {

        carService.updateCarPhoto(userService.findUserIDByUsername(authentication.getName()), photo);
        attributes.addFlashAttribute("message", 2);
        return "redirect:/";

    }

}
