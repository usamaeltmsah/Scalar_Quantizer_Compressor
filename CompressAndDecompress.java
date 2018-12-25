import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class CompressAndDecompress {
    public int[][] Compress(String imgPath, int codeBookSize) throws IOException {
        BufferedWriter bw = new BufferedWriter(
                new FileWriter("F:\\codebook.txt"));
        ReadAndWriteImage RW = new ReadAndWriteImage();

        System.out.println(codeBookSize);
        int[][] imageMatrix = RW.readImage(imgPath);
        int [][] image = new int[RW.getHeight()][RW.getWidth()];
        Vector<Integer> pixels = get_All_Pixels_Of_Image_In_one_Vector(imageMatrix);
        System.out.println(pixels.size());
        Vector<Association> associations1 = Quantize(pixels, codeBookSize);
        System.out.println(associations1.size());

        Map<Integer, Vector<Integer>> map = new TreeMap<>();

        int i = 0;
        for (Association ad:
                associations1 ) {
            try {
                map.put(ad.pixel, ad.Associated_pixels);
                if(!ad.Associated_pixels.isEmpty())
                {
                    bw.write(i + "|" + ad.pixel + "|" + get_min_pixel_in_association(ad.Associated_pixels) +
                            "|" + get_max_pixel_in_association(ad.Associated_pixels) + "\n");
                }
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
            /*System.out.println(ad.pixel);
            System.out.println("min: " + get_min_pixel_in_association(ad.Associated_pixels));
            System.out.println("max: " + get_max_pixel_in_association(ad.Associated_pixels));
//                System.out.println(ad.Associated_pixels);*/
            i++;
        }
        bw.close();

        int pix;
        for (int y = 0, h = RW.getHeight(); y < h; y++) {
            for (int x = 0, w = RW.getWidth(); x < w; x++) {
                pix = imageMatrix[y][x];
                for (Association s:
                     associations1) {
                    if(s.Associated_pixels.contains(pix))
                    {
                        image[y][x] = s.pixel;
                    }
                }
            }
        }
        return image;
    }
    void decompress(int[][] imageMatrix,String imageoutPath) throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("F:\\FCI\\FCIL3 T1\\IT433 - Multimedia\\Assignments\\scalarQuantizer\\codebook.txt"));
        ReadAndWriteImage RW = new ReadAndWriteImage();



        br.close();
    }

    Vector<Integer> get_All_Pixels_Of_Image_In_one_Vector(int[][] imageMatrix)
    {
        Vector<Integer> Vector_Of_Pixels = new Vector<>();
        int ImageHeight = imageMatrix.length;
        int ImageWidth = imageMatrix[0].length;
        int pixel;
        for (int y = 0; y < ImageHeight; y++)
        {
            for (int x = 0; x < ImageWidth; x++)
            {
                try {
                    pixel = imageMatrix[y][x];
                    Vector_Of_Pixels.add(pixel);
                }
                catch (Exception e)
                {
                    System.err.println(e);
                }
            }
        }
        return Vector_Of_Pixels;
    }

    int get_min_MSE_index(int pix, Vector<Association> v)
    {
        int min = get_mean_squared_error_pixel(pix, v.get(0).pixel);
        int MSE;
        int index = 0;
        for (int i = 1; i < v.size(); i++) {
            MSE = get_mean_squared_error_pixel(pix, v.get(i).pixel);
            if(MSE < min)
            {
                min = MSE;
                index = i;
            }
        }
        return index;
    }

    Vector<Association> Associate(Vector<Integer> pixels/**All image pixels**/, Vector<Association> vector)
    {
        int min_MSE_index;
        Vector<Association> associations = new Vector<>();
        Association as;
        for (int i = 0; i < vector.size(); i++) {
            as = new Association();
            as.pixel = vector.get(i).pixel;
            associations.add(as);
        }

        int cur_pixel;

        for (int j = 0; j < pixels.size(); j++) {
            //Put the pixel to which have MSE
            cur_pixel = pixels.get(j);
            min_MSE_index = get_min_MSE_index(cur_pixel, vector);
            try {
                associations.get(min_MSE_index).Associated_pixels.add(cur_pixel);
            }
            catch (Exception e )
            {
                System.err.println(e);
            }
        }
        return associations;
    }

    Vector<Integer> Split(Vector<Integer> avgs)
    {
        Vector<Integer> allSplited = new Vector<>();
        int left, right;
        for (int i = 0; i < avgs.size(); i++)
        {
            left = avgs.get(i);
            right = avgs.get(i) + 2;

            allSplited.add(left);
            allSplited.add(right);

        }
        return allSplited;
    }

    int get_mean_squared_error_pixel(int pix1, int pix2)
    {
        int MSE;
        MSE = Math.abs(pix1 - pix2);
        return MSE;
    }

    Vector<Integer> generateAveragePixels(Vector<Association> associations)
    {
        int vectorSize = associations.size();
        Vector<Integer> avgs = new Vector<>();
        long sum = 0, avg;
        avgs = new Vector<>();
        for (int i = 0; i < vectorSize; i++) {
            for (int pix : associations.get(i).Associated_pixels) {
                sum += pix;
            }
            if(associations.get(i).Associated_pixels.size() > 0)
            {
                avg = sum / associations.get(i).Associated_pixels.size();
                avgs.add((int)avg);
            }

            sum = 0;
        }
        return avgs;
    }
    Vector<Association> Quantize(Vector<Integer> pixels, int codeBookSize)
    {
        Association first = new Association();
        Vector<Integer> Splited = new Vector<>();
        Association association = new Association();
        Vector<Association> associations = new Vector<>();
        Vector<Integer> Avgs = new Vector<>();
        Vector<Association> new_Avg = new Vector<>();
        Vector<Association> cur_associasions = new Vector<>();
        first.Associated_pixels = pixels;
        associations.add(first);
        int levelPixels = 1;
        while (levelPixels <= codeBookSize)
        {
            Avgs = new Vector<>();
            Avgs.addAll(generateAveragePixels(associations));
                association.Associated_pixels.addAll(Avgs);
                new_Avg.add(association);
            Splited = new Vector<>();
            associations = new Vector<>();

                Splited.addAll(Split(new_Avg.get(0).Associated_pixels));
            for (Integer Associated:
                 Splited) {
                association = new Association();
                association.pixel = Associated;
                cur_associasions.add(association);
            }

                associations.addAll(Associate(pixels, cur_associasions));
            levelPixels *= 2;
            cur_associasions = new Vector<>();
            new_Avg = new Vector<>();
        }
        return associations;
    }
    int get_min_pixel_in_association(Vector<Integer> Associated)
    {
        int min = Associated.get(0);
        for (int x:
             Associated) {
            if(x < min)
                min = x;
        }
        return  min;
    }

    int get_max_pixel_in_association(Vector<Integer> Associated)
    {
        int max = Associated.get(0);
        for (int x:
                Associated) {
            if(x > max)
                max = x;
        }
        return  max;
    }
}
