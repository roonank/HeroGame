package controller;

import model.personagens.Heroi;
import model.personagens.Inimigo;

import java.util.List;
import java.util.Random;

import static util.Cores.*;

public class GerenciadorDistribuicaoPontos {
    private final UtilitarioMenu utilitario;
    private final Random random;

    public GerenciadorDistribuicaoPontos(UtilitarioMenu utilitario) {
        this.utilitario = utilitario;
        this.random = new Random();
    }

    public void evoluirHeroi(Heroi heroi) {
        if (heroi.getPontosHabilidade() == 0) {
            System.out.println(VERMELHO + "Sem pontos para distribuir." + RESET);
        } else {
            while (heroi.getPontosHabilidade() > 0) {
                System.out.println(AMARELO + "\nPontos disponíveis: " + heroi.getPontosHabilidade()
                        + "\nEscolha um atributo para aplicar pontos:"
                        + "\n1) Força   2) Defesa   3) Vida Máx (+5)  0) Sair" + RESET);

                int op = utilitario.lerOpcao(0, 3);
                if (op == 0) break;

                System.out.println(AMARELO + "Quantos pontos deseja aplicar? Heroi " +
                        heroi.getNome() + " tem " +
                        heroi.getPontosHabilidade() + " pontos para utlizar " +
                        "(0 para cancelar)" + RESET);

                int max = heroi.getPontosHabilidade();
                int qtd = utilitario.lerOpcao(0, max);

                if (!heroi.gastarPontosHabilidade(qtd)) {
                    System.out.println(AMARELO + "Pontos insuficientes." + RESET);
                    continue;
                }

                String opcao = switch (op) {
                    case 1 -> {
                        heroi.addForca(qtd);
                        yield "Força";
                    }
                    case 2 -> {
                        heroi.addDefesa(qtd);
                        yield "Defesa";
                    }
                    case 3 -> {
                        heroi.addVidaMaxima(5 * qtd);
                        yield "Vida Máxima";
                    }
                    default -> throw new IllegalArgumentException("Opção inválida: " + op);
                };
                System.out.printf(VERDE + "Aplicados %d ponto(s) em %s. Restam: %d%n" + RESET, qtd, opcao, heroi.getPontosHabilidade());
            }
        }
    }

    public void evoluirInimigos(List<Inimigo> inimigos) {
        for (Inimigo inimigo : inimigos) {
            int op = random.nextInt(3) + 1;
            int qtd = random.nextInt(inimigo.getPontosHabilidade() + 1);

            if (qtd == 0 || !inimigo.gastarPontosHabilidade(qtd)) {
                continue;
            }
            switch (op) {
                case 1 -> inimigo.addForca(qtd);
                case 2 -> inimigo.addDefesa(qtd);
                case 3 -> inimigo.addVidaMaxima(5 * qtd);
            }
        }
        System.out.println(CIANO + "\nInimigos Evoluidos" + RESET);
    }
}