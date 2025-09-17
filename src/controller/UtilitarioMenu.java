package controller;

import model.interfaces.MenuOpcoes;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static util.Cores.*;

public class UtilitarioMenu {
    private final Scanner scanner;

    public UtilitarioMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public <T extends Enum<T> & MenuOpcoes> T menuOpcao(String titulo, T[] valores) {
        // Converte enum em lista de nomes
        List<String> nomes = Arrays.stream(valores)
                .map(MenuOpcoes::getNome)
                .toList();
        // Mostra menu e lê a escolha do usuário
        int escolha = mostrarMenu(titulo, nomes);
        // Retorna o enum escolhido
        return valores[escolha - 1];
    }

    public int mostrarMenu(String titulo, List<String> opcoes) {
        System.out.println(AMARELO + "\n" + titulo + RESET);
        for (int i = 0; i < opcoes.size(); i++) {
            System.out.printf(VERDE + "%d - %s" + RESET + "%n", i + 1, opcoes.get(i));
        }
        return lerOpcao(1, opcoes.size());
    }

    public int lerOpcao(int min, int max) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) return valor;
            } catch (NumberFormatException ignored) {
            }
            System.out.print(VERMELHO + "Opção inválida, tente novamente: " + RESET);
        }
    }



}
