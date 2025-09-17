package Controller;

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

    public int mostrarMenu(String titulo, List<String> opcoes) {
        System.out.println(AMARELO + "\n" + titulo + RESET);
        for (int i = 0; i < opcoes.size(); i++) {
            System.out.printf(VERDE + "%d - %s" + RESET + "%n", i + 1, opcoes.get(i));
        }
        return lerOpcao(1, opcoes.size());
    }

    public <T extends Enum<T> & MenuOpcoes> List<String> menuOpcao(T[] valores) {
        return Arrays.stream(valores)
                .map(MenuOpcoes::getNome)
                .toList();
    }

}
