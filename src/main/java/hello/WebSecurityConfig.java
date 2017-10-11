package hello;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String selectUserQuery =
      "SELECT username,password, enabled FROM users WHERE username=?";
  private static final String authorizationUserQuery =
      "SELECT username, role FROM user_roles WHERE username=?";


  @Autowired
  DataSource dataSource;


  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery(WebSecurityConfig.selectUserQuery)
        .authoritiesByUsernameQuery(WebSecurityConfig.authorizationUserQuery);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // XXX _NOTE:
    // Configuration that is specific to certain pages or urls MUST BE placed FIRST
    // than configurations that are common among most urls.

 // @formatter:// @formatter:off
    http.authorizeRequests()
      .antMatchers("/", "/home").access("hasRole('ROLE_USER')")
      .antMatchers("/hello").access("hasRole('ROLE_ADMIN')")
// .anyRequest().permitAll()
      .anyRequest().authenticated()
      .and()
      .formLogin()
        .loginPage("/login").permitAll()
      .and()
        .logout().logoutSuccessUrl("/login?logout")
      .and()
        .exceptionHandling().accessDeniedPage("/403")
      .and()
      .csrf();
// @formatter:on
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/css/*");
  }



}
