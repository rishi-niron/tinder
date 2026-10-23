package com.hopsgrill;

import java.math.BigDecimal;
import java.util.Objects;

public final class MenuItem {
    private final String name;
    private final Category category;
    private final BigDecimal price;

    public MenuItem(String name, Category category, BigDecimal price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name is required");
        }
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("price must be non-negative");
        }
        this.name = name;
        this.category = Objects.requireNonNull(category, "category");
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem other = (MenuItem) o;
        return name.equals(other.name) && category == other.category && price.compareTo(other.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return name + " (" + category + ") $" + price;
    }
}
