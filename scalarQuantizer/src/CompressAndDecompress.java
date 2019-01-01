import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class CompressAndDecompress {
    public int[][] Compress(String imgPath, int codeBookSize) throws IOException {
        BufferedWriter bw = new BufferedWriter(
                new FileWriter("F:\\FCI\\FCIL3 T1\\IT433 - Multimedia\\Assignments\\scalarQuantizer\\codebook.txt"));
        ReadAndWriteImage RW = new ReadAndWriteImage();

        int[][] imageMatrix = RW.readImage(imgPath);
        int [][] image = new int[RW.getHeight()][RW.getWidth()];
        final Vector<Integer> pixels = get_All_Pixels_Of_Image_In_one_Vector(imageMatrix);
        Vector<Association> associations1 = Quantize(pixels, codeBookSize);
        Map<Integer, Vector<Integer>> map = new TreeMap<>();

        int i = 0, min, max, p;
        Vector<Integer> min_and_maxs = new Vector<>();
        for (Association ad:
                associations1 ) {
            try {
                if(!ad.Associated_pixels.isEmpty())
                {
                    min = get_min_pixel_in_association(ad.Associated_pixels);
                    max = get_max_pixel_in_association(ad.Associated_pixels);
                    min_and_maxs.add(min);
                    min_and_maxs.add(max);
                    map.put(ad.pixel, ad.Associated_pixels);
                    bw.write(i + "|" + ad.pixel + "|" + min +
                            "|" + max + "\n");
                }
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
            i++;
        }
        bw.close();
        Vector<Integer> indices = get_the_nearest_indices(pixels, min_and_maxs);

        int in = 0;
        for (int y = 0, h = RW.getHeight(); y < h; y++) {
            for (int x = 0, w = RW.getWidth(); x < w; x++) {
                image[y][x] = indices.get(in++);

        RW.writeImage(image, "F:\\FCI\\FCIL3 T1\\IT433 - Multimedia\\Assignments\\scalarQuantizer\\compressed.png");
        return image;
    }
    Vector<Integer> get_the_nearest_indices(Vector<Integer> pixels, Vector<Integer> min_and_maxs)
    {
        Vector<Integer> vectorOfIndices = new Vector<>();
        for (int p:
             pixels) {
//                System.out.println(a.Associated_pixels.get(i));
                for (int j = 0; j < min_and_maxs.size(); j++) {
                    if(j == min_and_maxs.size() - 1)
                        break;
                    if (p >= 0 && p <= min_and_maxs.get(1))
                    {
                        vectorOfIndices.add(0);
                        j = min_and_maxs.size();
                    }
                    else if (p >= min_and_maxs.get(j) && p <= min_and_maxs.get(j + 1))
                    {
                        vectorOfIndices.add(j / 2);
                        j = min_and_maxs.size();
                    }
                    else if(p >= min_and_maxs.get(min_and_maxs.size() - 1))
                    {
                        vectorOfIndices.add(min_and_maxs.get(min_and_maxs.size() - 1) / 2);
                        j = min_and_maxs.size();
                    }
                }
        }
        return vectorOfIndices;
    }
    int[][] decompress(int[][] imageMatrix) throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader("F:\\FCI\\FCIL3 T1\\IT433 - Multimedia\\Assignments\\scalarQuantizer\\codebook.txt"));
        codebook c;
        Vector<codebook> codebookVector = new Vector<>();
        int[][] deCompressesImage = new int[imageMatrix.length][imageMatrix[0].length];
        String line;
        String xx[];
        while ((line = br.readLine()) != null){
            c = new codebook();
            xx = line.split("[|]");
            c.index = Integer.parseInt(xx[0]);
            c.pixel = Integer.parseInt(xx[1]);
            c.min = Integer.parseInt(xx[2]);
            c.max = Integer.parseInt(xx[3]);
            codebookVector.add(c);
        }
        for (int y = 0, h = imageMatrix.length; y < h; y++) {
            for (int x = 0, w = imageMatrix[0].length; x < w; x++) {
                for (int i = 0; i < codebookVector.size(); i++) {
                    codebook cc = codebookVector.get(i);
                    if (imageMatrix[y][x] == i)
                    {
                        deCompressesImage[y][x] = cc.pixel;
                        i = codebookVector.size();
                    }
                }
            }
        }
        br.close();
        return deCompressesImage;
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
