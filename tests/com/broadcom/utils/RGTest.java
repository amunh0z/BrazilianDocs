package com.broadcom.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class RGTest {

    @Test
    public void mask() {
        RG tst = new RG();

        Object maskedVal = tst.mask("28447146X","8","N");
        System.out.println(maskedVal);
        assertEquals("28447146X", maskedVal);
    }
}