package controller;

import controller.Emun.MenuJogoEnum;
import model.habilidades.Habilidade;
import model.personagens.Heroi;

import java.util.List;

import static util.Cores.*;

public class MenuBatalha {

    private final UtilitarioMenu utilitario;

    public MenuBatalha(UtilitarioMenu utilitario) {
        this.utilitario = utilitario;
    }

    public void turnoJogador(Heroi heroi, Batalha batalha) {
        System.out.println("\n" + batalha.getStatusVida());
        MenuJogoEnum acao = utilitario.menuOpcao(
                "Escolha a ação para " + batalha.getJogador().getNome(),
                MenuJogoEnum.values()
        );

        switch (acao) {
            case ATACAR -> batalha.executarTurno(Batalha.AcaoBatalha.ATACAR, null);
            case USAR_HABILIDADE -> turnoHabilidade(heroi, batalha);
            case DEFENDER -> batalha.executarTurno(Batalha.AcaoBatalha.DEFENDER, null);
            case FUGIR -> batalha.executarTurno(Batalha.AcaoBatalha.FUGIR, null);
        }
    }

    private void turnoHabilidade(Heroi heroi, Batalha batalha) {
        List<Habilidade> habilidades = heroi.getHabilidades();
        if (habilidades.isEmpty()) {
            System.out.println(VERMELHO + "Você não possui habilidades. Atacando normalmente." + RESET);
            batalha.executarTurno(Batalha.AcaoBatalha.ATACAR, null);
            return;
        }

        System.out.println(AMARELO + "\nEscolha uma habilidade:" + RESET);
        for (int i = 0; i < habilidades.size(); i++) {
            Habilidade h = habilidades.get(i);
            System.out.println(CIANO + " " +  (i + 1) + " - " + h.getNome() + " (Custo: " + h.getCustoMana() + " mana)" + RESET);
        }

        int escolhaHabilidade = utilitario.lerOpcao(1, habilidades.size());
        Habilidade habilidadeSelecionada = habilidades.get(escolhaHabilidade - 1);
        batalha.executarTurno(Batalha.AcaoBatalha.USAR_HABILIDADE, habilidadeSelecionada);
    }

    public void turnoInimigo(Batalha batalha) {
        System.out.println(VERMELHO + "\n--- Vez de " + batalha.getInimigo().getNome() + " ---" + RESET);
        batalha.executarTurno(null, null);
    }

    public void exibirLog(Batalha batalha) {
        for (String log : batalha.getLogBatalha()) {
            System.out.println("\n" + log);
        }
    }
}