package com.socialnetwork.database;

import com.socialnetwork.model.entity.*;
import com.socialnetwork.repository.PostRepository;
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
import java.util.List;
import java.util.UUID;

@Configuration
public class Database {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PostRepository postRepository;

    public Database(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PostRepository postRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.postRepository = postRepository;
    }

    @Bean
    CommandLineRunner seeder() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                var roleAdmin = roleRepository.save(new Role(RoleEnum.ROLE_ADMIN));
                var roleUser = roleRepository.save(new Role(RoleEnum.ROLE_USER));
                var admin = userRepository.save(new User(UUID.fromString("00000000-0000-0000-0000-000000000000"),"admin", "admin", passwordEncoder.encode("123456789"), "admin@gmail.com", null, new Date(), null, null, "12345678"));
                var normalUser = userRepository.save(new User(UUID.fromString("00000000-0000-0000-0000-000000000001"),"user", "user", passwordEncoder.encode("123456789"), "user@gmail.com", null, new Date(), null, null, "12345678"));

                userRoleRepository.save(new UserRole(admin, roleAdmin));
                userRoleRepository.save(new UserRole(normalUser, roleUser));
            }
        };
    }
}
