package model.habilidades;

import model.personagens.Personagem;

public abstract class Habilidade {
    protected String nome;
    protected int custoPontosAcao;

    public Habilidade(String nome, int custoPontosAcao) {
        this.nome = nome;
        this.custoPontosAcao = custoPontosAcao;
    }

    public String getNome() { return nome; }
    public int getCustoPontosAcao() { return custoPontosAcao; }

    public abstract void usar(Personagem atacante, Personagem alvo);

}
