package model.personagens.deuses.gregos;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Hades extends Personagem {

    public Hades(String nome, Atributos atributos, Status status) {
        super(
                "Hades",
                new Atributos(20, 34, 20, 16),
                new Status(new Atributos(20, 34, 20, 16))
        );
    }
}
