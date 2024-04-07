package com.socialnetwork.database;

import com.socialnetwork.model.entity.Role;
import com.socialnetwork.model.entity.RoleEnum;
import com.socialnetwork.model.entity.User;
import com.socialnetwork.model.entity.UserRole;
import com.socialnetwork.repository.RoleRepository;
import com.socialnetwork.repository.UserRepository;
import com.socialnetwork.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Configuration
public class Database {

    private final PasswordEncoder passwordEncoder;

    public Database(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner seeder(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                var adminRole = roleRepository.save(new Role(RoleEnum.ROLE_ADMIN));
                roleRepository.save(new Role(RoleEnum.ROLE_USER));

                var admin = new User("admin", "admin", passwordEncoder.encode("123456789"), "admin@gmail.com", null, new Date(), null, null, "12345678");
                admin.setId(UUID.fromString("78108cad-d2b3-439e-a7df-d7ea54040a4c"));

                var adminUser = userRepository.save(admin);

                userRoleRepository.save(new UserRole(adminUser, adminRole));
            }
        };
    }
}
