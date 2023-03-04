package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Entity
@Builder
public class Users {
	@Tolerate
	public Users() {};
	
	//ユニークなユーザID
	@Id
	@GeneratedValue
	private Long id;
	
    @Size(max = 100)
	private String userName;
    
    private String password;
    private String role;
    
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    List<UserAccount> userAccount;
    
    //TODO userAccountItemsとusersの関係を見直す
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    List<UserAccountItem> userAccountItems;
}
