package pl.jedralski.CarService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    @Autowired
    public SpringSecurityConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(
                        "select username, hash, 1 as active from users where username = ?")
                .authoritiesByUsernameQuery(
                       "select username, role_name from users where username = ?")
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/").hasAnyAuthority("user", "admin")
                .antMatchers("/add-user-car/**").hasAnyAuthority("user", "admin")
                .antMatchers("/change-car-photo/**").hasAnyAuthority("user", "admin")
                .antMatchers("/update-user-car/**").hasAnyAuthority("user", "admin")
                .antMatchers("/combustion/**").hasAnyAuthority("user", "admin")
                .antMatchers("/account/**").hasAnyAuthority("user", "admin")
                .antMatchers("/admin/**").hasAnyAuthority("admin")
                .and().formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/")
                .usernameParameter("username").passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
                .and().exceptionHandling().accessDeniedPage("/access-denied");
    }

}


