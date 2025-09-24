package model.interfaces;

public interface ICombatente {
    String getNome();
    int getVidaAtual();
    int getVidaMaxima();
    boolean estaVivo();
    int receberDano(int dano);
    int calcularAtaque();
    int calcularDefesa();
}