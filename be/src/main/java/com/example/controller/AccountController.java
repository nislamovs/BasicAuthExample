package com.example.controller;

import com.example.model.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;  //Service which will do all data retrieval/manipulation work

    //-------------------Retrieve All Accounts--------------------------------------------------------

    @RequestMapping(value = "/account/", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> listAllAccounts() {

        List<Account> accounts = accountService.findAllAccounts();
        if(accounts.isEmpty()){
            return new ResponseEntity<List<Account>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }

    //-------------------Retrieve Single Account--------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {
        System.out.println("Fetching Account with id " + id);
        Account account = accountService.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }

    //-------------------Create a Account--------------------------------------------------------

    @RequestMapping(value = "/account/", method = RequestMethod.POST)
    public ResponseEntity<Void> createAccount(@RequestBody Account account, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Account " + account.getName());

        if (accountService.isAccountExist(account)) {
            System.out.println("A Account with name " + account.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        accountService.saveAccount(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/account/{id}").buildAndExpand(account.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //------------------- Update a Account --------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account account) {
        System.out.println("Updating Account " + id);

        Account currentAccount = accountService.findById(id);

        if (currentAccount==null) {
            System.out.println("Account with id " + id + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }

        currentAccount.setName(account.getName());
        currentAccount.setAge(account.getAge());
        currentAccount.setSalary(account.getSalary());

        accountService.updateAccount(currentAccount);
        return new ResponseEntity<Account>(currentAccount, HttpStatus.OK);
    }

    //------------------- Delete a Account --------------------------------------------------------

    @RequestMapping(value = "/account/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Account with id " + id);

        Account account = accountService.findById(id);
        if (account == null) {
            System.out.println("Unable to delete. Account with id " + id + " not found");
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }

        accountService.deleteAccountById(id);
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Accounts --------------------------------------------------------

    @RequestMapping(value = "/account/", method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteAllAccounts() {
        System.out.println("Deleting All Accounts");

        accountService.deleteAllAccounts();
        return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
    }
}