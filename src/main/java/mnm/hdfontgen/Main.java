package mnm.hdfontgen;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            // open the gui
            SwingMain.main();
        } else {
            TerminalMain.main(args);
        }
    }
}
