package pl.jedralski.CarService.controller;


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
import pl.jedralski.CarService.service.CarService;
import pl.jedralski.CarService.service.CombustionService;
import pl.jedralski.CarService.service.UserService;
import pl.jedralski.CarService.util.UserUtils;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    @Autowired
    private CombustionService combustionService;

    @RequestMapping("")
    public String main(Model model, Authentication authentication) throws DatabaseException {
        String username = authentication.getName();
        model.addAttribute("username", username);

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }
        return "admin";
    }

    @RequestMapping("/delete-user")
    public String deleteUser(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }
        return "/delete-user";
    }

    @PostMapping("/delete-user")
    public String deleteUserPost(@RequestParam("delete-user") String username, RedirectAttributes attributes) throws DatabaseException {

        if (userService.findUserIDByUsername(username) != null){
            combustionService.deleteUserCombustion(userService.findUserIDByUsername(username));
            carService.deleteUserCar(userService.findUserIDByUsername(username));
            userService.deleteUser(userService.findUserIDByUsername(username));
            attributes.addFlashAttribute("message", 1);
            return "redirect:/admin";
        } else {
            attributes.addFlashAttribute("message", 3);
            return "redirect:/admin";
        }

    }

    @RequestMapping("/add-car")
    public String addCar(Model model, Authentication authentication) throws DatabaseException, InputException {
        model.addAttribute("username", authentication.getName());

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }
        return "/add-car";
    }

    @PostMapping("/add-car")
    public String addCarPost(@RequestParam("year") int year, @RequestParam("make") String make, @RequestParam("model") String model, RedirectAttributes attributes) throws InputException, DatabaseException {

        if (carService.findID(year, make, model) == null){
            carService.addCar(year, make, model);
            attributes.addFlashAttribute("message", 2);
            return "redirect:/admin";
        } else {
            attributes.addFlashAttribute("message", 4);
            return "redirect:/admin";
        }



    }

}