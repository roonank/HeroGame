package model.calculadora;

import model.personagens.Status;

public class CalculadoraDefesa {

    public int aplicarDefesaPercentual(int dano, Status statusDefensor) {
        double reducao = statusDefensor.getDefesa() * 0.05;
        int danoFinal = (int) (dano *(1- reducao));
        return Math.max(0, danoFinal);
    }
}
