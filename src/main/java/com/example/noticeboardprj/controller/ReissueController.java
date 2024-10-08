package com.example.noticeboardprj.controller;

import com.example.noticeboardprj.jwt.JWTUtil;
import com.example.noticeboardprj.service.JoinService;
import com.example.noticeboardprj.service.ReissueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class ReissueController {

    private ReissueService reissueService;

    public ReissueController(ReissueService reissueService) {
        this.reissueService = reissueService;
    }

    @PostMapping("/reissue")
    public String reissue(HttpServletRequest request, HttpServletResponse response) {

        reissueService.reissue(request, response);
        return "reissue";
    }

}
