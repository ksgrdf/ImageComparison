import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Comparison {


    BufferedImage firstImage;
    BufferedImage secondImage;
    BufferedImage resultImage;

    private int width;
    private int height;
    private int [][] difference;


    public Comparison(BufferedImage firstImage, BufferedImage secondImage)
    {
        width = firstImage.getWidth();
        height = firstImage.getHeight();
        difference = new int[width][height];
        this.firstImage = firstImage;
        this.secondImage = secondImage;
    }


        //find different pixel and add points in array
    public void compare(BufferedImage image1, BufferedImage image2){

        int firstCount = 0;
        int secondCount = 0;


        for(int x = 0; x < image1.getWidth(); x++)
        {
            for (int y = 0; y < image1.getHeight(); y++)
            {


                if((firstImage.getRGB(x,y)) > (secondImage.getRGB(x,y)))
                {
                    firstCount++;
                }
                else
                {
                    secondCount++;
                }

                int red1 = (image1.getRGB(x, y) >> 16) & 0xFF;
                int green1 = ((image1.getRGB(x, y)) >> 8) & 0xFF;
                int blue1 = (image1.getRGB(x, y)) & 0xFF;

                int red2 = ((image2.getRGB(x, y)) >> 16) & 0xFF;
                int green2 = ((image2.getRGB(x, y)) >> 8) & 0xFF;
                int blue2 = (image2.getRGB(x, y)) & 0xFF;

                double red = Math.abs(red1 - red2) / 256. * 100 / 3;
                double green = Math.abs(green1 - green2) / 256. * 100 / 3;
                double blue = Math.abs(blue1 - blue2) / 256. * 100 / 3;

                double pixel = red + green + blue;


                if (pixel >= 10) {
                    difference[x][y] = 1; //marker point different pixel

                } else {
                    difference[x][y] = 0;
                }
            }


        }

        resultImage = firstCount > secondCount ? firstImage : secondImage;
        findMarker();
    }



    //find marker in different array point
    public void findMarker()
    {

        int x1 = 0;
        int x2 = 0;

        int y1 = 0;
        int y2 = 0;


        for(int i = 0; i < width; i++)
        {

            for(int a = 0; a < height; a++)
            {

                if(difference[i][a] == 1)
                {

                    x1 = i;
                    x2 = i;
                    y1 = a;
                    y2 = a;
                    coordinatesDraw(x1,x2,y1,y2);
                }
            }
        }
    }


    public void coordinatesDraw(int x1, int x2, int y1, int y2)
    {
        System.out.println(x1+ " " + y1);

        int tr=1; //allowed of value
        int atr=0;

        for(int j = y2; j <= y2 && y2<height-1; j++ ) { // line calculation

            for (int i = x2; i <= x2+tr && x2 < width - 1; i++) { //point positive X
                if (difference[i][y2] == 1) {
                    x2++;
                    atr++;
                    if (atr > 1)
                    {
                        atr = 0;
                        if (tr < 10)
                            tr++;
                    }

                }
                for (int a = y1; a >=y1- tr && y1 > 0; a-- ) //point negative Y
                {
                    if (difference[x2][a] == 1) {
                        y1--;
                        atr++;
                        if (atr > 1)
                        {
                            atr = 0;
                            if (tr < 10)
                                tr++;
                        }

                    }
                }
            }


            for (int i = x1; i >= x1-tr && x1 > 0; i--) {//point negative X
                if (difference[i][y2] == 1) {
                    x1--;
                    atr++;
                    if (atr > 1)
                    { atr = 0;
                        if (tr < 10)
                            tr++;
                    }

                }
                for (int a = y1; a >=y1- tr && y1 > 0; a-- ) //point negative Y
                {
                    if (difference[x1][a] == 1) {
                        y1--;
                        atr++;
                        if (atr > 1)
                        {
                            atr = 0;
                            if (tr < 10)
                                tr++;
                        }

                    }
                }
            }
            int sum = 0;
            for (int i = x1; i < x2; i++)
                if (difference[i][j] == 1)
                    sum++;
            if (sum == 0) break;
            y2++;
        }
        drawImg(resultImage,x1,y1,x2-x1,y2 - y1);


        clearDiff(x1, y1, x2, y2);




    }

    public void clearDiff(int x1, int y1,int x2, int y2)
    {
        for(int i = x1; i < x2; i++)
        {
            for(int a = y1; a < y2; a++)
            {
                difference[i][a] = 0;
            }
        }
    }

    public Graphics2D drawImg(BufferedImage image, int x, int y, int width, int heigth)
    {
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(Color.RED);
        graphics2D.draw(new Rectangle(x,y,width,heigth));
        graphics2D.dispose();
        try {
            ImageIO.write(image,"png", new File("e:\\result.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return graphics2D;
    }


}
