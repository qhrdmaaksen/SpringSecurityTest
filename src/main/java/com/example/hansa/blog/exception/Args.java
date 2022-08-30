package com.example.hansa.blog.exception;

class MyException1 extends Exception{
    //noting
}
class MyException2 extends Exception{
    //noting
}
class MyException {
    public static void main(String[] args) {
        try {
            //같은 클래스내의 static 멤버이므로 오브젝트 생성없이 MyMethod 메소드 직접 호출가능
            MyMethod(args[0]); //인수가 없으면 예외 발생
            } catch (MyException1 e1) {
                System.out.println("예외 1");
            } catch (MyException2 e2){
                System.out.println("예외 2");
            } finally{
                    System.out.println("종료");
            }
        }
        static void MyMethod(String str) throws MyException1,MyException2 {
            System.out.println("입력값은 " + str + "입니다.");
            int x = Integer.parseInt(str); // 문자열을 정수로 변환
            switch ( x ){
                case 1:
                    throw new MyException1();//예외 발생 시킴
                case 2:
                    throw new MyException2();//예외 발생 시킴
                default:
                    System.out.println("예외 없음");
            }
        }
    }
