package com.broadcom.utils;

import java.util.ArrayList;
import javax.swing.text.MaskFormatter;
import com.grid_tools.products.datamasker.IMaskFunction;

public class Titulo implements IMaskFunction {
    private ArrayList<Integer> listaAleatoria = new ArrayList<Integer>();
    private ArrayList<Integer> listaNumMultiplicados = null;
    private Integer digitsToKeep;

    @Override
    public Object mask(Object... args) {
        String originalValue = (String) args[0];
        digitsToKeep = Integer.valueOf((String) args[1]);
        String useSeparator = (String) args[2];
        int countDigits = 0;

        if (null == originalValue) {
            return null;
        }

        // Proprietary validations and modifications
        if (originalValue.length() < 12) {
            return originalValue;
        }

        // if the number of digits to keep is greater than 8,
        // then it must be 10, so only the checksum characters
        // get generated
        if(digitsToKeep > 8){
            digitsToKeep = 10;
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
        geraTEParcial();
        geraPrimeiroDigito();
        geraSegundoDigito();

        String te = "";
        String texto = "";

		/*Here, we concatenate all list values in a string
		  because the formatting Arraylist does doesn't allow masking,
		  since it generates special chars with the generated numbers
		  and the system would return a ParseException*/
        for(int item : listaAleatoria){
            texto += item;
        }

        //Inside of the try-catch we'll use a mask
        try{
            if (useSeparator.equals("N")) {
                MaskFormatter mf = new MaskFormatter("############");
                mf.setValueContainsLiteralCharacters(false);
                te = mf.valueToString(texto);
            } else {
                MaskFormatter mf = new MaskFormatter("####.####.####");
                mf.setValueContainsLiteralCharacters(false);
                te = mf.valueToString(texto);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return te;
    }

    //Method for random 0-9 number generation
    private int geraNumAleatorio(){
        int numero = (int) (Math.random() * 10);

        return numero;
    }

    //Method for generating part of the CPF (up to 9 digits)
    private ArrayList<Integer> geraTEParcial(){
        if(digitsToKeep < 9) {
            for (int i = 0; i < 8 - digitsToKeep; i++) {
                listaAleatoria.add(geraNumAleatorio());
            }

            // there are 26 states in Brazil, plus DF and ZZ for everyone living abroad
            int estado = (int) (Math.random() * 28) + 1;
            listaAleatoria.add(estado / 10);
            listaAleatoria.add(estado % 10);
        }

        return listaAleatoria;
    }

    //Method for the first checksum digit
    private ArrayList<Integer> geraPrimeiroDigito(){
        listaNumMultiplicados = new ArrayList<Integer>();
        int primeiroDigito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 2;

        for(int item : listaAleatoria){
            listaNumMultiplicados.add(item * peso);

            peso++;

            if(peso > 9) break;
        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao > 9){
            primeiroDigito = 0;
        } else {
            primeiroDigito = restoDivisao;
        }

        listaAleatoria.add(primeiroDigito);

        return listaAleatoria;
    }

    //Method for the second checksum digit
    private ArrayList<Integer> geraSegundoDigito(){
        listaNumMultiplicados = new ArrayList<Integer>();
        int segundoDigito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 7;
        int i = 1;

        for(int item : listaAleatoria){
            if(i < 9) {
                i++;
            } else {
                listaNumMultiplicados.add(item * peso);
                peso++;
            }

        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao > 9){
            segundoDigito = 0;
        } else{
            segundoDigito = restoDivisao;
        }

        listaAleatoria.add(segundoDigito);

        return listaAleatoria;
    }
}