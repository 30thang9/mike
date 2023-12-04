package com.nth.mike.model.dto.user;

import com.nth.mike.entity.AccountStatus;
import com.nth.mike.entity.Gender;
import com.nth.mike.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private Date dateOfBirth;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private String urlAvatar;
    private AccountStatus accountStatus;
    private List<Role> roles;
}
