package bank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController {
    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/about")
    public String about() { return "about"; }

    @RequestMapping("/contact")
    public String contact() { return "contact"; }

    @RequestMapping("/user/all")
    public String users() { return "users"; }
}
