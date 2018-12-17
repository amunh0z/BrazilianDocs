package com.broadcom.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class CNHTest {

    @Test
    public void mask() {
        CNH tst = new CNH();

        Object maskedVal = tst.mask("04132039722","9","N");
        System.out.println(maskedVal);
        assertEquals("04132039722",maskedVal);

        CNH tst2 = new CNH();

        Object maskedVal2 = tst2.mask("02736270980","9","N");
        System.out.println(maskedVal2);
        assertEquals("02736270980",maskedVal2);
    }
}