package com.example.securitytest.account;

import org.springframework.data.jpa.repository.JpaRepository;
//저장소 만들기 jpa가 구현체가 자동으로만들어지고 빈으로 등록
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByUsername(String username);
}
