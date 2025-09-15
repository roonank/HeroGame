package calculadora;

import model.habilidades.Habilidade;
import model.personagens.Personagem;

public class CalculoDano {
    private static final double DEFESA_BASE_CURVA = 100.0;

    private CalculoDano() {
    }

    public static int calcularDano(Personagem atacante, Personagem defensor, Habilidade habilidade) {
        return calcularDetalhado(atacante, defensor, habilidade).dano;
    }

    public static ResultadoAtaque calcularDetalhado(Personagem atacante, Personagem defensor, Habilidade habilidade) {
        double chanceAcertoConfig = habilidade.getChanceAcerto();
        double chanceAcertoUsada = (chanceAcertoConfig >= 0.0 && chanceAcertoConfig <= 1.0)
                ? chanceAcertoConfig
                : (0.70 + Math.random() * 0.30);

        boolean acertou = Math.random() < chanceAcertoUsada;
        if (!acertou) {
            return new ResultadoAtaque(false, false, 0, 1.0, chanceAcertoUsada,
                    habilidade.getChanceCritico(), 0.0, 0.0);
        }

        double danoBruto = habilidade.getDanoBase() + (atacante.getForca() * habilidade.getMultiplicador());
        if (danoBruto < 0) danoBruto = 0;

        double chanceCriticoUsada = habilidade.getChanceCritico();
        boolean critico = Math.random() < chanceCriticoUsada;
        double multCritico = 1.0;
        if (critico) {
            multCritico = 1.0 + Math.random();
            danoBruto *= multCritico;
        }

        int defesaAlvo = Math.max(0, defensor.getDefesa());
        double fatorReducao = DEFESA_BASE_CURVA / (DEFESA_BASE_CURVA + defesaAlvo);
        double danoPosDefesa = danoBruto * fatorReducao;

        int danoFinal = (int) Math.round(danoPosDefesa);
        if (danoFinal < 1) danoFinal = 1;


        return new ResultadoAtaque(true, critico, danoFinal, multCritico, chanceAcertoUsada,
                chanceCriticoUsada, danoBruto / multCritico, fatorReducao);
    }
}
