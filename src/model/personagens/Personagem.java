package model.personagens;

import model.interfaces.ICombatente;
import model.habilidades.Habilidade;
import model.interfaces.IHabilidade;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem implements ICombatente, IHabilidade {
    protected String nome;
    protected int pontosVida;
    protected int pontosVidaMaximos;
    protected int forca;
    protected int defesa;
    protected List<Habilidade> habilidades;
    protected boolean defendendo;

    public Personagem(String nome, int pontosVida, int forca, int defesa) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.pontosVidaMaximos = pontosVida;
        this.forca = forca;
        this.defesa = defesa;
        this.habilidades = new ArrayList<>();
        this.defendendo = false;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getVidaAtual() {
        return pontosVida;
    }

    @Override
    public int getVidaMaxima() {
        return pontosVidaMaximos;
    }

    @Override
    public boolean estaVivo() {
        return pontosVida > 0;
    }

    @Override
    public int receberDano(int dano) {
        int aplicado = dano;

        if (defendendo) {
            // Ex.: bloqueia 60% do dano (ajuste como quiser)
            aplicado = (int) Math.ceil(dano * 0.40);
            defendendo = false; // consome a postura
        }

        pontosVida = Math.max(0, pontosVida - aplicado);
        return aplicado;
    }

    @Override
    public int calcularAtaque() {
        return forca + (int) (Math.random() * 4); // Variação 0-3
    }

    @Override
    public int calcularDefesa() {
        return defesa + (defendendo ? defesa : 0);
    }

    @Override
    public List<Habilidade> getHabilidades() {
        return new ArrayList<>(habilidades);
    }

    @Override
    public void adicionarHabilidade(Habilidade habilidade) {
        if (habilidade != null) {
            habilidades.add(habilidade);
        }
    }

    public void defender() {
        this.defendendo = true;
    }

    public void curar(int quantidade) {
        pontosVida = Math.min(pontosVidaMaximos, pontosVida + quantidade);
    }

    // Getters para subclasses
    public int getForca() {
        return forca;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }
}