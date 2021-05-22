package com.example.securitytest.hansa.abstruct;

public class Calculation {//계산해주는 메인 클래스
    public static void main(String[] args) {
        Plus plus = new Plus() ; //plus 클래스 객체 생성
        plus.setData(12 , 24);//추상메소드 setdata 호출 및 값 입력
        plus.answer();//추상 메소드의 answer(응답) 메소드 호출
    }
}
