package com.example.hansa.blog.abstruct;

public class Plus extends Calc{
    @Override//Clac 추상 클래스의 추상메소드 answer 오버라이딩
    void answer() {
        System.out.println(a + " + " + b + " = " + (a + b));
    }
}
