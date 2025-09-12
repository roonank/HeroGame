package model.personagens;

import model.calculadora.CalculadoraDano;
import model.habilidades.Habilidade;
import model.interfaces.IAtacante;
import model.interfaces.IAtacavel;

public class Personagem implements IAtacante, IAtacavel {
    protected String nome;
    protected Atributos atributos;
    protected Status status;
    protected Habilidade habilidadeEspecial;
    protected int pontosDisponiveis;

    public Personagem(String nome, Atributos atributos, Status status) {
        this.nome = nome;
        this.atributos = atributos;
        this.status = new Status(atributos);
        this.pontosDisponiveis = 50;
    }

    public void ataqueBasico(Personagem inimigo) {
        int dano = 10;
        inimigo.receberDano(dano);
        System.out.println(nome + " realizou um ataque básico e causou " + dano + " de dano em " + inimigo.getNome());
    }

    @Override
    public void receberDano(int dano) {
        status.reduzirVida(dano);
    }

    @Override
    public boolean estaVivo() {
        return status.getVidaAtual() > 0;
    }

    public String getNome() {
        return nome;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public Status getStatus() {
        return status;
    }

    public int getPontosDisponiveis() {
        return pontosDisponiveis;
    }

    public void distribuirPontos(String atributo, int quantidade) {
        if (quantidade <= pontosDisponiveis) {
            switch (atributo.toLowerCase()) {
                case "forca" -> this.atributos.aumentarForca(quantidade);
                case "agilidade" -> this.atributos.aumentarAgilidade(quantidade);
                case "inteligencia" -> this.atributos.aumentarInteligencia(quantidade);
                case "constituicao" -> this.atributos.aumentarConstituicao(quantidade);
                default -> throw new IllegalArgumentException("Atributo inválido!");
            }
            this.pontosDisponiveis -= quantidade;
        } else {
            System.out.println("Pontos insuficientes para distribuir!");
        }
    }


    @Override
    public void atacar(Personagem alvo) {
        int dano = new CalculadoraDano().calcularDanoFisico(this, alvo);
        System.out.println(nome + " atacou " + alvo.getNome() + " causando " + dano + " de dano!");
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, Personagem alvo) {

    }

}
