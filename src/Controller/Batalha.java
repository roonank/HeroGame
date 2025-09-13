package Controller;

import model.interfaces.ICombatente;
import model.interfaces.IHabilidade;
import static util.Cores.*;
import model.personagens.Personagem;
import model.habilidades.Habilidade;

import java.util.Random;
import java.util.List;

public class Batalha {
    private ICombatente jogador;
    private ICombatente inimigo;
    private Random random;
    private boolean batalhaFinalizada;
    private boolean jogadorVenceu;
    private List<String> logBatalha;
    private boolean jogadorNaVez;

    public enum AcaoBatalha {
        ATACAR, USAR_HABILIDADE, DEFENDER, FUGIR
    }

    public Batalha(ICombatente jogador, ICombatente inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
        this.random = new Random();
        this.batalhaFinalizada = false;
        this.logBatalha = new java.util.ArrayList<>();
        this.jogadorNaVez = true;
    }

    public void executarTurno(AcaoBatalha acaoJogador, Habilidade habilidade) {
        logBatalha.clear();

        if (batalhaFinalizada) return;

        if (jogadorNaVez) {
            executarAcaoComLog(jogador, inimigo, acaoJogador, habilidade);
        } else {
            executarAcaoComLog(inimigo, jogador, escolherAcaoInimigo(), null);
        }

        if (!jogador.estaVivo()) finalizarBatalha(false);
        else if (!inimigo.estaVivo()) finalizarBatalha(true);

        jogadorNaVez = !jogadorNaVez; // Troca o turno
    }

    private void executarAcaoComLog(ICombatente atacante, ICombatente defensor, AcaoBatalha acao, Habilidade habilidade) {
        switch (acao) {
            case ATACAR -> {
                int dano = atacante.calcularAtaque();
                dano = Math.max(0, dano - defensor.calcularDefesa());

                if (random.nextDouble() < 0.2) {
                    dano = (int) (dano * 1.5);
                    logBatalha.add(ROXO + atacante.getNome() + " desferiu um GOLPE CRÍTICO!" + RESET);
                }

                defensor.receberDano(dano);
                logBatalha.add(VERMELHO + atacante.getNome() + " atacou " + defensor.getNome() + " causando " + dano + " de dano!" + RESET);
            }
            case DEFENDER -> {
                if (atacante instanceof Personagem p) {
                    p.defender();
                    logBatalha.add(AMARELO + atacante.getNome() + " assumiu postura defensiva!" + RESET);
                }
            }
            case USAR_HABILIDADE -> {
                if (atacante instanceof IHabilidade h && habilidade != null && h.podeUsarHabilidade(habilidade)) {
                    h.usarHabilidade(habilidade, defensor);
                    logBatalha.add(CIANO + atacante.getNome() + " usou " + habilidade.getNome() + "!" + RESET);
                } else {
                    logBatalha.add(VERMELHO + atacante.getNome() + " não conseguiu usar a habilidade! Atacando normalmente." + RESET);
                    executarAcaoComLog(atacante, defensor, AcaoBatalha.ATACAR, null);
                }
            }
            case FUGIR -> {
                int chance = 50;
                if (random.nextInt(100) < chance) {
                    batalhaFinalizada = true;
                    jogadorVenceu = (atacante == jogador);
                    logBatalha.add(VERDE + atacante.getNome() + " fugiu da batalha!" + RESET);
                } else {
                    logBatalha.add(VERMELHO + atacante.getNome() + " tentou fugir mas falhou!" + RESET);
                }
            }
        }
    }

    private AcaoBatalha escolherAcaoInimigo() {
        return random.nextInt(100) < 70 ?
                AcaoBatalha.ATACAR : AcaoBatalha.DEFENDER;
    }

    private void finalizarBatalha(boolean vitoria) {
        batalhaFinalizada = true;
        jogadorVenceu = vitoria;
        if (vitoria) logBatalha.add(VERDE + "Vitória!" + RESET);
        else logBatalha.add(VERMELHO + "Derrota..." + RESET);
    }

    public String getStatusVida() {
        return jogador.getNome() + ": " + jogador.getVidaAtual() + "/" + jogador.getVidaMaxima() +
                " | " + inimigo.getNome() + ": " + inimigo.getVidaAtual() + "/" + inimigo.getVidaMaxima();
    }

    public String getStatusVidaColorido() {
        return VERDE + jogador.getNome() + ": " + jogador.getVidaAtual() + "/" + jogador.getVidaMaxima() + RESET +
                " | " + VERMELHO + inimigo.getNome() + ": " + inimigo.getVidaAtual() + "/" + inimigo.getVidaMaxima() + RESET;
    }

    public List<String> getLogBatalha() {
        return new java.util.ArrayList<>(logBatalha);
    }

    public boolean isBatalhaFinalizada() {
        return batalhaFinalizada;
    }

    public boolean isJogadorVencedor() {
        return jogadorVenceu;
    }

    public boolean isJogadorNaVez() {
        return jogadorNaVez;
    }

    public ICombatente getJogador() {
        return jogador;
    }

    public ICombatente getInimigo() {
        return inimigo;
    }
}