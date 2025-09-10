package model.personagens.deuses.gregos;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Poseidon extends Personagem {


    public Poseidon(String nome, Atributos atributos, Status status) {
        super(
                "Poseidon",
                new Atributos(20, 16, 34, 20),
                new Status(new Atributos(20, 16, 34, 20))
        );
    }
}
