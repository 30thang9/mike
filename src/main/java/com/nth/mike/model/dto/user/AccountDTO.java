package com.nth.mike.model.dto.user;

import com.nth.mike.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String username;
    private String fullName;
    private Date dateOfBirth;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private String urlAvatar;

}
