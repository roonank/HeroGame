package model.calculadora;

import model.personagens.Atributos;
import model.personagens.Personagem;

public class CalculadoraDano {

    private CalculadoraCritico calculadoraCritico = new CalculadoraCritico();
    private CalculadoraDefesa calculadoraDefesa = new CalculadoraDefesa();

    public int calcularDanoFisico(Personagem atacante, Personagem alvo) {
        int danoBase = atacante.getAtributos().getForca() * 2;

        if (calculadoraCritico.calcularCritico(atacante.getStatus())) {
            danoBase *= 2;
            System.out.println("Ataque Crítico!");
        }

        int danoFinal = calculadoraDefesa.aplicarDefesaPercentual(danoBase, alvo.getStatus());

        return Math.min(0, danoFinal);
    }

    public int calcularDanoMagico(Personagem atacante, Personagem alvo) {
        int danoBase = atacante.getAtributos().getInteligentia() * 2;

        if (calculadoraCritico.calcularCritico(atacante.getStatus())) {
            danoBase *= 2;
            System.out.println("Magia Crítico!");
        }

        int danoFinal = calculadoraDefesa.aplicarDefesaPercentual(danoBase, alvo.getStatus());

        return Math.min(0, danoFinal);
    }
}
