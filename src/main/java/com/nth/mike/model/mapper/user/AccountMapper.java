package com.nth.mike.model.mapper.user;

import com.nth.mike.entity.Account;
import com.nth.mike.model.dto.user.AccountDTO;

public class AccountMapper {
    public static AccountDTO toAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setUsername(accountDTO.getUsername());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setDateOfBirth(account.getDateOfBirth());
        accountDTO.setGender(account.getGender());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setAddress(account.getAddress());
        accountDTO.setUrlAvatar(account.getUrlAvatar());
        return accountDTO;
    }
}
