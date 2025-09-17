package Controller;

import model.habilidades.efeitos.Atordoado;
import model.habilidades.efeitos.TipoEfeito;
import model.habilidades.TipoHabilidade;
import model.interfaces.ICombatente;
import model.interfaces.IHabilidade;

import static util.Cores.*;

import model.personagens.Heroi;
import model.personagens.Personagem;
import model.habilidades.Habilidade;

import java.util.Random;
import java.util.List;

public class Batalha {
    private ICombatente jogador;
    private ICombatente inimigo;
    private final Random random;
    private boolean batalhaFinalizada;
    private boolean jogadorVenceu;
    private final List<String> logBatalha;
    private boolean jogadorNaVez;
    private final boolean premiaAoVencer;

    private static final Habilidade ATAQUE_BASICO =
            new Habilidade("Ataque Básico", 0, 3, 1.0, TipoEfeito.DANO, TipoHabilidade.FISICO, 0.9, 0.10);

    public enum AcaoBatalha {ATACAR, USAR_HABILIDADE, DEFENDER, FUGIR}

    public Batalha(ICombatente jogador, ICombatente inimigo, boolean premiaAoVencer) {
        this.jogador = jogador;
        this.inimigo = inimigo;
        this.premiaAoVencer = premiaAoVencer;
        this.random = new Random();
        this.batalhaFinalizada = false;
        this.logBatalha = new java.util.ArrayList<>();
        this.jogadorNaVez = true;

        if (jogador instanceof Personagem pj) pj.resetarMana();
        if (inimigo instanceof Personagem pi) pi.resetarMana();
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

        jogadorNaVez = !jogadorNaVez; // troca o turno
    }

    // ---------- Helpers de efeitos ----------
    private boolean inicioDoTurnoPodeAgir(ICombatente combatente) {
        if (combatente instanceof Personagem p) {
            p.processarEfeitosInicio(logBatalha);     // DOTs/mensagens
            if (p.impedidoDeAgir()) {
                logBatalha.add(AMARELO + p.getNome() + " está ATORDOADO e perde o turno!" + RESET);
                p.processarEfeitosFim(logBatalha);    // tick/expiração mesmo perdendo o turno
                return false;
            }
        }
        return true;
    }

    private void fimDoTurno(ICombatente combatente) {
        if (combatente instanceof Personagem p) {
            p.processarEfeitosFim(logBatalha);
        }
    }

    private void tentarAplicarEfeito(Habilidade habilidade, Personagem alvo) {
        if (habilidade == null) return;
        var tipo = habilidade.getTipoEfeito();
        if (tipo == TipoEfeito.DANO || tipo == TipoEfeito.NENHUM) return;

        if (habilidade.getDuracaoEfeito() <= 0 || habilidade.getChanceEfeito() <= 0.0) return;
        if (random.nextDouble() >= habilidade.getChanceEfeito()) return;

        switch (tipo) {
            case STUN -> alvo.aplicarEfeito(new Atordoado(habilidade.getDuracaoEfeito()), logBatalha);
            // FUTURO: VENENO/QUEIMAR/CONGELAR aqui
        }
    }
    // ----------------------------------------

    private void executarAcaoComLog(ICombatente atacante, ICombatente defensor, AcaoBatalha acao, Habilidade habilidade) {
        // ✅ checa efeitos ANTES de qualquer ação
        if (!inicioDoTurnoPodeAgir(atacante)) return;

        switch (acao) {
            case ATACAR -> {
                if (!(atacante instanceof Personagem atk) || !(defensor instanceof Personagem def)) {
                    logBatalha.add(VERMELHO + "Alvo/atacante inválido para ataque básico." + RESET);
                    break;
                }
                var r = calculadora.CalculoDano.calcularDetalhado(atk, def, ATAQUE_BASICO);
                if (!r.acertou()) {
                    logBatalha.add(AMARELO + atk.getNome() + " tentou atacar mas ERROU (" +
                            Math.round(r.chanceAcertoUsada() * 100) + "%)." + RESET);
                    break;
                }
                int aplicado = def.receberDano(r.dano());
                if (r.critico())
                    logBatalha.add(ROXO + "CRÍTICO x" + String.format("%.2f", r.multiplicadorCritico()) + "!" + RESET);
                logBatalha.add(VERMELHO + atk.getNome() + " atacou " + def.getNome() + " causando " +
                        (aplicado > 0 ? aplicado : r.dano()) + " de dano!" + RESET);
            }

            case DEFENDER -> {
                if (atacante instanceof Personagem p) {
                    p.defender();
                    p.recuperarMana(10); // opcional
                    logBatalha.add(AMARELO + p.getNome() + " assumiu postura defensiva e recuperou 10 de mana!" + RESET);
                }
            }

            case USAR_HABILIDADE -> {
                if (!(atacante instanceof Personagem atk) || !(defensor instanceof Personagem def)
                        || !(atacante instanceof IHabilidade h) || habilidade == null
                        || !h.podeUsarHabilidade(habilidade)) {
                    logBatalha.add(VERMELHO + atacante.getNome() + " não conseguiu usar a habilidade! Atacando normalmente." + RESET);
                    executarAcaoComLog(atacante, defensor, AcaoBatalha.ATACAR, null);
                    break;
                }

                int custo = habilidade.getCustoMana();
                if (!atk.temMana(custo)) {
                    logBatalha.add(AMARELO + atk.getNome() + " não tem mana suficiente (" +
                            atk.getManaAtual() + "/" + atk.getManaMaxima() + "). " +
                            "Habilidade '" + habilidade.getNome() + "' custa " + custo + ". " + RESET +
                            "Atacando normalmente.");
                    executarAcaoComLog(atacante, defensor, AcaoBatalha.ATACAR, null);
                    break;
                }
                atk.gastarMana(custo);

                var r = calculadora.CalculoDano.calcularDetalhado(atk, def, habilidade);
                if (!r.acertou()) {
                    logBatalha.add(AMARELO + atk.getNome() + " usa " + habilidade.getNome() +
                            " mas ERRA (" + Math.round(r.chanceAcertoUsada() * 100) + "%). " +
                            "Mana: " + atk.getManaAtual() + "/" + atk.getManaMaxima() + RESET);
                    break;
                }

                int aplicado = def.receberDano(r.dano());
                if (r.critico())
                    logBatalha.add(ROXO + "CRÍTICO x" + String.format("%.2f", r.multiplicadorCritico()) + "!" + RESET);

                tentarAplicarEfeito(habilidade, def);

                logBatalha.add(CIANO + atk.getNome() + " usou " + habilidade.getNome() + " e causou " +
                        (aplicado > 0 ? aplicado : r.dano()) + " de dano! " +
                        AZUL + "(Mana: " + atk.getManaAtual() + "/" + atk.getManaMaxima() + ")" + RESET);
            }

            case FUGIR -> {
                int chance = random.nextInt(5);                 // 0..4 => ~20% em média
                if (random.nextInt(10) < chance) {
                    batalhaFinalizada = true;
                    jogadorVenceu = (atacante == jogador);      // quem fugiu “vence” para o seu fluxo
                    logBatalha.add(VERDE + atacante.getNome() + " fugiu da batalha!" + RESET);
                } else {
                    logBatalha.add(VERMELHO + atacante.getNome() + " tentou fugir mas falhou!" + RESET);
                }
            }
        }

        // ✅ tick/expiração no FIM do turno (se não perdeu o turno)
        fimDoTurno(atacante);
    }

    private AcaoBatalha escolherAcaoInimigo() {
        return random.nextInt(100) < 70 ? AcaoBatalha.ATACAR : AcaoBatalha.DEFENDER;
    }

    private void finalizarBatalha(boolean vitoria) {
        batalhaFinalizada = true;
        jogadorVenceu = vitoria;
        if (vitoria) {
            if (premiaAoVencer && jogador instanceof Heroi h) {
                h.adicionarPontosHabilidade(10);
                logBatalha.add(VERDE + "+10 pontos de habilidade!" + RESET);
            }
            logBatalha.add(VERDE + "Vitória!" + RESET);
        } else {
            logBatalha.add(VERMELHO + "Derrota..." + RESET);
        }
    }

    public String getStatusVida() {
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
