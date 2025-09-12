package Controller;

import model.personagens.Personagem;
import model.personagens.deuses.egipcios.*;
import model.personagens.herois.Aquiles;
import model.personagens.herois.Hercules;
import model.personagens.herois.Perseu;


import java.util.*;


public class TorreController {
    private Personagem jogador;
    private List<Personagem> inimigos;
    Scanner scanner = new Scanner(System.in);

    public TorreController() {
        this.inimigos = new ArrayList<>();
    }

    public void iniciarTorre() {
        Aquiles aquiles = new Aquiles();
        Hercules hercules = new Hercules();
        Perseu perseu = new Perseu();

        System.out.println("🏛️ Bem-vindo à Torre dos Deuses!");
        System.out.println("Escolha seu herói:");
        System.out.println("1 - " + aquiles.getNome() + " | " + aquiles.getAtributos().toString());
        System.out.println("2 - " + hercules.getNome() + " | " + hercules.getAtributos().toString());
        System.out.println("3 - " + perseu.getNome() + " | " + perseu.getAtributos().toString());

        int escolha = scanner.nextInt();
        switch (escolha) {
            case 1 -> jogador = new Aquiles();
            case 2 -> jogador = new Hercules();
            case 3 -> jogador = new Perseu();
            default -> {
                System.out.println("Opção inválida, selecionando Aquiles por padrão.");
                jogador = new Aquiles();
            }
        }

        distribuirPontosJogador(jogador);

        System.out.println("Você escolheu: " + jogador.getNome());

        carregarInimigos();


        System.out.println("Iniciando a subida na torre...\n");

        Random random = new Random();
        for (int andar = 0; andar < inimigos.size(); andar++) {
            Personagem inimigo = inimigos.get(random.nextInt(inimigos.size()));
            System.out.println("Andar " + (andar + 1) + ": Você encontrou " + inimigo.getNome() + "!");

            BatalhaController batalha = new BatalhaController(jogador, inimigo);
            boolean venceu = batalha.iniciarBatalha();

            if (venceu) {
                jogador.getStatus().setPontosAcao(jogador.getStatus().getPontosAcao() + 3);
                jogador.getStatus().setVidaAtual(jogador.getStatus().getVidaAtual() + 10);
                System.out.println(jogador.getNome() + " recuperou 10 pontos de vida! (Agora: " + jogador.getStatus().getVidaAtual() + ")");
            } else {
                System.out.println(jogador.getNome() + " foi derrotado na torre!");
                return;
            }
        }
        System.out.println("Parabéns! Você conquistou a Torre dos Deuses!");
    }

    private void carregarInimigos() {
        inimigos.clear(); // limpa a lista antes de adicionar

        System.out.println("Escolha a mitologia do inimigo:");
        System.out.println("1 - Deuses Gregos");
        System.out.println("2 - Deuses Egípcios");
        int inimigo = scanner.nextInt();

        switch (inimigo) {
            case 1 -> {
//                inimigos.add(new Ares());
//                inimigos.add(new Atena());
//                inimigos.add(new Hades());
//                inimigos.add(new Poseidon());
//                inimigos.add(new Zeus());
            }
            case 2 -> {
                inimigos.add(new Anubis());
                inimigos.add(new Horus());
                inimigos.add(new Isis());
                inimigos.add(new Osiris());
                inimigos.add(new Ra());
            }
            default -> {
                System.out.println("Opção inválida, selecionando Deuses Gregos por padrão.");
//                inimigos.add(new Ares());
//                inimigos.add(new Atena());
//                inimigos.add(new Hades());
//                inimigos.add(new Poseidon());
//                inimigos.add(new Zeus());
            }
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