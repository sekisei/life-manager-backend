package com.example.demo.controller.requestbody;

import java.util.List;

import javax.validation.constraints.Size;

import com.example.demo.model.UserAccountItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    @Size(max = 100)
	private Long id;
	private String title;
	private List<UserAccountItem> userAccountItems;
}
