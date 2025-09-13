import Controller.MenuConsole;

public class Main {
    public static void main(String[] args) {
        int executarConsoleTela = 1;
        if(executarConsoleTela == 1){
            new MenuConsole().iniciar();
        } else if (executarConsoleTela == 2) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                view.MainFrame frame = new view.MainFrame();
                frame.setVisible(true);
            });
        }



    }
}