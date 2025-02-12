
package com.rabobank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	List<Account> findByCustomer_Id(Long customerId);
}
