/**
 * Autora: Joana Dark
 * Data :19/11/2023
 */
package hash;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TesteHash {

    public static void main(String[] args) {
        int tamanhoTabela = 10000;
        int[] tamanhosTeste = {1000, 5000, 7000}; //tamanhos diferentes pra testar o desempenho das funções hash com amostragens diferentes

        // Itera sobre os diferentes tamanhos de teste
        for (int tamanho : tamanhosTeste) {
            testarDesempenho("hashDivisao", tamanhoTabela, tamanho);
            testarDesempenho("hashMultiplicacao", tamanhoTabela, tamanho);
            testarDesempenho("MeuHash", tamanhoTabela, tamanho);
        }
    }

    // Método para testar o desempenho de um método de hash específico
    private static void testarDesempenho(String tipoHash, int tamanhoTabela, int numeroElementos) {
        TabelaHash tabelaHash = new TabelaHash(tamanhoTabela);
        String[] vetor = new String[10000];
        tabelaHash.preencher(vetor);

        try {// Inicializa 3 arquivos, um json com dados formatados pra criar os gráicos depois,
            //Um txt que mostra a inserção de uma chave e depois se ela foi encontrada
            //outro txt que mostra os resultados de tempo/tamanho/tipo de hash
            PrintWriter writer = new PrintWriter(new FileWriter("src/hash/output.txt", true));
            PrintWriter foundWriter = new PrintWriter(new FileWriter("src/hash/detalhes_output.txt", true));
            PrintWriter jsonWriter = new PrintWriter(new FileWriter("src/hash/results.json", true));

            // Teste de inserção
            long inicioInsercao = System.currentTimeMillis();
            for (int i = 0; i < numeroElementos; i++) {
                tabelaHash.inserir(vetor[i], tipoHash);
                foundWriter.println("Inserindo chave '" + vetor[i] + "' com " + tipoHash);
            }
            long fimInsercao = System.currentTimeMillis();
            // Escreve o tempo de inserção no arquivo de resultados
            writer.println("Tamanho: " + numeroElementos + " Tipo de Hash: " + tipoHash +
                    " Tempo de inserção: " + (fimInsercao - inicioInsercao) + "ms");

            // Teste de busca
            long inicioBusca = System.currentTimeMillis();
            for (int i = 0; i < numeroElementos; i++) {
                boolean encontrado = tabelaHash.buscar(vetor[i], tipoHash);

                if (encontrado) {
                    foundWriter.println("Chave '" + vetor[i] + "' encontrada para " + tipoHash);
                } else {
                    // Se não encontrado, escreve no arquivo de resultados e detalhes
                    writer.println("Chave '" + vetor[i] + "' não encontrada para " + tipoHash);
                    foundWriter.println("Chave '" + vetor[i] + "' não encontrada para " + tipoHash);
                }
            }
            long fimBusca = System.currentTimeMillis();
            // Escreve o tempo de busca no arquivo de resultados
            writer.println("Tempo de busca para " + tipoHash + ": " + (fimBusca - inicioBusca) + "ms");

            // Escrever resultados em formato JSON
            jsonWriter.println("{");
            jsonWriter.println("  \"TipoHash\": \"" + tipoHash + "\",");
            jsonWriter.println("  \"Tamanho\": " + numeroElementos + ",");
            jsonWriter.println("  \"TempoInsercao\": " + (fimInsercao - inicioInsercao) + ",");
            jsonWriter.println("  \"TempoBusca\": " + (fimBusca - inicioBusca));
            jsonWriter.println("}");
            jsonWriter.println();

            writer.println(); // Adiciona uma linha em branco para separar os resultados

            writer.close();
            foundWriter.close();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
