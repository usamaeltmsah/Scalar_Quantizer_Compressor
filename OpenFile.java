import javax.swing.*;
import java.io.File;

public class OpenFile {
    JFileChooser fileChooser = new JFileChooser();
    File file;
    public String getFilePath() {
        String path = fileChooser.getSelectedFile().getAbsolutePath();
        return path;
    }
    public String getFileName()
    {
        String filename = fileChooser.getSelectedFile().getName();
        return filename;
    }

    public File pickFile()
    {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            file = fileChooser.getSelectedFile();

            String path= fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(path);
        }
        else
        {
            System.err.println("No File was selected");
        }
        fileChooser.cancelSelection();
        return fileChooser.getSelectedFile();
    }
}
