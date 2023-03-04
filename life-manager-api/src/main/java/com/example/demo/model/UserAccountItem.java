package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Entity
@Builder
@IdClass(UserAccountItemID.class)
public final class UserAccountItem {
	@Tolerate
	public UserAccountItem() {};
	
	@Id
	@GeneratedValue
	private Long id; //ユニークなアカウント番号
	
	@Id //アイテムのソート番号
	private Long sortOrderNumber;
	
	//フォーム画面のアイテムの種類
	private String itemType;
	
	//フォーム画面のアイテムの値
	private String itemValue;
	
	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;
	
	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
	@JoinColumn(name = "users_id")
	private Users users;
}