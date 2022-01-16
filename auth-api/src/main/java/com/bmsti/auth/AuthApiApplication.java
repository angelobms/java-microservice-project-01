package com.bmsti.auth;

import com.bmsti.auth.entity.Permission;
import com.bmsti.auth.entity.User;
import com.bmsti.auth.repository.PermissionRepository;
import com.bmsti.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApiApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            initUsers(userRepository, permissionRepository, passwordEncoder);
        };
    }

    private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        Permission permission = null;
        Permission admin = permissionRepository.findByDescription("Admin");
        if (admin == null) {
            permission = new Permission();
            permission.setDescription("Admin");
            permission = permissionRepository.save(permission);
        } else {
            permission = admin;
        }

        User userAdmin = new User();
        userAdmin.setUserName("angelobms");
        userAdmin.setAccountNonExpired(true);
        userAdmin.setAccountNonLocked(true);
        userAdmin.setCredentialsNonExpired(true);
        userAdmin.setEnabled(true);
        userAdmin.setPassword(passwordEncoder.encode("123456"));
        userAdmin.setPermissions(Arrays.asList(permission));

        User user = userRepository.findByUserName("angelobms");
        if(user == null) {
            userRepository.save(userAdmin);
        }
    }
}
