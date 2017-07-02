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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.application.componant.CustomAuthenticationProvider;
import com.application.componant.MinuteBasedVoter;
import com.application.config.handler.LoginFailHandler;
import com.application.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity // (debug=true)
@ComponentScan("com.application")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	@Qualifier("secureUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	LoginSuccessHandler loginSuccessHandler;

	@Autowired
	LoginFailHandler loginFailHandler;

	public SecurityConfig() {
		super();
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(UrlMapping.getLoginUrl(), UrlMapping.getSignInUrl())
				.permitAll()
				.anyRequest()
				.authenticated()
				.accessDecisionManager(accessDecisionManager())
				.and()
					.formLogin()
						.loginPage(UrlMapping.getLoginUrl())
						.loginProcessingUrl(UrlMapping.getSignInUrl())
						.failureUrl(UrlMapping.getSignInUrl())
						.failureHandler(loginFailHandler)
						.successHandler(loginSuccessHandler)
						.usernameParameter("email").passwordParameter("password").and()
						.exceptionHandling().accessDeniedPage(UrlMapping.getUnauthorizedUrl())
				.and()
					.logout()
						.logoutSuccessUrl("/")
						.deleteCookies("JSESSIONID", "SESSION")
						.invalidateHttpSession(true)
				.and()
					.sessionManagement()
						.sessionAuthenticationStrategy(concurrentSessionControlAuthenticationStrategy())
						.maximumSessions(1)
						.maxSessionsPreventsLogin(true)
						.sessionRegistry(sessionRegistry());

	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(15);
	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
				new RoleVoter(), new AuthenticatedVoter(), new MinuteBasedVoter());
		return new UnanimousBased(decisionVoters);
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
		ConcurrentSessionControlAuthenticationStrategy csca = new ConcurrentSessionControlAuthenticationStrategy(
				sessionRegistry());
		csca.setExceptionIfMaximumExceeded(true);
		csca.setMaximumSessions(1);
		return csca;
	}

	// @Bean
	// SessionAuthenticationStrategy sessionAuthenticationStrategy() {
	//
	// ConcurrentSessionControlAuthenticationStrategy
	// concurrenSessionControlStrategy = new
	// ConcurrentSessionControlAuthenticationStrategy(
	// sessionRegistry());
	// concurrenSessionControlStrategy.setMaximumSessions(1);
	// concurrenSessionControlStrategy.setExceptionIfMaximumExceeded(false);
	//
	// SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new
	// SessionFixationProtectionStrategy();
	// sessionFixationProtectionStrategy.setMigrateSessionAttributes(false);
	//
	// RegisterSessionAuthenticationStrategy
	// registerSessionAuthenticationStrategy = new
	// RegisterSessionAuthenticationStrategy(
	// sessionRegistry());
	//
	// List<SessionAuthenticationStrategy> strategies = new LinkedList<>();
	// strategies.add(concurrenSessionControlStrategy);
	// strategies.add(sessionFixationProtectionStrategy);
	// strategies.add(registerSessionAuthenticationStrategy);
	//
	// CompositeSessionAuthenticationStrategy
	// compositeSessionAuthenticationStrategy = new
	// CompositeSessionAuthenticationStrategy(
	// strategies);
	//
	// return compositeSessionAuthenticationStrategy;
	// }
}
