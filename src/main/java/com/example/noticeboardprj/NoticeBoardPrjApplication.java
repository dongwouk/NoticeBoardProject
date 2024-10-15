package com.example.noticeboardprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NoticeBoardPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoticeBoardPrjApplication.class, args);
    }

}
