package model.personagens.herois;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Hercules extends Personagem {

    public Hercules() {
        super(
                "Hércules",
                new Atributos(20, 12, 8, 10),
                new Status(new Atributos(20, 12, 8, 10))
        );
    }
}
