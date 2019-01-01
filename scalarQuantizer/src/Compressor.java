import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Compressor extends JFrame{
    private JButton loadFileButton;
    private JPanel panel1;
    private JButton decompressButton;
    private JButton compressButon;
    private JSpinner codeBookSize;


    OpenFile of;

    public Compressor() {
        CompressAndDecompress CommAndDecomm = new CompressAndDecompress();
        ReadAndWriteImage RW = new ReadAndWriteImage();
        add(panel1);
        setTitle("Files Compressor");
        setSize(500, 500);

        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Called when load file button clicked

                of = new OpenFile();

                try {
                    of.pickFile();
                }
                catch (Exception e1)
                {
                    System.out.println(e1);
                }
            }

        });


        compressButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Called when compress button clicked
                try
                {
                    int bookSize = Integer.parseInt(codeBookSize.getValue().toString());
                    int[][] imagMatrix = CommAndDecomm.Compress(of.getFilePath(), bookSize);
                    JOptionPane.showMessageDialog(null, "File compressed successfully :)");
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, "No file was selected!");
                }

            }
        });
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Called when Decompress button clicked
                try
                {
                    int [][] compressedMatrix = RW.readImage(of.getFilePath());

                    int [][] decomm = CommAndDecomm.decompress(compressedMatrix);

                    RW.writeImage(decomm, "F:\\FCI\\FCIL3 T1\\IT433 - Multimedia\\Assignments\\scalarQuantizer\\cool_football_in_high_definition_picture_of_5_168193_decomm.png");

                    JOptionPane.showMessageDialog(null, "File decompressed successfully :)");
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, "No file was selected!");
                }
            }
        });
    }

}
