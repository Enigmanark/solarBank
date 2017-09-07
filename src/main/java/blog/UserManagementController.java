package blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserManagementController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        return "users";
    }

    @GetMapping("/user/account")
    public String accountSummary(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username);
        if(username.equals("admin")) {
            user = new User("admin", "Super", "Administrator");
        }
        model.addAttribute("user", user);
        return "account";
    }

    @GetMapping("/user/new")
    public String userCreation(
            @RequestParam(value="success", required = false, defaultValue = "false") String success, Model model) {
        if(success.equals("true")) {
            model.addAttribute("success", true);
            return "newUser";
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("user", new User());
            return "newUser";
        }
    }

    @PostMapping("/user/new")
    public String makeUser(@ModelAttribute User user) {
        //Add user authority
        user.makeUser();
        //Make an auth token
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        //Add the user auth to security
        SecurityContextHolder.getContext().setAuthentication(auth);
        //Save the user in the database
        userRepo.save(user);
        return "redirect:/user/new?success=true";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
