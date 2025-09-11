package model.personagens.herois;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Perseu  extends Personagem {

    public Perseu () {
        super(
                "Perseu ",
                new Atributos(10, 8, 17, 5),
                new Status( new Atributos(10, 8, 17, 5))
        );
    }
}
