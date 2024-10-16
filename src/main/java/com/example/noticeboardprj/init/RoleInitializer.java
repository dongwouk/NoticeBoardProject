package com.example.noticeboardprj.init;

import com.example.noticeboardprj.entity.RoleEntity;
import com.example.noticeboardprj.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (!roleRepository.existsByName("ROLE_USER")) {
            RoleEntity userRole = new RoleEntity();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
}