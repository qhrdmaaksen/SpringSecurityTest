package com.example.securitytest.hansa.abstruct;
//추상 클래스는 미완성이므로 오브젝트를 생성시킬수 없고 ,
//서브 클래스로 상속시킨 후 구체적인 메소드를 구현해서 사용함
abstract class Calc {
    int a ;
    int b ;
    //추상 메소드
    //처리내용을 기술하지 않고 호출하는 방법(프로토타입)만을 정의한 메소드
    abstract void answer();

    void setData(int m , int n){
        a = m ;
        b = n ;
    }
}
