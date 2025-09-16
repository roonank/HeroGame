package Controller;

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
}
