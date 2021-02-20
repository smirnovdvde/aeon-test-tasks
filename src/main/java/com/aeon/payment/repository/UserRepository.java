package com.aeon.payment.repository;

import com.aeon.payment.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
