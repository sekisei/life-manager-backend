package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserAccountItem;

public interface UserAccountItemRepository extends JpaRepository<UserAccountItem, Long>{
	
	//@Query("DELETE FROM UserAccountItems e WHERE e.user_account_id = ?1 AND e.USERS_ID = ?2")
	//Collection<UserAccountItems> deleteByUserAccountIdAndUsersId(Long userAccount, Long users);
}
