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
            @RequestParam(value = "error", required = false, defaultValue = "0") String error,
            Model model) {
        if(success.equals("success")) {
            return "withdraw";
        }
        else {
            model.addAttribute("withdraw", new DAOWithdraw());
            return "withdraw";
        }
    }

    @PostMapping("/user/account/withdraw")
    public String withdraw(@ModelAttribute DAOWithdraw withdraw, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username);
        if(withdraw.getAmount() == 0) {
            return "redirect:/user/account/withdraw?error=1";
        }
        float result = user.withdraw(withdraw.getAmount());
        if(result == -1) {
            return "redirect:/user/account/withdraw?error=2";
        }
        else {
            userRepo.save(user);
            return "redirect:/user/account/withdraw?success=true";
        }
    }


    @GetMapping("/user/account/deposit")
    public String deposit(
            @RequestParam(value = "success", required = false, defaultValue = "false") String success,
            @RequestParam(value = "error", required = false, defaultValue = "0") String error,
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
        float amount = deposit.getDepositAmount();
        if(amount == 0) {
            return "redirect:/user/account/deposit?error=1";
        }
        else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepo.findByUsername(username);
            user.deposit(deposit.getDepositAmount());
            userRepo.save(user);
            return "redirect:/user/account/deposit?success=true";
        }
    }

}
