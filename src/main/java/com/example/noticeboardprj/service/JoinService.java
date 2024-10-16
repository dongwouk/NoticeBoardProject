package com.example.noticeboardprj.service;

import com.example.noticeboardprj.dto.request.JoinDTO;
import com.example.noticeboardprj.entity.RoleEntity;
import com.example.noticeboardprj.entity.UserEntity;
import com.example.noticeboardprj.repository.RoleRepository;
import com.example.noticeboardprj.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    @Transactional
    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

        Boolean isExits = userRepository.existsByUsername(username);

        if(isExits) {
            System.out.println("중복된 아이디!!");
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
//        data.setRole("ROLE_ADMIN");
        data.setEmail(email);

        Optional<RoleEntity> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        roleAdmin.ifPresent(role -> data.setRole(Set.of(role))); // 역할이 존재할 때 설정
//        if(roleAdmin != null) {
//            data.setRole(Set.of(roleAdmin));
//        }


        System.out.println("===========");
        userRepository.save(data);
    }
}
