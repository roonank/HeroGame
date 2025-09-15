package calculadora;

import model.habilidades.Habilidade;
import model.personagens.Personagem;

public class CalculoDano {
    private static final int DEFESA_BASE_CURVA = 100;

    private CalculoDano() {}

    public static int calcularDano(Personagem atacante, Personagem defensor, Habilidade habilidade) {
        if (Math.random() >= habilidade.getChanceAcerto()) {
            return 0;
        }

        double danoBruto = habilidade.getDanoBase() + (atacante.getForca() * habilidade.getMultiplicador());

        if (danoBruto < 0) danoBruto = 0;

        boolean critico = Math.random() < habilidade.getChanceCritico();
        if (critico) {
            double multiplicadorCritico = 1.0 + (Math.random());
            danoBruto *= multiplicadorCritico;
        }

        int defesaAlvo = defensor.getDefesa();
        double fatorReducao = (double) DEFESA_BASE_CURVA / (DEFESA_BASE_CURVA + Math.max(0, defesaAlvo));
        double danoPosDefesa = danoBruto * fatorReducao;

        int danoFinal = (int) Math.round(danoPosDefesa);
        if (danoFinal < 1) danoFinal = 1;

        return danoFinal;
    }
}
