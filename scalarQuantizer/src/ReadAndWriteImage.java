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
                    /*int p = img.getRGB(x,y);
                    color = new Color(img.getRGB(x, y));
    //                System.out.println(color);
                    imageMAtrix[y][x] = p;*/
                }
            }
//            System.out.println(imageMAtrix.length);
            /*CompressAndDecompress compressAndDecompress = new CompressAndDecompress();
            Vector<Integer> pixels = new Vector<>();
            pixels = compressAndDecompress.get_All_Pixels_Of_Image_In_one_Vector(imageMAtrix);*/

            /*imageMAtrix = new int[3][6];

            imageMAtrix[0][0] = 6;
            imageMAtrix[0][1] = 15;
            imageMAtrix[0][2] = 17;
            imageMAtrix[0][3] = 60;
            imageMAtrix[0][4] = 100;
            imageMAtrix[0][5] = 90;
            imageMAtrix[1][0] = 66;
            imageMAtrix[1][1] = 59;
            imageMAtrix[1][2] = 18;
            imageMAtrix[1][3] = 3;
            imageMAtrix[1][4] = 5;
            imageMAtrix[1][5] = 16;
            imageMAtrix[2][0] = 14;
            imageMAtrix[2][1] = 67;
            imageMAtrix[2][2] = 63;
            imageMAtrix[2][3] = 2;
            imageMAtrix[2][4] = 98;
            imageMAtrix[2][5] = 92;
            *//*imageMAtrix[3][0] = 1900;
            imageMAtrix[3][1] = 2000;
            imageMAtrix[3][2] = 2100;
            imageMAtrix[3][3] = 2200;
            imageMAtrix[3][4] = 2300;
            imageMAtrix[3][5] = 2400;
            imageMAtrix[4][0] = 2500;
            imageMAtrix[4][1] = 2600;
            imageMAtrix[4][2] = 2700;
            imageMAtrix[4][3] = 2800;
            imageMAtrix[4][4] = 2900;
            imageMAtrix[4][5] = 3000;
            imageMAtrix[5][0] = 3100;
            imageMAtrix[5][1] = 3200;
            imageMAtrix[5][2] = 3300;
            imageMAtrix[5][3] = 3400;
            imageMAtrix[5][4] = 3500;
            imageMAtrix[5][5] = 3600;*//*
            pixels = compressAndDecompress.get_All_Pixels_Of_Image_In_one_Vector(imageMAtrix);


            Vector<Association> associations = new Vector<>();
            Association as = new Association();*/

            /*as.pixel = 250;
            associations.add(as);
            as = new Association();
            as.pixel = 1000;
            associations.add(as);
            as = new Association();
            as.pixel = 1254;
            associations.add(as);
            as = new Association();
            as.pixel = 1840;
            associations.add(as);
            as = new Association();
            as.pixel = 1362;
            associations.add(as);
            as = new Association();
            as.pixel = 2021;
            associations.add(as);*/



            /*Vector<Association> associations1 = compressAndDecompress.Quantize(pixels, bookSize);

            for (Association ad:
                 associations1 ) {
                System.out.println(ad.pixel);
//                System.out.println(ad.Associated_pixels);
            }*/

            /*for (int i = 0; i < pixels.size(); i++) {
                as.Associated_pixels.add(pixels.get(i));
            }
            associations.add(as);*/

//            Vector<Integer> aa = compressAndDecompress.generateAveragePixels(associations);
           /* Vector<Association> associations1 = compressAndDecompress.Associate(pixels, associations);

            for (Association association:
                 associations1) {
                System.out.println(association.Associated_pixels);
            }*/
/*
            for (Integer c:
                 aa) {
                System.out.println(c);
            }*/

//            Vector<Integer> S =  compressAndDecompress.Split(pixels);
//            for (int x:
//                 S) {
//                System.out.println(x);
//            }

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