package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.requestbody.AccountsWrapper;
import com.example.demo.controller.requestbody.UsersAccountCredentials;
import com.example.demo.model.UserAccount;
import com.example.demo.model.UserAccountItem;
import com.example.demo.model.Users;
import com.example.demo.repository.UserAccountItemRepository;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.repository.UsersRepository;
import com.example.demo.service.security.JwtService;

//@Controller
@RestController
public class CommentController {

    private final UsersRepository usersRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserAccountItemRepository userAccountItemRepository;

    //@Autowired ← コンストラクタが１つの場合、@Autowiredは省略
    public CommentController(UsersRepository usersRepository, UserAccountRepository userAccountRepository, UserAccountItemRepository userAccountItemRepository) {
		this.usersRepository = usersRepository;
		this.userAccountRepository = userAccountRepository;
		this.userAccountItemRepository = userAccountItemRepository;
    }
    
    @PostMapping("/v1/users") //DBにユーザ登録
    public String addUser(@Validated @RequestBody Users users){
    	usersRepository.save(users);
    	return "Add user";
    }
    
    /*
    @RequestMapping("/v1/users/{id}") //DBからユーザ取得
    public Users getUserInfo(@Validated @PathVariable("id") Long id){
    	return usersRepository.findById(id).orElse(new Users());
    }
    */
    
    @PostMapping("/v1/users/{id}/accounts") //アカウント登録
    public String addAccount(@Validated @RequestBody AccountsWrapper account, @PathVariable("id") Long id){
    	//TODO 開発用のユーザを消去し、クライアントから追加できるようにする
    	//予めDBに保存しておいたユーザをしばらく使う。
    	var user = usersRepository.findById(id).get();
    	var storedUserAccounts = userAccountRepository.findAll();
    	
    	List<UserAccount> updateTarget = storedUserAccounts.stream()
    			.filter(ac -> ac.getTitle().equals(account.getAccount().getTitle()))
    			.collect(Collectors.toList());
    	
		UserAccount userAccount = UserAccount.builder()
				.title(account.getAccount().getTitle())
				.users(user)
				.build();
		
    	//既存のユーザアカウントが存在する場合は、それを更新対象とする。
		//空の場合は更新対象が存在しないものとする。
    	if(updateTarget.isEmpty()) {
			userAccountRepository.save(userAccount);
			account.getAccount().getUserAccountItems().stream().forEach(accountItem -> {
        		UserAccountItem userAccountItem = UserAccountItem.builder()
        				.itemType(accountItem.getItemType())
        				.itemValue(accountItem.getItemValue())
        				.sortOrderNumber(accountItem.getSortOrderNumber())
        				.userAccount(userAccount)
        				.users(user)
        				.build();
    			userAccountItemRepository.save(userAccountItem);
    		});
    	} else {
    		account.getAccount().getUserAccountItems().stream().forEach(accountItem -> {
    			updateTarget.stream().forEach(ac -> {
    				ac.getUserAccountItems().stream().forEach(item -> {
    					item.setItemType(accountItem.getItemType());
    					item.setItemValue(accountItem.getItemValue());
    					userAccountItemRepository.save(item);
    				});
    				userAccountRepository.saveAll(updateTarget);
    			});
    		});
    	}
    	return "Add account";
    }
    
    @PostMapping("/v1/users/{id}/accounts/{acId}") //アカウントの削除
    public String delAccount(@PathVariable("id") Long id, @PathVariable("acId") Long acId) {
    	var userAccount = userAccountRepository.findById(acId).get();
    	if(userAccount.getUsers().getId().equals(id)) {
        	userAccountRepository.delete(userAccount);
    	}
    	//userAccountRepository.deleteByIdAndUsersId(acId, id);
    	//userAccountItemsRepository.deleteByUserAccountIdAndUsersId(acId, id);
    	return "Delete Account";
    }
    
    @RequestMapping("/v1/users/{id}/accounts") //アカウント取得
    public List<UserAccount> getAccounts(@Validated @PathVariable("id") Long id){
    	List<UserAccount> targetAccounts =  userAccountRepository.findAll()
    			.stream()
    			.filter(account -> account.getUsers().getId().equals(id))
    			.collect(Collectors.toList());

    	return targetAccounts;
    }
    
    @RequestMapping("/v1/users/{id}/accounts/{acId}") //特定のアカウント取得
    public UserAccount getAccount(@Validated @PathVariable("id") Long id, @PathVariable("acId") Long acId){
    	List<UserAccount> targetAccount =  userAccountRepository.findAll()
    			.stream()
    			.filter(account -> account.getUsers().getId().equals(id))
    			.filter(account -> account.getId().equals(acId))
    			.collect(Collectors.toList());
    	return targetAccount.get(0);
    }
    
    @GetMapping("/v1/test")
    public String test() {
    	return "Secret data";
    }
    
    /*
    @RequestMapping("/v1/users/{id}/accounts/{acId}/items") //アイテム取得
    public List<AccountItems> getItems(@PathVariable("id") Long id, @PathVariable("acId") Long acId){
    	List<AccountItems> items = accountItemsRepository.findAll();
    	var sortedItem = items.stream()
    			.sorted(Comparator.comparing(AccountItems::getSortOrderNumber)).collect(Collectors.toList());
    	return sortedItem;
    }
    
    @PostMapping("/v1/users/{id}/accounts/{acId}/items") //アイテム登録
    public String setItems(@RequestBody AccountItems items){
    	accountItemsRepository.save(items);
    	return "Add items";
    }
    */
}
