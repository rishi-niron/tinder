package com.hopsgrill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MenuTest {
    @Test
    void findIsCaseInsensitive() {
        assertTrue(Menu.defaultMenu().find("ribeye steak").isPresent());
        assertFalse(Menu.defaultMenu().find("pizza").isPresent());
    }

    @Test
    void filtersByCategory() {
        assertEquals(2, Menu.defaultMenu().byCategory(Category.GRILL).size());
    }

    @Test
    void rejectsDuplicates() {
        Menu m = Menu.defaultMenu();
        assertThrows(IllegalArgumentException.class,
                () -> m.add(new MenuItem("brownie", Category.DESSERT, BigDecimal.ONE)));
    }

    @Test
    void rejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new MenuItem("Free", Category.DRINK, new BigDecimal("-1")));
    }
}
