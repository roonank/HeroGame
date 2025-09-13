package model.personagens;

import model.interfaces.IExperienciavel;

public abstract class Heroi extends Personagem implements IExperienciavel {
    protected int nivel;
    protected int experiencia;
    protected int experienciaProximoNivel;
    protected int pontosDisponiveis;

    public Heroi(String nome, int pontosVida, int forca, int defesa) {
        super(nome, pontosVida, forca, defesa);
        this.nivel = 1;
        this.experiencia = 0;
        this.experienciaProximoNivel = 100;
        this.pontosDisponiveis = 10;
    }

    @Override
    public int getNivel() {
        return nivel;
    }

    @Override
    public int getExperiencia() {
        return experiencia;
    }

    @Override
    public void ganharExperiencia(int quantidade) {
        experiencia += quantidade;
        while (podeSubirNivel()) {
            subirNivel();
        }
    }

    @Override
    public boolean podeSubirNivel() {
        return experiencia >= experienciaProximoNivel;
    }

    @Override
    public void subirNivel() {
        nivel++;
        experiencia -= experienciaProximoNivel;
        experienciaProximoNivel = (int) (experienciaProximoNivel * 1.5);
        pontosDisponiveis += 3;
        // Aumento base por nível
        pontosVidaMaximos += 10;
        pontosVida = pontosVidaMaximos; // Cura completa no nível
    }

    public void distribuirPontos(String atributo, int quantidade) {
        if (quantidade > pontosDisponiveis || quantidade <= 0) return;

        switch (atributo.toLowerCase()) {
            case "forca" -> forca += quantidade;
            case "defesa" -> defesa += quantidade;
            case "vida" -> {
                pontosVidaMaximos += quantidade * 5;
                pontosVida += quantidade * 5;
            }
        }
        pontosDisponiveis -= quantidade;
    }

    public int getPontosDisponiveis() {
        return pontosDisponiveis;
    }

    public void adicionarPontosDisponiveis(int pontos) {
        pontosDisponiveis += pontos;
    }
}
