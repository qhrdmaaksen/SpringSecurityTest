package com.example.securitytest.account;

public class AccountContext {
    //Account를 저장할 수 있는 ThreadLocal 로컬 생성
    private static final ThreadLocal<Account>ACCOUNT_THREAD_LOCAL
            = new ThreadLocal<>();

    //account 를 받아서 ACCOUNT_THREAD_LOCAL에 넣음
    public static void setAccount(Account account){
        ACCOUNT_THREAD_LOCAL.set(account);
    }
    // ACCOUNT_THREAD_LOCAL 에서 꺼내올 메소드 생성
    public static Account getAccount(){
        return ACCOUNT_THREAD_LOCAL.get();
    }
}
