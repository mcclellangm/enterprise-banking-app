package com.portfolio.banking.repository;

import com.portfolio.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
/*
Interview Question
Q: Why can we extend JpaRepository without writing an implementation class ourselves?

A: Spring Data JPA provides built-in CRUD methods through the JpaRepository interface.
 At runtime, Spring Data automatically creates a repository implementation using a proxy,
  allowing us to perform database operations such as save(), findById(), and deleteById()
  without writing the implementation ourselves. Hibernate handles the underlying JPA
  persistence operations.

  Here:
    - Account identifies the entity we're managing.
    - Long identifies the type of the entity's primary key.
    - Spring Data JPA supplies the repository implementation at runtime.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
