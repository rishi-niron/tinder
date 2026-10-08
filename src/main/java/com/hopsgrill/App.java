package com.hopsgrill;

public class App {
    public String greeting() {
        return "Hello from Hops Grill!";
    }

    public static void main(String[] args) {
        System.out.println(new App().greeting());
    }
}
