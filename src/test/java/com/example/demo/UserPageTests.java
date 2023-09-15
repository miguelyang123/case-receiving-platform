package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.service.ifs.UserPageService;

@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class UserPageTests {

    @Autowired
    private UserPageService userPageService;
    
    @Test
    public void updatePDFTest() {

        userPageService.updatePDF();
        
    }
    
}
