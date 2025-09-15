package Controller;

import model.habilidades.Habilidade;
import model.personagens.Heroi;
import model.personagens.herois.*;
import util.Cores;

import java.util.List;
import java.util.Scanner;

import static util.Cores.*;

public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        System.out.println(AZUL + "=== TORRE DOS DEUSES ===" + RESET);
        Jogo jogo = new Jogo();
        List<Heroi> herois = List.of(
                new Aquiles(),
                new Hercules(),
                new Perseu(),
                new Ragnar(),
                new Gilgamesh()
        );
        System.out.println(AMARELO + "\nEscolha seu herói:" + RESET);
        for (int i = 0; i < herois.size(); i++) {
            System.out.println(VERDE + "" + (i + 1) + " - " + herois.get(i).getNome() + RESET);
        }
        int escolhaHeroi = lerOpcao(1, herois.size());
        Heroi heroiEscolhido = herois.get(escolhaHeroi - 1);
        jogo.setJogador(heroiEscolhido);
        System.out.println(AMARELO + "\nEscolha a mitologia:" + RESET);
        System.out.println(VERDE + "1 - Egípcia" + RESET);
        System.out.println(VERDE + "2 - Grega" + RESET);
        jogo.carregarInimigos(lerOpcao(1, 2));

        Torre torre = new Torre(jogo);

        while (!torre.jogoFinalizado()) {
            if (!torre.iniciarAndar()) break;
            Batalha batalha = torre.getBatalhaAtual();
            System.out.println(AMARELO + "\n--- Andar " + torre.getAndarAtual() + " de " + torre.getTotalAndares() + " ---" + RESET);

            while (!batalha.isBatalhaFinalizada()) {
                if (batalha.isJogadorNaVez()) {
                    System.out.println("\n" + batalha.getStatusVidaColorido());
                    //System.out.println(VERDE + "\n--- Vez de " + batalha.getJogador().getNome() + " ---" + RESET);
                    System.out.println(AMARELO + "\nEscolha a ação para " + batalha.getJogador().getNome()  + RESET);
                    System.out.println(VERDE +"1 - Atacar");
                    System.out.println(VERDE +"2 - Usar habilidade");
                    System.out.println(VERDE +"3 - Defender");
                    System.out.println(VERDE +"4 - Fugir");

                    int acao = lerOpcao(1, 4);

                    switch (acao) {
                        case 1:
                            batalha.executarTurno(Batalha.AcaoBatalha.ATACAR, null);
                            break;
                        case 2:
                            List<Habilidade> habilidades = heroiEscolhido.getHabilidades();
                            if (habilidades.isEmpty()) {
                                System.out.println(VERMELHO + "Você não possui habilidades. Atacando normalmente." + RESET);
                                batalha.executarTurno(Batalha.AcaoBatalha.ATACAR, null);
                            } else {
                                System.out.println(AMARELO + "\nEscolha uma habilidade:" + RESET);
                                for (int i = 0; i < habilidades.size(); i++) {
                                    Habilidade h = habilidades.get(i);
                                    System.out.println(CIANO + "" + (i + 1) + " - " + h.getNome() + " (Custo: " + h.getCustoMana() + " mana)" + RESET);
                                }
                                int escolhaHabilidade = lerOpcao(1, habilidades.size());
                                Habilidade habilidadeSelecionada = habilidades.get(escolhaHabilidade - 1);
                                batalha.executarTurno(Batalha.AcaoBatalha.USAR_HABILIDADE, habilidadeSelecionada);
                            }
                            break;
                        case 3:
                            batalha.executarTurno(Batalha.AcaoBatalha.DEFENDER, null);
                            break;
                        case 4:
                            batalha.executarTurno(Batalha.AcaoBatalha.FUGIR, null);
                            break;
                    }
                } else {
                    System.out.println(VERMELHO + "\n--- Vez de " + batalha.getInimigo().getNome() + " ---" + RESET);
                    batalha.executarTurno(null, null); // Ação do inimigo é decidida internamente
                }

                for (String log : batalha.getLogBatalha()) {
                    System.out.println("\n" + log);
                }
            }

            if (!batalha.isJogadorVencedor()) {
                System.out.println(VERMELHO + "\nVocê foi derrotado..." + RESET);
                return;
            }

            System.out.println(VERDE + "\nVitória no andar " + torre.getAndarAtual() + "!" + RESET);
            distribuirPontos(jogo.getJogador());
        }

        if (jogo.getJogador().estaVivo())
            System.out.println(AZUL + "\nParabéns! Você conquistou a Torre dos Deuses!" + RESET);
    }

    private void distribuirPontos(Heroi heroi) {
        if (heroi.getPontosDisponiveis() > 0) {
            System.out.println(AMARELO + "\nVocê tem " + heroi.getPontosDisponiveis() + " pontos para distribuir!" + RESET);
            System.out.println(VERDE + "1 - Força" + RESET);
            System.out.println(VERDE + "2 - Defesa" + RESET);
            System.out.println(VERDE + "3 - Vida" + RESET);
            System.out.print(AMARELO + "Escolha o atributo: " + RESET);

            int escolha = lerOpcao(1, 3);
            System.out.print(AMARELO + "Quantos pontos deseja gastar? " + RESET);
            int qtd = Integer.parseInt(scanner.nextLine());
            switch (escolha) {
                case 1 -> heroi.distribuirPontos("forca", qtd);
                case 2 -> heroi.distribuirPontos("defesa", qtd);
                case 3 -> heroi.distribuirPontos("vida", qtd);
            }
        }

    }

    private int lerOpcao(int min, int max) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) return valor;
            } catch (NumberFormatException ignored) {}
            System.out.print(VERMELHO + "Opção inválida, tente novamente: " + RESET);
        }
    }
}