package com.hopsgrill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderTest {
    private final Menu menu = Menu.defaultMenu();

    @Test
    void computesTotals() {
        Order o = new Order()
                .add(menu.find("Ribeye Steak").get(), 2)
                .add(menu.find("IPA Pint").get(), 3);
        assertEquals(new BigDecimal("77.00"), o.subtotal());
        assertEquals(new BigDecimal("6.16"), o.tax());
        assertEquals(new BigDecimal("83.16"), o.total());
    }

    @Test
    void mergesRepeatedItems() {
        MenuItem pint = menu.find("IPA Pint").get();
        Order o = new Order().add(pint, 1).add(pint, 2);
        assertEquals(new BigDecimal("21.00"), o.subtotal());
    }

    @Test
    void rejectsZeroQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> new Order().add(menu.find("Brownie").get(), 0));
    }
}
