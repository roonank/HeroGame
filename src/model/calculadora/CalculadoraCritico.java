package model.calculadora;

import model.personagens.Status;

public class CalculadoraCritico {

    public boolean calcularCritico(Status status) {
        double chance = status.getChanceCritico();
        return Math.random() < chance;
    }
}
