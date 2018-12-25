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
//    ReadAndWriteImage rf = new ReadAndWriteImage();

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

//                vector v = new vector();
//                v.width = Integer.parseInt(vectorWidth.getValue().toString());
//                v.height = Integer.parseInt(vectorHight.getValue().toString());

//                System.out.println(v.width);
//                System.out.println(v.height);


//                int[][] imagMatrix = RW.readImage(of.getFilePath());
//                RW.writeImage(imagMatrix,"E:\\my phone\\2017_new.jpg");
            }

        });


        compressButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Called when compress button clicked
                try
                {
                    int bookSize = Integer.parseInt(codeBookSize.getValue().toString());
                    System.out.println(bookSize);
                    int[][] imagMatrix = CommAndDecomm.Compress(of.getFilePath(), bookSize);
                    RW.writeImage(imagMatrix,"F:\\FCI\\FCIL3 T1\\IT433 - Multimedia\\Assignments\\scalarQuantizer\\2017_new.jpg");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(of.getFilePath()));
                    writer.close();

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
                    BufferedWriter writer = new BufferedWriter(new FileWriter(of.getFilePath()));
                    writer.close();
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
