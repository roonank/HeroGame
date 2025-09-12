package model.personagens.deuses.egipcios;

import model.personagens.Atributos;
import model.personagens.Personagem;
import model.personagens.Status;

public class Ra extends Personagem {
    public Ra() {
        super(
                "Rá",
                new Atributos(17, 12, 20, 16),
                new Status(new Atributos(17, 12, 20, 16))
        );
    }
}