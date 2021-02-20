package com.aeon.payment.repository;

import com.aeon.payment.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
