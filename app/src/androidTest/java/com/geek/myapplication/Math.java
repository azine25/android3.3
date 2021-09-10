package com.geek.myapplication;

public class Math {

    public int add(double a, double b){
        return (int) (a + b);
    }

    public int sub(double a, double b){
        return (int) (a - b);
    }
    public  int multi(int a, int b){
        if (a*b == 0) return 0;
        else return a * b;
    }

    public int divide(int a,int b){
        if(b == 0 || a == 0) return 0;
        else return a / b;
    }

    public String reverse(String s){
        String[] sp = s.split(" ");
        StringBuilder word = new StringBuilder();

        if (sp[0].equals(""))return null;
        else {
            for (int i = sp.length - 1; i >= 0; i--) {
                word.append(sp[i]).append(" ");
            }
        }
        return word.toString().trim();
    }

}
