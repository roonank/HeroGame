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
    protected int manaMaxima = 150;
    protected int manaAtual = 150;
    protected int pontosHabilidade = 0;

    public Personagem(String nome, int pontosVida, int forca, int defesa) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.pontosVidaMaximos = pontosVida;
        this.forca = forca;
        this.defesa = defesa;
        this.habilidades = new ArrayList<>();
        this.defendendo = false;
    }

    public int getPontosHabilidade() {
        return pontosHabilidade;
    }

    public void adicionarPontosHabilidade(int qtd) {
        if (qtd > 0) pontosHabilidade += qtd;
    }

    public boolean gastarPontosHabilidade(int qtd) {
        if (qtd <= 0) return false;
        if (pontosHabilidade < qtd) return false;
        pontosHabilidade -= qtd;
        return true;
    }

    public void addForca(int v) { this.forca += Math.max(0, v); }
    public void addDefesa(int v) { this.defesa += Math.max(0, v); }
    public void addVidaMaxima(int v) {
        if (v <= 0) return;
        this.pontosVidaMaximos += v;
        this.pontosVida = Math.min(pontosVidaMaximos, pontosVida + v); // “cura” proporcional
    }

    public void setHabilidades(List<Habilidade> habilidades) {
        this.habilidades = habilidades;
    }
    public int getManaMaxima() {
        return manaMaxima;
    }
    public void setManaMaxima(int manaMaxima) {
        this.manaMaxima = Math.max(0, manaMaxima);
        this.manaAtual  = Math.min(this.manaAtual, this.manaMaxima);
    }
    public int getManaAtual() {
        return manaAtual;
    }
    public void setManaAtual(int manaAtual) {
        this.manaAtual = manaAtual;
    }


    public void resetarMana() {
        this.manaAtual = this.manaMaxima; // começa batalha com 100
    }

    public boolean temMana(int custo) {
        return custo <= manaAtual;
    }

    public boolean gastarMana(int custo) {
        if (custo < 0) custo = 0;
        if (manaAtual >= custo) {
            manaAtual -= custo;
            return true;
        }
        return false;
    }

    public void recuperarMana(int quantidade) {
        if (quantidade <= 0) return;
        manaAtual = Math.min(manaMaxima, manaAtual + quantidade);
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