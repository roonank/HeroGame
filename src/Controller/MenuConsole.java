package Controller;

import model.Torre;
import model.habilidades.Habilidade;
import model.personagens.Heroi;
import model.personagens.Inimigo;

import java.util.Random;
import java.util.List;
import java.util.Scanner;

import static util.Cores.*;

public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    UtilitarioMenu utilitario = new UtilitarioMenu(scanner);
    GerenciadorDistribuicaoPontos gerenciador = new GerenciadorDistribuicaoPontos(utilitario);

    public void iniciar() {
        System.out.println(AZUL + "=== TOWER OF TRIALS ===" + RESET);
        Jogo jogo = new Jogo();
        List<String> herois = List.of(
                "Aquiles",//new Aquiles(),
                "Hércules",//new Hercules(),
                "Perseu",//new Perseu(),
                "Ragnar",//new Ragnar(),
                "Gilgamesh"//new Gilgamesh()
        );
        System.out.println(AMARELO + "\nEscolha seu herói:" + RESET);
        for (int i = 0; i < herois.size(); i++) {
            System.out.println(VERDE + "" + (i + 1) + " - " + herois.get(i) + RESET);
        }
        jogo.escolherHeroi(utilitario.lerOpcao(1, herois.size()));
        Heroi heroiEscolhido = jogo.getJogador();
        System.out.println(AMARELO + "\nEscolha a mitologia:" + RESET);
        System.out.println(VERDE + "1 - Egípcia" + RESET);
        System.out.println(VERDE + "2 - Grega" + RESET);
        jogo.carregarInimigos(utilitario.lerOpcao(1, 2));

        Torre torre = new Torre(jogo);

        while (!torre.jogoFinalizado()) {
            if (!torre.iniciarAndar()) break;
            Batalha batalha = torre.getBatalhaAtual();

            if (torre.getAndarAtual() > 1) {
                gerenciador.evoluirInimigos(jogo.getInimigos());
            }
            System.out.println(AMARELO + "\n--- Andar " + torre.getAndarAtual() + " de " + torre.getTotalAndares() + " ---" + RESET);

            while (!batalha.isBatalhaFinalizada()) {
                if (batalha.isJogadorNaVez()) {
                    System.out.println("\n" + batalha.getStatusVida());
                    System.out.println(AMARELO + "\nEscolha a ação para " + batalha.getJogador().getNome() + RESET);
                    System.out.println(VERDE + "1 - Atacar");
                    System.out.println(VERDE + "2 - Usar habilidade");
                    System.out.println(VERDE + "3 - Defender");
                    System.out.println(VERDE + "4 - Fugir" + RESET);

                    int acao = utilitario.lerOpcao(1, 4);

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
                                int escolhaHabilidade = utilitario.lerOpcao(1, habilidades.size());
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
            boolean podeDistribuir = (torre.getAndarAtual() < jogo.getTotalAndares()) && (heroiEscolhido.getPontosDisponiveis() > 0);
            if (podeDistribuir) {
                gerenciador.evoluirHeroi(jogo.getJogador());
                //distribuirPontos(jogo.getJogador());
            }
        }

        if (jogo.getJogador().estaVivo())
            System.out.println(AZUL + "\nParabéns! Você conquistou a Torre dos Deuses!" + RESET);
    }
}