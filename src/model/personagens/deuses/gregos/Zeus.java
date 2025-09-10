package model.personagens.deuses.gregos;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Zeus extends Personagem {

    public Zeus(String nome, Atributos atributos, Status status) {
        super(
                "Zeus",
                new Atributos(34, 20, 20, 16),
                new Status(new Atributos(34, 20, 20, 16))
        );
    }
}
