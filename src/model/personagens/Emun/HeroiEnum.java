package model.personagens.Emun;

import model.interfaces.MenuOpcoes;

public enum HeroiEnum implements MenuOpcoes {
    AQUILES("Aquiles"),
    HERCULES("Hércules"),
    PERSEU("Perseu"),
    RAGNAR("Ragnar"),
    GILGAMESH("Gilgamesh");

    private final String nome;

    HeroiEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
