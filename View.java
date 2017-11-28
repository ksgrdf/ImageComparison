import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class View extends JFrame{

    private File [] files;


    public View()
    {
    JOptionPane.showMessageDialog(null, "select two files");
    setBounds(0, 0, 500, 500);
    JFileChooser dialog = new JFileChooser();
    dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    dialog.setApproveButtonText("Выбрать");
    dialog.setDialogTitle("Выберите файлы для загрузки");
    dialog.setDialogType(JFileChooser.OPEN_DIALOG);
    dialog.setMultiSelectionEnabled(true);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "png");
       dialog.setFileFilter(filter);
    dialog.showOpenDialog(this);


        files = dialog.getSelectedFiles();
    }




    public static void main(String[] args) {

        View comp = new View();

        BufferedImage firstImg = null;
        BufferedImage secondImg = null;


            try {
                if (comp.files == null || comp.files.length < 2) {

                    JOptionPane.showMessageDialog(null, "select two files");
                    comp = new View();

                }

                firstImg = ImageIO.read(new FileImageInputStream(comp.files[0]));
                secondImg = ImageIO.read(new FileImageInputStream(comp.files[1]));


            } catch (IOException e) {
                e.printStackTrace();
            }


            Comparison comparison = new Comparison(firstImg,secondImg);
            comparison.compare(firstImg, secondImg);
            JLabel a = new JLabel();

            a.setIcon(new ImageIcon(comparison.resultImage));
            JOptionPane.showMessageDialog(null, a);
            comp.add(a);

            System.exit(1);

        }


    }





