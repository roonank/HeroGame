package Controller;

import model.Torre;
import model.personagens.Heroi;
import model.personagens.herois.HeroiEnum;
import model.personagens.inimigos.deuses.MitologiaEnum;

import java.util.Scanner;

import static util.Cores.*;

public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);
    UtilitarioMenu utilitario = new UtilitarioMenu(scanner);
    GerenciadorDistribuicaoPontos gerenciador = new GerenciadorDistribuicaoPontos(utilitario);
    MenuBatalha menuBatalha = new MenuBatalha(utilitario);

    public void iniciar() {
        System.out.println(AZUL + "=== TOWER OF TRIALS ===" + RESET);
        Jogo jogo = new Jogo();

        //Defini o heroi para iniciar o jogo
        HeroiEnum  escolhaHeroi = utilitario.menuOpcao("Escolha seu herói:", HeroiEnum.values());
        jogo.escolherHeroi(escolhaHeroi);
        Heroi heroiEscolhido = jogo.getJogador();

        //Defini a mitologia para iniciar o jogo
        MitologiaEnum escolhaMitologia = utilitario.menuOpcao("Escolha uma mitologia:", MitologiaEnum.values());
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
                    menuBatalha.turnoJogador(heroiEscolhido, batalha);
                } else {
                    menuBatalha.turnoInimigo(batalha);
                }
                menuBatalha.exibirLog(batalha);
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