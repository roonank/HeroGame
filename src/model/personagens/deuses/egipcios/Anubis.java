package model.personagens.deuses.egipcios;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Anubis extends Personagem {
    public Anubis() {
        super(
                "Anúbis",
                new Atributos(21, 24, 11, 11),
                new Status(new Atributos(21, 24, 11, 11))
        );
    }
}