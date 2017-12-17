package com.example.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.example.model.Account;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

    private static final AtomicLong counter = new AtomicLong();

    private static List<Account> accounts;

    static{
        accounts= populateDummyAccounts();
    }

    public List<Account> findAllAccounts() {
        return accounts;
    }

    public Account findById(long id) {
        for(Account account : accounts){
            if(account.getId() == id){
                return account;
            }
        }
        return null;
    }

    public Account findByName(String name) {
        for(Account account : accounts){
            if(account.getName().equalsIgnoreCase(name)){
                return account;
            }
        }
        return null;
    }

    public void saveAccount(Account account) {
        account.setId(counter.incrementAndGet());
        accounts.add(account);
    }

    public void updateAccount(Account account) {
        int index = accounts.indexOf(account);
        accounts.set(index, account);
    }

    public void deleteAccountById(long id) {

        for (Iterator<Account> iterator = accounts.iterator(); iterator.hasNext(); ) {
            Account account = iterator.next();
            if (account.getId() == id) {
                iterator.remove();
            }
        }
    }

    public boolean isAccountExist(Account account) {
        return findByName(account.getName())!=null;
    }

    public void deleteAllAccounts(){
        accounts.clear();
    }

    private static List<Account> populateDummyAccounts(){
        List<Account> accounts = new ArrayList<Account>();
        accounts.add(new Account(counter.incrementAndGet(),"Sam",30, 70000));
        accounts.add(new Account(counter.incrementAndGet(),"Tom",40, 50000));
        accounts.add(new Account(counter.incrementAndGet(),"Jerome",45, 30000));
        accounts.add(new Account(counter.incrementAndGet(),"Silvia",50, 40000));
        return accounts;
    }

}