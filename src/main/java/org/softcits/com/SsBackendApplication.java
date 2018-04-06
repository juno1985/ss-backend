package org.softcits.com;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsUtils;

@SpringBootApplication
@Controller
public class SsBackendApplication {

		public static void main(String[] args) {
			SpringApplication.run(SsBackendApplication.class, args);
		}
	
	  @GetMapping("/user")
	    @ResponseBody
	    public Principal user(Principal user) {
	        return user;
	    }

	    @GetMapping("/resource")
	    @ResponseBody
	    public Map<String, Object> home() {
	        Map<String, Object> model = new HashMap<String, Object>();
	        model.put("id", UUID.randomUUID().toString());
	        model.put("content", "Hello World");
	        return model;
	    }

	    @GetMapping(value = "/{path:[^\\.]*}")
	    public String redirect() {
	        return "forward:/";
	    }


	    @Configuration
	   /* @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)*/
	    @Order(SecurityProperties.BASIC_AUTH_ORDER)
	    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	        @Override
	        protected void configure(HttpSecurity http) throws Exception {
	            // @formatter:off
	            http
	                .httpBasic().and()
	                .authorizeRequests()
	                    .antMatchers("/index.html", "/", "/home", "/login").permitAll()
	                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()//就是这一行啦 
	                    .anyRequest().authenticated()
	                    .and()
	                .csrf()
	                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	            // @formatter:on
	        }
	    }


}
