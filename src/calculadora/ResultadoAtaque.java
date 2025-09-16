package calculadora;

public record ResultadoAtaque(boolean acertou, boolean critico, int dano, double multiplicadorCritico,
                              double chanceAcertoUsada, double chanceCriticoUsada, double danoBrutoAntesDefesa,
                              double fatorReducaoDefesa) {
}
