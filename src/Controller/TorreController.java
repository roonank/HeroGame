package Controller;

import model.personagens.Personagem;
import model.personagens.herois.Gilgamesh;
import model.personagens.herois.Hercules;
import model.personagens.herois.Ragnar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class TorreController {
    private Personagem jogador;
    private List<Personagem> inimigos;
    Scanner scanner = new Scanner(System.in);

    public TorreController() {
        this.inimigos = new ArrayList<>();
        inimigos.add(new Gilgamesh());
        inimigos.add(new Hercules());
        inimigos.add(new Ragnar());
    }

    public void iniciarTorre() {
        Gilgamesh gilgamesh = new Gilgamesh();
        Hercules hercules = new Hercules();
        Ragnar ragnar = new Ragnar();

        System.out.println("🏛️ Bem-vindo à Torre dos Deuses!");
        System.out.println("Escolha seu herói:");
        System.out.println("1 - " + gilgamesh.getNome() + " | " + gilgamesh.getAtributos().toString());
        System.out.println("2 - " + hercules.getNome() + " | " + hercules.getAtributos().toString());
        System.out.println("3 - " + ragnar.getNome() + " | " + ragnar.getAtributos().toString());

        int escolha = scanner.nextInt();
        switch (escolha) {
            case 1 -> jogador = new Gilgamesh();
            case 2 -> jogador = new Hercules();
            case 3 -> jogador = new Ragnar();
            default -> {
                System.out.println("Opção inválida, selecionando Gilgamesh por padrão.");
                jogador = new Gilgamesh();
            }
        }

        distribuirPontosJogador(jogador);

        System.out.println("Você escolheu: " + jogador.getNome());
        System.out.println("Iniciando a subida na torre...\n");

        inimigos.removeIf(inimigo -> inimigo.getNome().equals(jogador.getNome()));

        Random random = new Random();
        for (int andar = 1; andar <= inimigos.size(); andar++) {
            Personagem inimigo = inimigos.get(random.nextInt(inimigos.size()));
            System.out.println("Andar " + andar + ": Você encontrou " + inimigo.getNome() + "!");

            BatalhaController batalha = new BatalhaController(jogador, inimigo);
            boolean venceu = batalha.iniciarBatalha();

            if (venceu) {
                System.out.println(jogador.getNome() + " venceu a batalha e sobe para o próximo andar!\n");
                jogador.getStatus().setPontosAcao(jogador.getStatus().getPontosAcao() + 3);
                jogador.getStatus().setVidaAtual(jogador.getStatus().getVidaAtual() + 10);
                System.out.println(jogador.getNome() + " recuperou 10 pontos de vida! (Agora: " + jogador.getStatus().getVidaAtual());
            } else {
                System.out.println(jogador.getNome() + " foi derrotado na torre!");
                return;
            }


            System.out.println("Parabéns! Você conquistou a Torre dos Deuses!");
        }
    }


    public void distribuirPontosJogador(Personagem jogador) {
        Scanner scanner = new Scanner(System.in);
        String[] nomesAtributos = {"forca", "agilidade", "inteligencia", "constituicao"};

        System.out.println("\nVocê tem " + jogador.getPontosDisponiveis() + " pontos para distribuir entre os atributos.");

        while (jogador.getPontosDisponiveis() > 0) {
            System.out.println("\nPontos restantes: " + jogador.getPontosDisponiveis());
            System.out.println("Escolha o atributo para aumentar: ");
            System.out.println("1 - Força");
            System.out.println("2 - Agilidade");
            System.out.println("3 - Inteligência");
            System.out.println("4 - Constituição");

            int esc = scanner.nextInt();

            if (esc < 1 || esc > 4) {
                System.out.println("⚠️ Atributo inválido! Tente novamente.");
                continue;
            }

            System.out.println("Quantos pontos deseja adicionar em " + nomesAtributos[esc - 1] + "?");
            int quantidade = scanner.nextInt();

            jogador.distribuirPontos(nomesAtributos[esc - 1], quantidade);

            System.out.println("✅ " + nomesAtributos[esc - 1] + " atualizado: " + jogador.getAtributos());
        }

        System.out.println("\nDistribuição concluída! Seus atributos finais:");
        System.out.println(jogador.getAtributos());
    }
}
