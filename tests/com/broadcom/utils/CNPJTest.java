package com.broadcom.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class CNPJTest {

    @Test
    public void mask() {

        CNPJ tst = new CNPJ();

        Object maskedVal = tst.mask("08584535000169","8","N");
        System.out.println(maskedVal);
        assertEquals("08584535000169",maskedVal);
    }
}