package Controller;

import model.personagens.*;
import model.personagens.herois.*;
import model.personagens.inimigos.deuses.MitologiaEnum;
import model.personagens.inimigos.deuses.egipcios.*;
import model.personagens.inimigos.deuses.gregos.*;

import java.util.ArrayList;
import java.util.List;

public class Jogo {
    private Heroi jogador;
    private final List<Inimigo> inimigos;
    private int andarAtual;

    public Jogo() {
        inimigos = new ArrayList<>();
        andarAtual = 0;
    }
    /*
    public void escolherHeroi(int opcao) {
        switch (opcao) {
            case 1 -> jogador = new Aquiles();
            case 2 -> jogador = new Hercules();
            case 3 -> jogador = new Perseu();
            case 4 -> jogador = new Ragnar();
            case 5 -> jogador = new Gilgamesh();
            default -> jogador = new Aquiles();
        }
    }

    //carrega inimigos por mitolgia
    public void carregarInimigos(int mitologia) {
        inimigos.clear();
        if (mitologia == 1) {
            inimigos.add(new Anubis());
            inimigos.add(new Horus());
            inimigos.add(new Isis());
            inimigos.add(new Osiris());
            inimigos.add(new Ra());
        } else if (mitologia == 2) {
            inimigos.add(new Ares());
            inimigos.add(new Atena());
            inimigos.add(new Hades());
            inimigos.add(new Poseidon());
            inimigos.add(new Zeus());
        }
        andarAtual = 0;
    }

     */

    // Escolhe o herói com base no enum
    public void escolherHeroi(HeroiEnum heroi) {
        switch (heroi) {
            case AQUILES -> jogador = new Aquiles();
            case HERCULES -> jogador = new Hercules();
            case PERSEU -> jogador = new Perseu();
            case RAGNAR -> jogador = new Ragnar();
            case GILGAMESH -> jogador = new Gilgamesh();
        }
    }

    // Carrega inimigos com base na mitologia escolhida
    public void carregarInimigos(MitologiaEnum mitologia) {
        inimigos.clear();
        switch (mitologia) {
            case EGIPCIA -> {
                inimigos.add(new Anubis());
                inimigos.add(new Horus());
                inimigos.add(new Isis());
                inimigos.add(new Osiris());
                inimigos.add(new Ra());
            }
            case GREGA -> {
                inimigos.add(new Ares());
                inimigos.add(new Atena());
                inimigos.add(new Hades());
                inimigos.add(new Poseidon());
                inimigos.add(new Zeus());
            }
        }
        andarAtual = 0;
    }

    public Batalha proximaBatalha() {
        if (andarAtual >= inimigos.size()) return null;
        Inimigo inimigo = inimigos.get(andarAtual);
        andarAtual++;
        boolean eUltimoAndar = (andarAtual == getTotalAndares());
        return new Batalha(jogador, inimigo, !eUltimoAndar);

    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }

    public Heroi getJogador() {
        return jogador;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public int getTotalAndares() {
        return inimigos.size();
    }

    public boolean jogoFinalizado() {
        return andarAtual >= inimigos.size() || !jogador.estaVivo();
    }
}
