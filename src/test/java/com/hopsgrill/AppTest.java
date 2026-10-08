package com.hopsgrill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void greetingIsReturned() {
        assertEquals("Hello from Hops Grill!", new App().greeting());
    }
}
