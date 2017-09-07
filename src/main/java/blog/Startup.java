package blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Startup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent e) {
        loadUsersIntoSecurity();
    }

    private void loadUsersIntoSecurity() {
        List<User> users = userRepo.findAll();
        for(User user : users) {
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println(user.getUsername() + " loaded.");
        }
        System.out.println("Accounts loaded into memory.");
    }
}
