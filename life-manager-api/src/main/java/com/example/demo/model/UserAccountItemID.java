package com.example.demo.model;

import java.io.Serializable;

public class UserAccountItemID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	
	//アイテムのソート番号
	private Long sortOrderNumber;
	
	public UserAccountItemID() {}
	
	public UserAccountItemID(Long id, Long sortOrderNumber){
		this.id = id;
		this.sortOrderNumber = sortOrderNumber;
	}
}
