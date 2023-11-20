/**
 * Autora: Joana Dark
 * Data :19/11/2023
 */
package hash;
import java.nio.charset.*;
import java.util.LinkedList;
import java.util.Random;

public class TabelaHash {
    private LinkedList<String>[] tabela;
    private int tamanho;


    // Construtor da classe
    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }
    // Método para gerar uma string aleatória
    static String getRandomString(int i) {
        byte[] bytearray;
        String mystring;
        StringBuffer thebuffer;

        bytearray = new byte[256];
        new Random().nextBytes(bytearray);

        mystring = new String(bytearray, Charset.forName("UTF-8"));


        thebuffer = new StringBuffer();

        for (int m = 0; m < mystring.length(); m++) {
            char n = mystring.charAt(m);

            if (((n >= 'A' && n <= 'Z') || (n >= '0' && n <= '9')) && (i > 0)) {
                thebuffer.append(n);
                i--;
            }
        }


        return thebuffer.toString();
    }

    // Método para preencher um vetor de strings com strings aleatórias
    public void preencher(String vetor[]) {
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = getRandomString(10);
        }
    }

    // Método para calcular o hash usando o método de divisão
    private int hashDivisao(String chave) {
        return Math.abs(chave.hashCode()) % tamanho;
    }

    // Método para calcular o hash usando o método de multiplicação
    private int hashMultiplicacao(String chave) {
        double a = 0.6180339887; // valor indicado segundo pdfs e sites
        double produto = Math.abs(chave.hashCode()) * a;
        return (int) Math.floor(tamanho * (produto % 1));
    }

    // Método para calcular o hash personalizado
    private int MeuHash(String chave) { //baseado no MÉTODO DE DOBRAGEM
        int soma = 0;

        // Adiciona os valores ASCII dos caracteres em pares
        for (int i = 0; i < chave.length(); i += 2) {
            int valorCaractere = chave.charAt(i);
            if (i + 1 < chave.length()) {
                valorCaractere += chave.charAt(i + 1);
            }
            soma += valorCaractere;
        }

        // Usa a soma como índice final (garantindo que seja não negativo)
        return (soma + tamanho) % tamanho;
    }

    // Método para inserir uma chave na tabela usando o método de hash especificado
    public void inserir(String chave, String metodo) {
        int indice;
        switch (metodo) {
            case "hashDivisao":
                indice = hashDivisao(chave);
                break;
            case "hashMultiplicacao":
                indice = hashMultiplicacao(chave);
                break;
            case "MeuHash":
                indice = MeuHash(chave);
                break;
            default:
                throw new IllegalArgumentException("Método de hash não suportado: " + metodo);
        }
        tabela[indice].add(chave);
    }

    // Método para buscar uma chave na tabela usando o método de hash especificado
    public boolean buscar(String chave, String metodo) {
        int indice;
        switch (metodo) {
            case "hashDivisao":
                indice = hashDivisao(chave);
                break;
            case "hashMultiplicacao":
                indice = hashMultiplicacao(chave);
                break;
            case "MeuHash":
                indice = MeuHash(chave);
                break;
            default:
                throw new IllegalArgumentException("Método de hash não suportado: " + metodo);
        }
        return tabela[indice].contains(chave);
    }

}