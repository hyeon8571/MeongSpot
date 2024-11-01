package com.ottogi.be.dog.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class DogAddRequest {
    private MultipartFile profileImage;
    private String name;
    private String breed;
    private String size;
    private int age;
    private String gender;
    private Boolean isNeuter;
    private LocalDate birth;
    private String introduction;
    private List<Long> personality;
}
