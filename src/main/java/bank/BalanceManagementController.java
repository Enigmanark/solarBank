package bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BalanceManagementController {
    @Autowired
    UserRepo userRepo;

    @GetMapping("/user/account/withdraw")
    public String withdraw(
            @RequestParam(value = "success", required = false, defaultValue = "false") String success,
            Model model) {
        if(success.equals("true")) {
            return "withdraw";
        }
        else {
            model.addAttribute("withdraw", new DAOWithdraw());
            return "withdraw";
        }
    }

    @PostMapping("/user/account/withdraw")
    public String deposit(@ModelAttribute DAOWithdraw withdraw, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username);
        user.withdraw(withdraw.getWithdrawAmount());
        userRepo.save(user);
        return "redirect:/user/account/withdraw?success=true";
    }


    @GetMapping("/user/account/deposit")
    public String deposit(
            @RequestParam(value = "success", required = false, defaultValue = "false") String success,
            Model model) {
        if(success.equals(true)) {
            return "deposit";
        }
        else {
            model.addAttribute("deposit", new DAODeposit());
            return "deposit";
        }

    }

    @PostMapping("/user/account/deposit")
    public String deposit(@ModelAttribute DAODeposit deposit, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username);
        user.deposit(deposit.getDepositAmount());
        userRepo.save(user);
        return "redirect:/user/account/deposit?success=true";
    }

}
