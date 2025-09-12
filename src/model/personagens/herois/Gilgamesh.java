package model.personagens.herois;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Gilgamesh extends Personagem {

    public Gilgamesh() {
        super(
                "Gilgamesh",
                new Atributos(10, 8, 17, 5),
                new Status(new Atributos(10, 8, 17, 5))
        );
    }
}