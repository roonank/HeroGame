package Controller;

import model.interfaces.MenuOpcoes;

public enum MenuJogoEnum implements MenuOpcoes {
    ATACAR("Atacar"),
    USARHABILIDADE("Usar habilidade"),
    DEFENDER("Defender"),
    FUGIT("Fugir");

    private final String nome;

    MenuJogoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
