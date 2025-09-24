package controller;

import model.personagens.Heroi;
import model.personagens.Inimigo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class GerenciadorDistribuicaoPontosSwing {
    private final Random random = new Random();

    public void evoluirHeroi(Heroi heroi, Component parent) {
        if (heroi == null) return;
        while (heroi.getPontosHabilidade() > 0) {
            String[] opcoes = {"Força", "Defesa", "Vida Máx (+5)", "Concluir"};
            int escolha = JOptionPane.showOptionDialog(parent,
                    "Pontos disponíveis: " + heroi.getPontosHabilidade() + "\nEscolha um atributo:",
                    "Distribuir Pontos",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, opcoes, opcoes[0]);

            if (escolha == -1 || escolha == 3) break;

            int max = heroi.getPontosHabilidade();
            Integer qtd = solicitarQuantidade(parent, max);
            if (qtd == null || qtd <= 0) break;
            if (!heroi.gastarPontosHabilidade(qtd)) {
                JOptionPane.showMessageDialog(parent, "Pontos insuficientes.", "Aviso", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            switch (escolha) {
                case 0 -> heroi.addForca(qtd);
                case 1 -> heroi.addDefesa(qtd);
                case 2 -> heroi.addVidaMaxima(5 * qtd);
            }
        }
    }

    public void evoluirInimigos(List<Inimigo> inimigos) {
        if (inimigos == null) return;
        for (Inimigo inimigo : inimigos) {
            int op = random.nextInt(3); // 0,1,2
            int qtdMax = Math.max(0, inimigo.getPontosHabilidade());
            int qtd = qtdMax == 0 ? 0 : random.nextInt(qtdMax + 1);
            if (qtd == 0 || !inimigo.gastarPontosHabilidade(qtd)) continue;
            switch (op) {
                case 0 -> inimigo.addForca(qtd);
                case 1 -> inimigo.addDefesa(qtd);
                case 2 -> inimigo.addVidaMaxima(5 * qtd);
            }
        }
    }

    private Integer solicitarQuantidade(Component parent, int max) {
        while (true) {
            String s = JOptionPane.showInputDialog(parent,
                    "Quantos pontos deseja aplicar? (1.." + max + ")\nCancelar ou vazio para encerrar",
                    Math.min(3, max));
            if (s == null || s.isBlank()) return null;
            try {
                int v = Integer.parseInt(s.trim());
                if (v >= 1 && v <= max) return v;
            } catch (NumberFormatException ignored) {}
            JOptionPane.showMessageDialog(parent, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

