package model.personagens.deuses.gregos;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Ares extends Personagem {

    public Ares(String nome, Atributos atributos, Status status) {
        super(
                "Ares",
                new Atributos(16, 20, 20, 34),
                new Status(new Atributos(16, 20, 20, 34))
        );
    }
}
