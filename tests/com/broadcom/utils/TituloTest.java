package com.broadcom.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class TituloTest {

    @Test
    public void mask() {
        Titulo tst = new Titulo();

        Object maskedVal = tst.mask("250449010132","10","N");
        System.out.println(maskedVal);
        assertEquals("250449010132",maskedVal);
    }
}