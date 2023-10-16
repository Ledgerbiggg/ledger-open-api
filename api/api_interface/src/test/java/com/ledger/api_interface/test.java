package com.ledger.api_interface;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest
public class test {
    @Test
    void name() {
        /*
         * A :!A
         * B : C
         * C : D
         * D : !D
         * */

        System.out.println(getRes());
    }

    public char getRes() {
        int j = 0; // Initialize 'j' to 0 or another appropriate value.
        for (int i = 'A'; i <= 'D'; i++) {
            if (i != 'A') {
                j++;
            }
            if ((i == 'C')) {
                j++;
            }
            if (i == 'D') {
                j++;
            }
            if (i != 'D') {
                j++;
            }
            if (j == 3) {
                return (char) i;
            }
            j = 0;
        }
        return 'Z';
    }
}
