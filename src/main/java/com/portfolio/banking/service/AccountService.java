package com.portfolio.banking.service;

import com.portfolio.banking.model.Account;
import com.portfolio.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    //DEPENDENCY INJECTION (Two-steps):
    //Step 1: Declare the Dependency as Final
    private final AccountRepository accountRepository;

    //Step 2: Define the Dependency Constructor
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //we used this to locally store Accounts prior to connecting the database
    private final Map<Long, Account> accounts = new HashMap<>();

    public Account createAccount(Account account) {

        if(account!=null&&accountRepository.existsById(account.getId())!=true){
            return accountRepository.save(account);

        }else{
            return null;
        }



        // We used the followng when we were saving accounts locally to aHashmap prior to database connection
//        if(account!=null){
//            Account acc = new Account();
//            acc.setBalance(account.getBalance());
//            acc.setId(account.getId());
//            acc.setAccountHolder(account.getAccountHolder());
//
//            if(accounts.containsKey(account.getId())){
//                return null;
//            }else{
//                accounts.put(acc.getId(),acc);
//                return acc;
//            }
//        }else{
//            return null;
//        }

    }

    public Account getAccountById(Long id) {

        /*
        Interview checkpoint
        Q: Why does JpaRepository.findById() return Optional<Account> instead of Account?

        A: Optional<Account> represents a value that may or may not exist.
        It makes the possibility of a missing database record explicit and provides
        methods such as .orElse(), .isPresent(), and .orElseThrow() to handle that case.
         */
        return accountRepository.findById(id).orElse(null);

        //Ee used this to locally retrieve Accounts prior to connecting the database
//        //return accounts.get(id); <----same as below code bc it returns null if isn't found
//        if(accounts.get(id)!=null){
//            return accounts.get(id);
//        }else{
//            return null;
//        }
//
//        // TODO:
//        // ID 1 -> John Doe, balance 15000
//        if(id.equals(1L)){
//            Account account = new Account();
//            account.setAccountHolder("John Doe");
//            account.setId(1L);
//            account.setBalance(15000);
//            return account;
//
//            // ID 2 -> Jane Smith, balance 25000
//        } else if (id.equals(2L)) {
//            Account account = new Account();
//            account.setAccountHolder("Jane Smith");
//            account.setId(2L);
//            account.setBalance(25000);
//            return account;
//
//        }
//        // Otherwise return null for now
//        return null;

    }
    /*
        Interview checkpoint
        Q: What is the purpose of Optional<T> in Java, and why does JpaRepository.findById() return an Optional?

        A: Optional<T> is a Java container that may or may not contain a value. Spring Data JPA's findById() returns an Optional
         because a database record with the requested ID might not exist. This allows developers to handle missing records
         explicitly instead of assuming an entity was found.

         Interview-ready question and answer
        Q: Why should we retrieve an existing JPA entity before updating it instead of saving the incoming request object directly?

        A: We retrieve the existing entity to ensure we're modifying the correct database record. The incoming request contains
        the new values, while the existing entity represents the persisted record. We update the appropriate fields and use
        JpaRepository.save() to persist the changes.
     */
    public Account updateAccount(Long id, Account updatedAccount) {
       Optional<Account> targetAccount = accountRepository.findById(id);
       if(targetAccount.isEmpty()){
           return null;
       }else{
           targetAccount.get().setBalance(updatedAccount.getBalance());
           return accountRepository.save(targetAccount.get());
       }
    }
}
