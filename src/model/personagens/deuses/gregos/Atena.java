package model.personagens.deuses.gregos;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Atena extends Personagem {

    public Atena(String nome, Atributos atributos, Status status) {
        super(
                "Atena",
                new Atributos(20, 34, 20, 16),
                new Status(new Atributos(20, 34, 20, 16))
        );
    }
}
