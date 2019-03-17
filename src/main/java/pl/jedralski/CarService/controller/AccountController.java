package pl.jedralski.CarService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jedralski.CarService.exception.DatabaseException;
import pl.jedralski.CarService.exception.InputException;
import pl.jedralski.CarService.model.User;
import pl.jedralski.CarService.service.CarService;
import pl.jedralski.CarService.service.UserService;
import pl.jedralski.CarService.util.UserUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    @RequestMapping("")
    public String account(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("userData", userService.findAllData(authentication.getName()));

        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }

        return "account";
    }

    @RequestMapping("/edit/profile")
    public String editProfile(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("userData", userService.findAllData(authentication.getName()));
        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }
        return "edit-profile";
    }

    @PostMapping("/edit/profile")
    public String editProfilePost(@RequestParam("password") String password, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("photo") String photo, RedirectAttributes attributes, Model model, Authentication authentication, HttpServletRequest request) throws InputException, DatabaseException {
        User userData = userService.findAllData(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("userData", userData);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (username.equals("")) {
            username = userData.getUsername();
        }
        if (email.equals("")) {
            email = userData.getEmail();
        }
        if (firstName.equals("")) {
            firstName = userData.getFirstName();
        }
        if (lastName.equals("")) {
            lastName = userData.getLastName();
        }
        if (photo.equals("")) {
            photo = userData.getPhoto();
        }
        if (!(passwordEncoder.matches(password, userService.getHash(userData.getId())))) {
            attributes.addFlashAttribute("message", 2);
            return "redirect:/account/edit/profile";
        }
        if (username.equals(userData.getUsername())) {
            userService.updateUser(userData.getId(), username, firstName, lastName, email, photo);
            attributes.addFlashAttribute("message", 1);
            return "redirect:/account";
        } else {
            if (userService.findUsername(username)) {
                attributes.addFlashAttribute("message", 1);
                return "redirect:/account/edit/profile";
            } else {
                userService.updateUser(userData.getId(), username, firstName, lastName, email, photo);
                login(request, username, password);
                attributes.addFlashAttribute("message", 1);
                return "redirect:/account";
            }
        }
    }

    //Authentication new username
    private void login(HttpServletRequest request, String userName, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        //Authenticate the user
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //Create a new session and add the security context.
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    @RequestMapping("/edit/password")
    public String editPassword(Model model, Authentication authentication) throws DatabaseException {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("userData", userService.findAllData(authentication.getName()));
        if (UserUtils.hasRoleAdmin()) {
            model.addAttribute("admin", 1);
        }
        return "edit-password";
    }

    @PostMapping("/edit/password")
    public String editPasswordPost(@RequestParam("current-password") String currentPassword, @RequestParam("new-password") String password, Model model, Authentication authentication, RedirectAttributes attributes) throws InputException, DatabaseException {
        User userData = userService.findAllData(authentication.getName());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("userData", userData);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPassword, userService.getHash(userData.getId()))) {
            if (currentPassword.equals(password)) {
                attributes.addFlashAttribute("message", 2);
                return "redirect:/account/edit/password";
            } else {
                userService.updateUserPassword(userData.getId(), password);
                attributes.addFlashAttribute("message", 2);
                return "redirect:/account";
            }
        } else {
            attributes.addFlashAttribute("message", 1);
            return "redirect:/account/edit/password";
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchByTitle(@RequestParam(value = "search", required = false) String username, Model model, Authentication authentication, HttpServletRequest request, RedirectAttributes attributes) throws DatabaseException {
        if (userService.findAllData(username) != null) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("userData", userService.findAllData(username));
            if (carService.findUserCar(userService.findUserIDByUsername(username))) {
                model.addAttribute("checkUserCarData", 1);
                model.addAttribute("carData", carService.findAllDataUserCar(userService.findUserIDByUsername(username)));
            } else {
                model.addAttribute("checkUserCarData", 0);
            }

            if (UserUtils.hasRoleAdmin()) {
                model.addAttribute("admin", 1);
            }
            return "user";
        } else {
            attributes.addFlashAttribute("searchMessage", 1);
            return "redirect:" + request.getHeader("Referer");
        }
    }
}
