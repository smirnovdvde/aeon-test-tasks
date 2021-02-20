package com.aeon.payment.repository;

import com.aeon.payment.domain.Payments;
import org.springframework.data.repository.CrudRepository;

public interface PaymentsRepository extends CrudRepository<Payments,Long> {
}
