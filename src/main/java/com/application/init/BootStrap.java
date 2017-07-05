package com.application.init;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.application.config.UrlMapping;
import com.application.constants.AppConstant;
import com.application.enums.UserType;
import com.application.model.Permission;
import com.application.model.Role;
import com.application.model.User;
import com.application.service.RoleService;
import com.application.service.UserService;

@Component
public class BootStrap implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(BootStrap.class.getName());

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		generateDefaultData();
	}

	public void generateDefaultData() {
		LOGGER.info("************* Start Default Data *************");
		Role adminRole = null;
		if (roleService.countByName(AppConstant.DEFAULT_ROLE_ADMIN) == 0) {
			adminRole = new Role();
			adminRole.setName(AppConstant.DEFAULT_ROLE_ADMIN);

			// Permission permissionAdmin = new Permission();
			// permissionAdmin.setName("*:*");
			// adminRole.addPermission(permissionAdmin);

			for (Permission permission : UrlMapping.getPermissionListForAdmin()) {
				adminRole.addPermission(permission);
			}

			roleService.save(adminRole);
		}

		Role adviserRole = null;
		if (roleService.countByName(AppConstant.DEFAULT_ROLE_ADVISER) == 0) {
			adviserRole = new Role();
			adviserRole.setName(AppConstant.DEFAULT_ROLE_ADVISER);

			for (Permission permission : UrlMapping.getPermissionListForAdviser()) {
				adviserRole.addPermission(permission);
			}
			roleService.save(adviserRole);
		}

		Role userRole = null;
		if (roleService.countByName(AppConstant.DEFAULT_ROLE_USER) == 0) {
			userRole = new Role();
			userRole.setName(AppConstant.DEFAULT_ROLE_USER);

			for (Permission permission : UrlMapping.getPermissionListForUser()) {
				userRole.addPermission(permission);
			}
			roleService.save(userRole);
		}

		/* Admin User */
		if (userService.countByEmail("piyu181203@gmail.com") == 0) {
			User adminUser = new User();
			adminUser.setFirstName("Piyush");
			adminUser.setLastName("Chaudhari");
			adminUser.setEmail("piyu181203@gmail.com");
			adminUser.setPasswordHash(passwordEncoder.encode("123"));
			adminUser.setUserType(UserType.ADMIN);
			List<Role> roles = new ArrayList<>();
			roles.add(adminRole);
			adminUser.setRole(roles);
			userService.save(adminUser);
		}

		/* Adviser User */
		if (userService.countByEmail("appu4u@gmail.com") == 0) {
			User adviserUser = new User();
			adviserUser.setFirstName("Aplan");
			adviserUser.setLastName("Patel");
			adviserUser.setEmail("appu4u@gmail.com");
			adviserUser.setPasswordHash(passwordEncoder.encode("123"));
			adviserUser.setUserType(UserType.ADVISER);
			List<Role> adviserRoles = new ArrayList<>();
			adviserRoles.add(adviserRole);
			adviserUser.setRole(adviserRoles);
			userService.save(adviserUser);
		}
		/* User */
		if (userService.countByEmail("saurabh16388@gmail.com") == 0) {
			User user = new User();
			user.setFirstName("Saurabh");
			user.setLastName("Chaudhari");
			user.setEmail("saurabh16388@gmail.com");
			user.setPasswordHash(passwordEncoder.encode("123"));
			user.setUserType(UserType.USER);
			List<Role> userRoles = new ArrayList<>();
			userRoles.add(userRole);
			user.setRole(userRoles);
			userService.save(user);
		}
		
		if (userService.countByEmail("vipul.solanki@gmail.com") == 0) {
			User adviserUser = new User();
			adviserUser.setFirstName("Vipul");
			adviserUser.setLastName("Solanki");
			adviserUser.setEmail("vipul.solanki@gmail.com");
			adviserUser.setPasswordHash(passwordEncoder.encode("123"));
			adviserUser.setUserType(UserType.ADVISER);
			List<Role> adviserRoles = new ArrayList<>();
			adviserRoles.add(adviserRole);
			adviserRoles.add(adminRole);
			adviserUser.setRole(adviserRoles);
			userService.save(adviserUser);
		}
		LOGGER.info("************* End Default Data *************");
	}
}
