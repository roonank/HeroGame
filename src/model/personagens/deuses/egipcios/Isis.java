package model.personagens.deuses.egipcios;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Isis extends Personagem {
    public Isis() {
        super(
                "Isis",
                new Atributos(6, 15, 16, 12),
                new Status(new Atributos(6, 15, 16, 12))
        );
    }
}