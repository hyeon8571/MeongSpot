package com.ottogi.be.dog.dto.response;

import com.ottogi.be.dog.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DogListResponse {
    private String name;
    private LocalDate birth;
    private String introduction;
    private Gender gender;
    private Boolean isNeuter;
    private String profileImage;
    private int age;
    private String breed;
}
