package model.personagens;

import model.interfaces.IExperienciavel;

public abstract class Inimigo extends Personagem implements IExperienciavel {
    protected int nivel;
    protected int experiencia;
    protected int experienciaProximoNivel;
    protected int pontosDisponiveis;

    public Inimigo(String nome, int pontosVida, int forca, int defesa) {
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
        pontosVidaMaximos += 10;
        pontosVida = pontosVidaMaximos; // Cura completa no nível
    }

    public int getPontosDisponiveis() {
        return pontosDisponiveis;
    }

    public void adicionarPontosDisponiveis(int pontos) {
        pontosDisponiveis += pontos;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getExperienciaProximoNivel() {
        return experienciaProximoNivel;
    }

    public void setExperienciaProximoNivel(int experienciaProximoNivel) {
        this.experienciaProximoNivel = experienciaProximoNivel;
    }

    public void setPontosDisponiveis(int pontosDisponiveis) {
        this.pontosDisponiveis = pontosDisponiveis;
    }
}