package ftn.xws.userservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ftn.xws.userservice.security.TokenUtils;
import ftn.xws.userservice.security.auth.RestAuthenticationEntryPoint;
import ftn.xws.userservice.security.auth.TokenAuthenticationFilter;
import ftn.xws.userservice.service.Impl.UserServiceImpl;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
		// Hesiranje lozinke korisnika pomocu BCrypt funkcije.
	 	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	    // Kod punjenja podacima u data.sql napisati u komentaru lozinku jer pamti njen hes, a ne original!!!
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(10);
	    }
	
	    @Autowired
	    private UserServiceImpl jwtUserDetailsService;
	    
	    @Autowired
	    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	    
	    @Autowired
	    private TokenUtils tokenUtils;
	    
	    // Registrujemo authentication managera koji ce da uradi autentifikaciju korisnika za nas
	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	    
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			
			 auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
			 
	    }
	    
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	        	//stateless komuikacija klijent server
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	            
	            //401 greska za neautorizovane zahteve, moze i nazad na login stranicu
	            .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
	            
	            //ovim putanjama mogu svi korisnici da pristupe
	            .authorizeRequests().antMatchers(	"/auth/**",
													"/follow/followedBy/**",
													"/user/ms/**",
													"/user/follow/**",
													"/user/unfollow/**",
													"/user/followRequest/**",
													"/user/acceptRequest/**",
													"/user/deleteRequest/**",
													"/user/content/**", 
													"/user/following/**",
													"/user/followers/**",
													"/user/one/**,",
													"/user/addCloseFriend",
													"/user/removeCloseFriend",
													"/user/getCloseFriends")
													.permitAll()
	            									
	            .anyRequest().authenticated().and() //svaki zahtev mora biti autorizovan	
	                 
	            .cors().and()  //ubacivanje CORS filtera u lanac filtera
	        	
	            .addFilterBefore( //ubacivanje filtera za proveru tokena u lanac filtera
	        		new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
	        		BasicAuthenticationFilter.class);
	        

	        http.csrf().disable();
	    }

	    @Override
	    public void configure(WebSecurity web) throws Exception {
	       web.ignoring().antMatchers( "/**");
	       				 //.antMatchers("/user/ms/public/**")
	       				 //.antMatchers("/user/getInfo/**");
	    	
	      
	        
	    }
	    
}
