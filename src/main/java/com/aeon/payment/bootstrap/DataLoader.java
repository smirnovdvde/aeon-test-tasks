package com.aeon.payment.bootstrap;

import com.aeon.payment.domain.Account;
import com.aeon.payment.domain.User;
import com.aeon.payment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        Account account = Account.builder().balans(new BigDecimal(8)).build();
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        User user = User.builder().username("user1").password(passwordEncoder.encode("user1")).build();
        user.setAccounts(accounts);
        account.setUser(user);
        userRepository.save(user);

    }
}
