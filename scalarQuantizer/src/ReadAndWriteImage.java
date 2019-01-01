import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

class ReadAndWriteImage
{
    int width;
    int height;
    public int bookSize;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getImageMAtrix() {
        return imageMAtrix;
    }

    int[][] imageMAtrix = null;
    Color color;
    public int[][] readImage(String filePath){
        File f = new File(filePath); //image file path
        int p, a, r, g, b;
        try {
            BufferedImage img = ImageIO.read(f);
            width = img.getWidth();
            height = img.getHeight();
            imageMAtrix = new int[height][width];
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    p = img.getRGB(x, y);
                    a = (p >> 24) & 0xff;
                    g = (p >> 8) & 0xff;
                    r = (p >> 16) & 0xff;
                    b = p & 0xff;
                    //because in gray image r = g = b  we will select r

                    imageMAtrix[y][x] = r;

                    //set new RGB value
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    img.setRGB(x, y, p);
                }
            }

        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        return imageMAtrix;
    }

    public void writeImage(int[][] imageMatrix,String imageoutPath) {

        int height = imageMatrix.length;
        int width = imageMatrix[0].length;
        int a, pix, p;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /*int pix = imageMatrix[y][x];
                img.setRGB(x, y, pix);*/

                a = 255;
                pix = imageMatrix[y][x];
                p = (a << 24) | (pix << 16) | (pix << 8) | pix;

                img.setRGB(x, y, p);
            }
        }

        File f = new File(imageoutPath);

        try {
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
