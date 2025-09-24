package controller;

import model.interfaces.MenuOpcoes;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;

public class UtilitarioMenuSwing {
    public <T extends Enum<T> & MenuOpcoes> T menuOpcao(String titulo, T[] valores) {
        List<String> nomes = Arrays.stream(valores)
                .map(MenuOpcoes::getNome)
                .toList();
        String escolha = (String) JOptionPane.showInputDialog(
                null,
                titulo,
                "Escolha uma opção",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nomes.toArray(),
                nomes.get(0)
        );
        for (int i = 0; i < nomes.size(); i++) {
            if (nomes.get(i).equals(escolha)) {
                return valores[i];
            }
        }
        return null; // Se o usuário cancelar
    }
}

