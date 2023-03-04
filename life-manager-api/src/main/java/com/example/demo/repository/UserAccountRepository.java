package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
	
	//@Query("DELETE FROM UserAccount e WHERE e.id = ?1 AND e.users_id = ?2")
	//Collection<UserAccount> deleteByIdAndUsersId(Long id, Long users);
}
