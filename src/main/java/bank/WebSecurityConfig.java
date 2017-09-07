package bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    UserLookupService userLookupService = new UserLookupService();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Assign the UserLookupService to httpSecurity
        http.userDetailsService(userLookupService);

        //Configure security
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/new").permitAll()
                .antMatchers("/about").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").permitAll().and()
                .formLogin().successForwardUrl("/").and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.toString()))
                .invalidateHttpSession(true);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //Remove this hardcoded user later
        auth.inMemoryAuthentication()
                .withUser("admin").password("johnathan9!")
                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        //Assign the UserLookupService to authenication
        auth.userDetailsService(userLookupService);
    }
}
