import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Compressor frame = new Compressor();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To make the program stop when close the GUI
        CompressAndDecompress cc = new CompressAndDecompress();
    }
}
