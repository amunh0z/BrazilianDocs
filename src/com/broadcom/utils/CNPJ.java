package com.broadcom.utils;

import java.util.ArrayList;
import javax.swing.text.MaskFormatter;
import com.grid_tools.products.datamasker.IMaskFunction;

public class CNPJ implements IMaskFunction {
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
        if (originalValue.length() < 14) {
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
        geraCNPJParcial();
        geraPrimeiroDigito();
        geraSegundoDigito();

        String cnpj = "";
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
                MaskFormatter mf = new MaskFormatter("##############");
                mf.setValueContainsLiteralCharacters(false);
                cnpj = mf.valueToString(texto);
            } else {
                MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
                mf.setValueContainsLiteralCharacters(false);
                cnpj = mf.valueToString(texto);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return cnpj;
    }

    //Method for random 0-9 number generation
    private int geraNumAleatorio(){
        int numero = (int) (Math.random() * 10);

        return numero;
    }

    //Method for generating part of the CPF (up to 9 digits)
    private ArrayList<Integer> geraCNPJParcial(){
        for(int i = 0; i < 8 - digitsToKeep; i++){
            listaAleatoria.add(geraNumAleatorio());
        }

        //this is to add the 0001 code to the CNPJ
        listaAleatoria.add(0);
        listaAleatoria.add(0);
        listaAleatoria.add(0);
        listaAleatoria.add(1);

        return listaAleatoria;
    }

    //Method for the first checksum digit
    private ArrayList<Integer> geraPrimeiroDigito(){
        listaNumMultiplicados = new ArrayList<Integer>();
        int primeiroDigito;
        int totalSomatoria = 0;
        int restoDivisao;
        int peso = 5;

        for(int item : listaAleatoria){
            listaNumMultiplicados.add(item * peso);

            peso--;

            if(peso < 2){
                peso = 9;
            }
        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao < 2){
            primeiroDigito = 0;
        } else{
            primeiroDigito = 11 - restoDivisao;
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
        int peso = 6;

        for(int item : listaAleatoria){
            listaNumMultiplicados.add(item * peso);

            peso--;

            if(peso < 2){
                peso = 9;
            }
        }

        for(int item : listaNumMultiplicados){
            totalSomatoria += item;
        }

        restoDivisao = (totalSomatoria % 11);

        if(restoDivisao < 2){
            segundoDigito = 0;
        } else{
            segundoDigito = 11 - restoDivisao;
        }

        listaAleatoria.add(segundoDigito);

        return listaAleatoria;
    }
}
