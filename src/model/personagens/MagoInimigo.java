package model.personagens;

import calculadora.CalculoDano;
import model.habilidades.Habilidade;
import model.habilidades.TipoHabilidade;

import java.util.List;

public class MagoInimigo extends Inimigo {
    private int pontosMagia;

    // Construtor com habilidades
    public MagoInimigo(String nome, int vida, int forca, int defesa, int pontosMagia, List<Habilidade> habilidades) {
        super(nome, vida, forca, defesa);
        this.pontosMagia = pontosMagia;


        // Apenas adiciona as habilidades passadas como parâmetro
        if (habilidades != null) {
            for (Habilidade habilidade : habilidades) {
                adicionarHabilidade(habilidade);
            }
        }
    }

    // Construtor sem habilidades
    public MagoInimigo(String nome, int vida, int forca, int defesa, int pontosMagia) {
        super(nome, vida, forca, defesa);
        this.pontosMagia = pontosMagia;
        // Não adiciona nenhuma habilidade - deixa para as classes filhas
    }

    public MagoInimigo() {
        super("Mago", 100, 12, 6);
    }

    @Override
    public boolean podeUsarHabilidade(Habilidade habilidade) {
        return habilidade.getTipo() == TipoHabilidade.MAGICO &&
                pontosMagia >= habilidade.getCustoMana();
    }

    @Override
    public void usarHabilidade(Habilidade habilidade, model.interfaces.ICombatente alvo) {
        if (habilidade == null) {
            System.out.println(getNome() + " não pode usar esta habilidade!");
            return;
        }

        if (!(alvo instanceof Personagem)) {
            System.out.println(getNome() + " não pode atingir este alvo com " + habilidade.getNome() + ".");
            return;
        }

        Personagem defensor = (Personagem) alvo;

        int dano = CalculoDano.calcularDano(this, defensor, habilidade);
        alvo.receberDano(dano);

        System.out.println(getNome() + " usa " + habilidade.getNome() + " e causa " + dano + " de dano!");
    }

    // Habilidade única do Mago - Explosão Arcana
    public void explosaoArcana(model.interfaces.ICombatente alvo) {
        if (pontosMagia >= 10) {
            pontosMagia -= 10;
            int dano = getForca() * 2 + (int) (Math.random() * 10);
            alvo.receberDano(dano);
            System.out.println(getNome() + " libera uma Explosão Arcana causando " +
                    dano + " de dano devastador!");
        } else {
            System.out.println(getNome() + " não tem magia suficiente para esta habilidade!");
        }
    }

    public int getPontosMagia() {
        return pontosMagia;
    }

    public void setPontosMagia(int pontosMagia) {
        this.pontosMagia = pontosMagia;
    }

}