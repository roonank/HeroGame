package model.personagens.Emun;

import model.interfaces.MenuOpcoes;

public enum MitologiaEnum implements MenuOpcoes {
    EGIPCIA("Egípcia"),
    GREGA("Grega");

    private final String nome;

    MitologiaEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
