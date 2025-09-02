package model.personagens.herois;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Ragnar extends Personagem {

    public Ragnar() {
        super(
                "Ragnar",
                new Atributos(13, 18, 10, 8),
                new Status(new Atributos(13, 18, 10, 8))
        );
    }
}
