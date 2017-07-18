package com.application.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.request.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@ComponentScan("com.application")
public class TilesConfig {

	@Bean
	public TilesViewResolver getTilesViewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		// tilesViewResolver.setOrder(1);
		return tilesViewResolver;
	}

	@Bean
	public TilesConfigurer getTilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setCheckRefresh(true);
		tilesConfigurer.setDefinitionsFactoryClass(TilesDefinitionsConfig.class);

		// Add apache tiles definitions
		TilesDefinitionsConfig.addDefinitions();

		return tilesConfigurer;
	}

	public final static class TilesDefinitionsConfig implements DefinitionsFactory {

		private static final Map<String, Definition> tilesDefinitions = new HashMap<String, Definition>();
		private static final Attribute BASE_GUEST_TEMPLATE = new Attribute(
				"/WEB-INF/views/layouts/guest/mainGuestLayout.jsp");
		private static final Attribute BASE_MAIN_TEMPLATE = new Attribute(
				"/WEB-INF/views/layouts/main/mainLayout.jsp");

		@Override
		public Definition getDefinition(String name, Request tilesContext) {
			return tilesDefinitions.get(name);
		}

		/**
		 * @param name
		 *            <code>Name of the view</code>
		 * @param title
		 *            <code>Page title</code>
		 * @param body
		 *            <code>Body JSP file path</code>
		 *
		 *            <code>Adds default layout definitions</code>
		 */
		private static void addDefaultLayoutDef(String name, String title, String body) {
			Map<String, Attribute> attributes = new HashMap<String, Attribute>();

			if (name.equals("/auth/login")) {
				attributes.put("title", new Attribute(title));
				attributes.put("guestHeader", new Attribute("/WEB-INF/views/layouts/guest/guestHeader.jsp"));
				// attributes.put("menu", new Attribute("/WEB-INF/views/layout/menu.jsp"));
				attributes.put("body", new Attribute(body));
				attributes.put("guestFooter", new Attribute("/WEB-INF/views/layouts/guest/guestFooter.jsp"));

				tilesDefinitions.put(name, new Definition(name, BASE_GUEST_TEMPLATE, attributes));
			}else {
				
				attributes.put("title", new Attribute(title));
				attributes.put("mainHeader", new Attribute("/WEB-INF/views/layouts/main/mainHeader.jsp"));
				// attributes.put("menu", new Attribute("/WEB-INF/views/layout/menu.jsp"));
				attributes.put("body", new Attribute(body));
				attributes.put("mainFooter", new Attribute("/WEB-INF/views/layouts/main/mainFooter.jsp"));

				tilesDefinitions.put(name, new Definition(name, BASE_MAIN_TEMPLATE, attributes));
			}
		}

		public static void addDefinitions() {
			// Not Authenticate Users
			addDefaultLayoutDef("/auth/login", "Login Page", "/WEB-INF/views/auth/login.jsp");
			
			// Authenticate Users
			addDefaultLayoutDef("/user/list", "User List", "/WEB-INF/views/user/list.jsp");
			addDefaultLayoutDef("/user/show", "User Details", "/WEB-INF/views/user/show.jsp");
			addDefaultLayoutDef("/user/edit", "User Edit", "/WEB-INF/views/user/edit.jsp");
			addDefaultLayoutDef("/user/profile", "User Profile", "/WEB-INF/views/user/profile.jsp");
			addDefaultLayoutDef("/user/active", "All LoggedIn Users", "/WEB-INF/views/user/active.jsp");
		}

	}

}
