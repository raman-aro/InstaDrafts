import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class imagefile {

    String path = null;
    String name = null;
    File f = null;
    BufferedImage buff_image = null;
    Image image = null;
    ImageIcon img_icon = null;
    String creation_date;
    ratings rt = null;
    int width = 0;
    int height = 0;

    imagefile(String file_path) {

        // set filename
        path = file_path;

        // set ranking
        rt = new ratings();

        try {
            // get file attributes
            Path file = Paths.get(path);
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            creation_date = df.format((attr.creationTime()).toMillis());

            // create file
            f = new File(path);
            name = f.getName();

            // create scaled ImageIcon
            buff_image = ImageIO.read(f);

            double resize_scale = getScale(buff_image.getWidth(), buff_image.getHeight(), 200, 200);

            Graphics g = buff_image.getGraphics();
            g.setColor(new Color(0,0,0,0));
            g.fillRect(0,0, 200 - ((int) (buff_image.getWidth() * resize_scale)), 200 - (int) (buff_image.getHeight() * resize_scale));


            ImageIcon temp_img = new ImageIcon(f.getPath());
            image = temp_img.getImage();
            Image newimg = image.getScaledInstance((int) (buff_image.getWidth() * resize_scale), (int) (buff_image.getHeight() * resize_scale), Image.SCALE_SMOOTH);
            //Image newimg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            img_icon = new ImageIcon(newimg);

            double resize_scale_full = getScale(buff_image.getWidth(), buff_image.getHeight(), 680, 510);
            width = (int) (buff_image.getWidth() * resize_scale_full);
            height = (int) (buff_image.getHeight() * resize_scale_full);
            image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public double getScale (double orig_w, double orig_h, double desired_w, double desired_h) {
        double w_scale = desired_w / orig_w;
        double h_scale = desired_h / orig_h;
        return Math.min(w_scale, h_scale);
    }

    class detailedView implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFrame d_frame = new JFrame();
            JDialog d_modal = new JDialog(d_frame, "Modal Frame", true);


            d_modal.setDefaultCloseOperation(d_modal.DISPOSE_ON_CLOSE);
            d_modal.addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    Main.updateCurrentView();
                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }

            });

            JPanel d_settings = new JPanel();
            d_settings.setLayout(new FlowLayout());

            JPanel d_panel = new JPanel();
            d_panel.setLayout(new FlowLayout());

            JLabel img = new JLabel(new ImageIcon(image));
            d_panel.add(img);

            JPanel tile_stars = new JPanel();
            JPanel tile_clearstars = new JPanel();

            tile_stars.setLayout(new GridLayout(1,5));
            for (JButton jb : rt.stars){
                tile_stars.add(jb);
            }

            tile_clearstars.add(rt.clearstars);

            d_settings.add(tile_stars);
            d_settings.add(tile_clearstars);

            d_modal.add(d_settings, "North");
            d_modal.add(d_panel, "Center");

            d_modal.setResizable(false);
  //          d_frame.setMinimumSize(new Dimension(Math.min(800, (int) (width * 1.1)),Math.min(600, (int) (height * 1.1) + 100)));
            d_modal.setSize(new Dimension(800,600));
            d_modal.setMaximumSize((new Dimension(800,600)));
            d_modal.setVisible(true);
            d_modal.setLocationRelativeTo(null);

        }
    }

    public JPanel toGridPanel() {
        JPanel tile = new JPanel();
        BoxLayout boxLayout = new BoxLayout(tile, BoxLayout.Y_AXIS);
        tile.setLayout(boxLayout);

        JButton tile_img = new JButton();

        tile_img.setPreferredSize(new Dimension(205, 205));

        tile_img.addActionListener(new detailedView());
        JLabel tile_img_icon = new JLabel(img_icon);
        //tile_img_icon.setPreferredSize(new Dimension(250, 250));
        tile_img.add(tile_img_icon, JLabel.CENTER);
        tile_img_icon.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        tile_img.setBackground(tile.getBackground());
        tile_img.setOpaque(true);


        JLabel tile_name = new JLabel(name);

        JLabel tile_creation_date = new JLabel(creation_date);
        JPanel tile_stars = new JPanel();
        JPanel tile_clearstars = new JPanel();

        tile_stars.setLayout(new GridLayout(1,5));
        for (JButton jb : rt.stars){
            tile_stars.add(jb);
        }

        tile_clearstars.add(rt.clearstars);

        tile.add(tile_img);
        tile.add(tile_name);
        tile.add(tile_creation_date);
        tile.add(tile_stars);
        tile.add(tile_clearstars);


        tile_img.setAlignmentX(Component.CENTER_ALIGNMENT);
        tile_name.setAlignmentX(Component.CENTER_ALIGNMENT);
        tile_creation_date.setAlignmentX(Component.CENTER_ALIGNMENT);
        tile_stars.setAlignmentX(Component.CENTER_ALIGNMENT);
        tile_clearstars.setAlignmentX(Component.CENTER_ALIGNMENT);

        return tile;

    }

    public JPanel toListPanel() {
        JPanel tile = new JPanel();
        JPanel metadata = new JPanel();

        tile.setLayout(new GridLayout(1,6));

        JButton tile_img = new JButton();

        tile_img.setPreferredSize(new Dimension(205, 205));

        tile_img.addActionListener(new detailedView());
        JLabel tile_img_icon = new JLabel(img_icon);
        tile_img.add(tile_img_icon, JLabel.CENTER);
        tile_img_icon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        tile_img.setBackground(tile.getBackground());
        tile_img.setOpaque(true);

        JLabel tile_name = new JLabel(name);
        JLabel tile_creation_date = new JLabel(creation_date);
        JPanel tile_stars = new JPanel();
        JPanel tile_clearstars = new JPanel();

        tile_stars.setLayout(new GridLayout(1,5));
        for (JButton jb : rt.stars){
            tile_stars.add(jb);
        }

        tile_clearstars.add(rt.clearstars);
        tile.add(tile_img);

        metadata.setLayout(new GridLayout(4,1));
        metadata.add(tile_name);
        metadata.add(tile_creation_date);
        metadata.add(tile_stars);
        metadata.add(tile_clearstars);


        tile.add(metadata);
        tile.setMaximumSize(new Dimension(1200, 200));
        tile.add(new JPanel());
        tile.add(new JPanel());
        tile.add(new JPanel());
        tile.add(new JPanel());

        tile.setAlignmentX(Component.LEFT_ALIGNMENT);

        return tile;

    }

}

//
//    public JPanel toListPanel() {
//        JPanel tile = new JPanel();
//        //BoxLayout h_boxLayout = new BoxLayout(tile, BoxLayout.X_AXIS);
//        //tile.setLayout(h_boxLayout);
//        tile.setLayout(new GridLayout(1,10));
//        //tile.setLayout(new GridBagLayout());
//        JButton tile_img = new JButton();
//        tile_img.addActionListener(new detailedView());
//
//        JLabel tile_img_icon = new JLabel(img_icon);
//        tile_img.add(tile_img_icon);
//        tile_img.setBackground(tile.getBackground());
//        tile_img.setOpaque(true);
////        tile_img.setBorderPainted(false);
//
//        JLabel tile_name = new JLabel(name);
//        JLabel tile_creation_date = new JLabel(creation_date);
//        JPanel tile_stars = new JPanel();
//        JPanel tile_clearstars = new JPanel();
//
//        tile_stars.setLayout(new GridLayout(1,5));
//        for (JButton jb : rt.stars){
//            tile_stars.add(jb);
//        }
//
//        tile_clearstars.add(rt.clearstars);
//
//        tile.add(tile_img);
//        tile.add(tile_name);
//        tile.add(tile_creation_date);
//        tile.add(tile_stars);
//        tile.add(tile_clearstars);
//
//        tile.add(new JPanel());
//        tile.add(new JPanel());
//        tile.add(new JPanel());
//        tile.add(new JPanel());
//        tile.add(new JPanel());
//
//        return tile;
//
//    }

