package org.example.gradingcenter;

import org.example.gradingcenter.data.entity.School;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradingCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradingCenterApplication.class, args);
        School school = new School();
        school.setName("School");
    }

}
