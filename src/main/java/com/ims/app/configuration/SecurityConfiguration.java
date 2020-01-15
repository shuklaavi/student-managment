package com.ims.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

 @Autowired
 private BCryptPasswordEncoder bCryptPasswordEncoder;
 
 @Autowired
 private javax.sql.DataSource dataSource;
 
 private final String STUDENT_QUERY = "select username, password, active from student where username=?";
 private final String ROLES_QUERY = "select s.username, r.role from student s inner join student_role sr on (s.id = sr.student_id) inner join role r on (sr.role_id=r.role_id) where s.username=?";

 @Override
 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.jdbcAuthentication()
   .usersByUsernameQuery(STUDENT_QUERY)
   .authoritiesByUsernameQuery(ROLES_QUERY)
   .dataSource(dataSource)
   .passwordEncoder(bCryptPasswordEncoder);
 }
 
 @Override
 protected void configure(HttpSecurity http) throws Exception{
  http.authorizeRequests()
   .antMatchers("/").permitAll()
   .antMatchers("/login").permitAll()
   .antMatchers("/students/add").permitAll()
   .antMatchers("/students/list").permitAll()
   .antMatchers("/students/signup").permitAll()
   .antMatchers("/students/home/**").hasAuthority("ADMIN").anyRequest()
   .authenticated().and().csrf().disable()
   .formLogin().loginPage("/login").failureUrl("/login?error=true")
   .defaultSuccessUrl("/students/home/home")
   .usernameParameter("username")
   .passwordParameter("password")
   .and().logout()
   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
   .logoutSuccessUrl("/login")
   .and().rememberMe()
   .tokenRepository(persistentTokenRepository())
   .tokenValiditySeconds(60*60)
   .and().exceptionHandling().accessDeniedPage("/access_denied");
 }
 
 @Bean
 public PersistentTokenRepository persistentTokenRepository() {
  JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
  db.setDataSource(dataSource);
  
  return db;
 }
}
