package calculadora;

public class ResultadoAtaque {
    public final boolean acertou;
    public final boolean critico;
    public final int dano;
    public final double multiplicadorCritico;
    public final double chanceAcertoUsada;
    public final double chanceCriticoUsada;
    public final double danoBrutoAntesDefesa;
    public final double fatorReducaoDefesa;

    public ResultadoAtaque(
            boolean acertou, boolean critico, int dano,
            double multiplicadorCritico, double chanceAcertoUsada, double chanceCriticoUsada,
            double danoBrutoAntesDefesa, double fatorReducaoDefesa) {
        this.acertou = acertou;
        this.critico = critico;
        this.dano = dano;
        this.multiplicadorCritico = multiplicadorCritico;
        this.chanceAcertoUsada = chanceAcertoUsada;
        this.chanceCriticoUsada = chanceCriticoUsada;
        this.danoBrutoAntesDefesa = danoBrutoAntesDefesa;
        this.fatorReducaoDefesa = fatorReducaoDefesa;
    }
}
