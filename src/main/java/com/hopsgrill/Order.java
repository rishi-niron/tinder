package com.hopsgrill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    public static final BigDecimal TAX_RATE = new BigDecimal("0.08");

    private final Map<MenuItem, Integer> lines = new LinkedHashMap<>();

    public Order add(MenuItem item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        lines.merge(item, quantity, Integer::sum);
        return this;
    }

    public BigDecimal subtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<MenuItem, Integer> e : lines.entrySet()) {
            total = total.add(e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal tax() {
        return subtotal().multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal total() {
        return subtotal().add(tax());
    }

    public boolean isEmpty() {
        return lines.isEmpty();
    }

    public String receipt() {
        StringBuilder sb = new StringBuilder("--- Hops Grill ---\n");
        for (Map.Entry<MenuItem, Integer> e : lines.entrySet()) {
            BigDecimal line = e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue()));
            sb.append(String.format("%dx %-14s %8.2f%n", e.getValue(), e.getKey().getName(), line));
        }
        sb.append(String.format("%-17s %8.2f%n", "Subtotal", subtotal()));
        sb.append(String.format("%-17s %8.2f%n", "Tax (8%)", tax()));
        sb.append(String.format("%-17s %8.2f%n", "Total", total()));
        return sb.toString();
    }
}
