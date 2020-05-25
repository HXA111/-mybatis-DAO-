package test;

import util.check;

public class Md5_Driver {
    public static void main(String[] args) {
        check check=new check();
        String password="";
        password=check.md5("123");
        System.out.println(password);
    }
}
