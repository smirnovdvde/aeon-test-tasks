package com.aeon.payment.service;

import com.aeon.payment.domain.Account;
import com.aeon.payment.domain.User;
import com.aeon.payment.repository.AccountRepository;
import com.aeon.payment.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;
import java.util.Set;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    public PaymentServiceImpl( UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account makePayment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        if (!user.isPresent())
            throw new RuntimeException("User is not found");
        Set<Account> accounts = user.get().getAccounts();
        Optional<Account> optAccount = accounts.stream().findFirst();
        if (!optAccount.isPresent())
            throw new RuntimeException("User does not have any accounts, which can be used for payment");
        Account account = optAccount.get();
        if (account.getBalans().doubleValue()<1.1)
            throw new RuntimeException("User does not have enough money on his account");

        MathContext mathContext = new MathContext(2);

        account.setBalans(new BigDecimal(account.getBalans().doubleValue() - 1.1, mathContext));

        return accountRepository.save(account);
    }
}
