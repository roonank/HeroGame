package Controller;

import model.interfaces.MenuOpcoes;

public enum MenuJogoEnum implements MenuOpcoes {
    ATACAR("Atacar"),
    USAR_HABILIDADE("Usar habilidade"),
    DEFENDER("Defender"),
    FUGIR("Fugir");

    private final String nome;

    MenuJogoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
