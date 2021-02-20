package com.aeon.payment.controller;


import com.aeon.payment.exception.PaymentsNotPossibleException;
import com.aeon.payment.domain.Account;
import com.aeon.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/login")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> tryLogin(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/pay")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> makePayment(){

        Account account;

        try {
            account = paymentService.makePayment();
        }catch(Exception e){
            throw new PaymentsNotPossibleException(e.getMessage());
        }
        return new ResponseEntity<>("Current account balans of user is " + account.getBalans().toString(),HttpStatus.OK);
    }

}
