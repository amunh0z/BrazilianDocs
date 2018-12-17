package com.broadcom.utils;

import static org.junit.Assert.*;
import org.junit.Test;

public class CPFTest {

    @Test
    public void mask() {
        CPF tst = new CPF();

        Object maskedVal = tst.mask("16582104845","9","N");
        System.out.println(maskedVal);
        assertEquals("16582104845",maskedVal);
    }
}