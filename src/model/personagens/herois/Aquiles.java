package model.personagens.herois;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Aquiles extends Personagem {

    public Aquiles() {
        super(
                "Aquiles",
                new Atributos(13, 18, 10, 8),
                new Status(new Atributos(13, 18, 10, 8))
        );
    }
}
