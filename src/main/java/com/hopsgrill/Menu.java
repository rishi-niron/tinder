package com.hopsgrill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Menu {
    private final Map<String, MenuItem> items = new LinkedHashMap<>();

    public void add(MenuItem item) {
        if (items.putIfAbsent(item.getName().toLowerCase(), item) != null) {
            throw new IllegalArgumentException("duplicate item: " + item.getName());
        }
    }

    public Optional<MenuItem> find(String name) {
        return Optional.ofNullable(items.get(name.toLowerCase()));
    }

    public List<MenuItem> byCategory(Category category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : items.values()) {
            if (item.getCategory() == category) {
                result.add(item);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<MenuItem> all() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public static Menu defaultMenu() {
        Menu m = new Menu();
        m.add(new MenuItem("Onion Rings", Category.STARTER, new BigDecimal("6.50")));
        m.add(new MenuItem("Ribeye Steak", Category.GRILL, new BigDecimal("28.00")));
        m.add(new MenuItem("BBQ Chicken", Category.GRILL, new BigDecimal("18.50")));
        m.add(new MenuItem("IPA Pint", Category.DRINK, new BigDecimal("7.00")));
        m.add(new MenuItem("Brownie", Category.DESSERT, new BigDecimal("8.00")));
        return m;
    }
}
