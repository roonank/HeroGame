package model.habilidades.efeitos;

import model.personagens.Personagem;

import java.util.List;

public abstract class EfeitoStatus {
    protected final String nome;
    protected int turnos;

    public EfeitoStatus(String nome, int turnos) {
        this.nome = nome;
        this.turnos = Math.max(1, turnos);
    }

    public String getNome() { return nome; }
    public int getTurnosRestantes() { return turnos; }

    public void aoAplicar(Personagem alvo, List<String> log) {}
    public void inicioDoTurno(Personagem alvo, List<String> log) {};
    public void fimDoTurno(Personagem alvo, List<String> log) {}
    public boolean impedeAgir() { return false; }
    public int modificarDanoRecebido(int dano) { return dano; }

    public void tick() { turnos--; }
    public boolean expirou() { return turnos <= 0; }
}
