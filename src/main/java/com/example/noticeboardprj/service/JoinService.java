package com.example.noticeboardprj.service;

import com.example.noticeboardprj.dto.JoinDTO;
import com.example.noticeboardprj.entity.UserEntity;
import com.example.noticeboardprj.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JoinService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public void joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        LocalDateTime created_date = LocalDateTime.now();
        LocalDateTime updated_date = LocalDateTime.now();
        LocalDateTime removed_date = LocalDateTime.now();

        Boolean isExits = userRepository.existsByUsername(username);

        if(isExits) {
            System.out.println("중복된 아이디!!");
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");
        data.setEmail(email);
        data.setCreated_date(created_date);
        data.setModified_date(updated_date);
        data.setRemoved_date(removed_date);


        System.out.println("===========");
        userRepository.save(data);
    }
}
