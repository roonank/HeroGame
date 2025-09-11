package model.personagens.deuses.egipcios;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Osiris extends Personagem {

    public Osiris() {
        super(
                "Osíris",
                new Atributos(18, 15, 18, 20),
                new Status(new Atributos(18, 15, 18, 20))
        );
    }
}