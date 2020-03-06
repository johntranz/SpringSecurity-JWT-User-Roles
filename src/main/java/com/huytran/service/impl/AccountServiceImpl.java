package com.huytran.service.impl;

import com.huytran.exception.BadRequestException;
import com.huytran.models.Account;
import com.huytran.repository.AccountRepository;
import com.huytran.security.AccountUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElseThrow(()-> new BadRequestException("Username is not found"));
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AccountUserDetails(account);
    }

}
