package com.application.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import com.application.componant.CustomAuthenticationProvider;
import com.application.componant.MinuteBasedVoter;
import com.application.config.handler.LoginFailHandler;
import com.application.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("com.application")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	@Qualifier("secureUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private LoginFailHandler loginFailHandler;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(UrlMapping.getLoginUrl(), UrlMapping.getSignInUrl()).permitAll()
				.anyRequest().authenticated().accessDecisionManager(accessDecisionManager()).and().formLogin()
				.loginPage(UrlMapping.getLoginUrl()).loginProcessingUrl(UrlMapping.getSignInUrl())
				.failureUrl(UrlMapping.getSignInUrl()).failureHandler(loginFailHandler)
				.successHandler(loginSuccessHandler).usernameParameter("email").passwordParameter("password").and()
				.logout().logoutSuccessUrl(UrlMapping.getLogOutUrl()).and().exceptionHandling()
				.accessDeniedPage(UrlMapping.getUnauthorizedUrl()).and().logout().logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID", "SESSION");
				
//				.and().sessionManagement()
//				.invalidSessionUrl(UrlMapping.getLoginUrl()).sessionFixation().changeSessionId().maximumSessions(1)
//				.expiredUrl(UrlMapping.getLoginUrl());

		// http.authorizeRequests().antMatchers("/auth/login",
		// "/auth/signIn").anonymous().anyRequest().authenticated()
		// .and().formLogin().loginPage("/auth/login.jsp").loginProcessingUrl("/auth/signIn")
		// .usernameParameter("email").passwordParameter("passwordHash").defaultSuccessUrl("/user/list",
		// true)
		// .failureUrl("/auth/login?error=true").and().logout().logoutSuccessUrl("/auth/signOut");

	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(15);
	}

	@SuppressWarnings("unchecked")
	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
				new RoleVoter(), new AuthenticatedVoter(), new MinuteBasedVoter());
		return new UnanimousBased(decisionVoters);
	}
}
