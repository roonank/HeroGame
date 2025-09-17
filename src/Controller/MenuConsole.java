package Controller;

import model.Torre;
import model.habilidades.Habilidade;
import model.personagens.Heroi;
import model.personagens.herois.HeroiEnum;
import model.personagens.inimigos.deuses.MitologiaEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static util.Cores.*;

public class MenuConsole {
    private final Scanner scanner = new Scanner(System.in);
    UtilitarioMenu utilitario = new UtilitarioMenu(scanner);
    GerenciadorDistribuicaoPontos gerenciador = new GerenciadorDistribuicaoPontos(utilitario);

    public void iniciar() {
        System.out.println(AZUL + "=== TOWER OF TRIALS ===" + RESET);
        Jogo jogo = new Jogo();

        //Defini o heroi para iniciar o jogo
        int escolhaHeroi = utilitario.mostrarMenu("Escolha seu herói:", utilitario.menuOpcao(HeroiEnum.values()));
        jogo.escolherHeroi(escolhaHeroi);
        Heroi heroiEscolhido = jogo.getJogador();

        //Defini a mitologia para iniciar o jogo
        int escolhaMitologia = utilitario.mostrarMenu("Escolha uma mitologia:", utilitario.menuOpcao(MitologiaEnum.values()));
        jogo.carregarInimigos(escolhaMitologia);

        Torre torre = new Torre(jogo);

        while (!torre.jogoFinalizado()) {
            if (!torre.iniciarAndar()) break;
            Batalha batalha = torre.getBatalhaAtual();

            //Evolui o inimigo conforme ocorre cada batalha
            if (torre.getAndarAtual() > 1) {
                gerenciador.evoluirInimigos(jogo.getInimigos());
            }

            System.out.println(AMARELO + "\n--- Andar " + torre.getAndarAtual() + " de " + torre.getTotalAndares() + " ---" + RESET);

            while (!batalha.isBatalhaFinalizada()) {
                if (batalha.isJogadorNaVez()) {
                    System.out.println("\n" + batalha.getStatusVida());

                    int acao = utilitario.mostrarMenu("Escolha a ação para:" +  batalha.getJogador().getNome(), utilitario.menuOpcao(MenuJogoEnum.values()));

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
                    batalha.executarTurno(null, null);
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
            }
        }
        if (jogo.getJogador().estaVivo())
            System.out.println(AZUL + "\nParabéns! Você conquistou a Torre dos Deuses!" + RESET);
    }
}