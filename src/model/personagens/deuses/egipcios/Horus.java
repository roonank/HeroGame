package model.personagens.deuses.egipcios;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Horus extends Personagem {
    public Horus() {
        super(
                "Hórus",
                new Atributos(24, 27, 13, 11),
                new Status(new Atributos(24, 27, 13, 11))
        );
    }
}
