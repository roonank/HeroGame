package Controller;

import model.personagens.Personagem;

import java.util.Random;
import java.util.Scanner;

public class BatalhaController {
    private Personagem jogador;
    private Personagem inimigo;
    private Random random = new Random();

    public BatalhaController(Personagem jogador, Personagem inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
    }

    public boolean iniciarBatalha() {
        Scanner scanner = new Scanner(System.in);

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n=== STATUS ===");
            System.out.println(jogador.getNome() + " ❤️ " + jogador.getStatus().getVidaAtual() + " | PA: " + jogador.getStatus().getPontosAcao());
            System.out.println(inimigo.getNome() + " ❤️ " + inimigo.getStatus().getVidaAtual() + " | PA: " + inimigo.getStatus().getPontosAcao());

            System.out.println("\nSua vez! Escolha uma ação:");
            System.out.println("1 - Ataque básico");
            System.out.println("2 - Ataque especial");
            System.out.println("3 - Defender");
            System.out.println("4 - Fugir");

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1 -> {
                    jogador.atacar(inimigo);
                }
                case 2 -> {
                }
                case 3 -> {
                }
                case 4 -> {
                    System.out.println(jogador.getNome() + " fugiu!");
                    return false;
                }
            }

            if (!inimigo.estaVivo()) break;

            int acaoInimigo = random.nextInt(4);
            switch (acaoInimigo) {
                case 0 -> {
                    inimigo.atacar(jogador);
                    System.out.println(inimigo.getNome() + " atacou você!");
                }
                case 1 -> {
                }
                case 2 -> {
                }
                case 3 -> {
                    System.out.println(inimigo.getNome() + " fugiu da batalha!");
                    return true;
                }
            }
        }
        return jogador.estaVivo();
    }
}