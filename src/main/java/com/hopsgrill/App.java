package com.hopsgrill;

public class App {
    public String greeting() {
        return "Hello from Hops Grill!";
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.greeting());

        Menu menu = Menu.defaultMenu();
        Order order = new Order()
                .add(menu.find("Ribeye Steak").get(), 2)
                .add(menu.find("IPA Pint").get(), 3)
                .add(menu.find("Brownie").get(), 1);
        System.out.print(order.receipt());
    }
}
