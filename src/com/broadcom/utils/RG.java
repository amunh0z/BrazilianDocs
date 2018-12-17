package com.broadcom.utils;

import java.util.ArrayList;
import javax.swing.text.MaskFormatter;
import com.grid_tools.products.datamasker.IMaskFunction;

public class RG implements IMaskFunction {
    private ArrayList<Integer> listaAleatoria = new ArrayList<Integer>();
    private ArrayList<Integer> listaNumMultiplicados = null;
    private Integer digitsToKeep;

    @Override
    public Object mask(Object... args) {
        String originalValue = (String) args[0];
        digitsToKeep = Integer.valueOf((String) args[1]);
        String useSeparator = (String) args[2];
        int countDigits = 0;
        String digito;

        if (null == originalValue) {
            return null;
        }

        // Proprietary validations and modifications
        if (originalValue.length() < 9) {
            return originalValue;
        }

        for (char c : originalValue.toCharArray()) {
            if (countDigits < digitsToKeep) {
                if (Character.isDigit(c)) {
                    listaAleatoria.add(Character.getNumericValue(c));
                    countDigits++;
                }
            } else break;
        }
        //First, run the operation methods
        geraRGParcial();
        digito = geraDigito();

        String rg = "";
        String texto = "";

		/*Here, we concatenate all list values in a string
		  because the formatting Arraylist does doesn't allow masking,
		  since it generates special chars with the generated numbers
		  and the system would return a ParseException*/
        for(int item : listaAleatoria){
            texto += item;
        }

        // as the listaAleatoria has only digits and the checksum can be
        // a X char, then it is concatenated later
        texto += digito;

        //Inside of the try-catch we'll use a mask
        try{
            if (useSeparator.equals("N")) {
                MaskFormatter mf = new MaskFormatter("########A");
                mf.setValueContainsLiteralCharacters(false);
                rg = mf.valueToString(texto);
            } else {
                MaskFormatter mf = new MaskFormatter("##.###.###-A");
                mf.setValueContainsLiteralCharacters(false);
                rg = mf.valueToString(texto);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return rg;
    }

    //Method for random 0-9 number generation
    private int geraNumAleatorio(){
        int numero = (int) (Math.random() * 10);

        return numero;
    }

    //Method for generating part of the CPF (up to 9 digits)
    private ArrayList<Integer> geraRGParcial(){
        for(int i = 0; i < 8 - digitsToKeep; i++){
            listaAleatoria.add(geraNumAleatorio());
        }

        return listaAleatoria;
    }

    //Method for the first checksum digit
    private String geraDigito(){
        listaNumMultiplicados = new ArrayList<Integer>();
        String digito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 2;

        for(int item : listaAleatoria){
            listaNumMultiplicados.add(item * peso);

            peso++;
        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao == 1) {
            digito = "X";
        } else if (restoDivisao == 0) {
            digito = "0";
        } else {
            digito = Integer.toString(11 - restoDivisao);
        }

        return digito;
    }
}
