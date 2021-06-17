import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.io.*;

public class Main {

    private static String saveFileName = "saveFile.csv";

    private static ArrayList <imagefile> all_images = new ArrayList();
    private static ArrayList <imagefile> filtered_images = new ArrayList();

    static ratings global_ratings_filter = new ratings();
    static JFrame frame = new JFrame("InstaDrafts");

    static JTabbedPane jtp = new JTabbedPane();

    public static JPanel g_panel = new JPanel();
    public static JScrollPane grid_scrollpane = new JScrollPane(g_panel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public static JPanel l_panel = new JPanel();
    public static JScrollPane list_scrollpane = new JScrollPane(l_panel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public static void Upload (String filedir) {
        imagefile im = new imagefile(filedir);
        all_images.add(im);
    }

    public static void createFiltered(int min_rating) {
        filtered_images.clear();
        for (imagefile im : all_images) {
            if (im.rt.rating >= min_rating) {
                filtered_images.add(im);
            }
        }
    }

    public static void updateCurrentView() {

        String current_tab = jtp.getSelectedComponent().getName();

        if (current_tab.equals("List")) {
            makeListPanel();
        }
        else if (current_tab.equals("Grid")) {
            makeGridPanel();
        }

    }

    static class changeRatingFilter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton parent = (JButton) e.getSource();
            createFiltered(Integer.parseInt(parent.getName()));
            updateCurrentView();
        }
    }

    public static void makeGridPanel() {
        g_panel.removeAll();
        // create a panel and add components
        g_panel.setLayout(new WrapLayout());

        for (imagefile im: filtered_images) {
            g_panel.add(im.toGridPanel());
        }

        g_panel.revalidate();
        g_panel.repaint();

        frame.revalidate();
    }

    public static void makeListPanel() {

        // create a panel and add components
        l_panel.removeAll();
        l_panel.setLayout(new BoxLayout(l_panel, BoxLayout.Y_AXIS));

        for (imagefile im: filtered_images) {
            l_panel.add(im.toListPanel());
        }
        l_panel.revalidate();
        l_panel.repaint();

        frame.revalidate();

    }

    static class chooseUploadFile implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser();

            File workingDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(workingDirectory);

            fileChooser.setMultiSelectionEnabled(true);
            FileNameExtensionFilter fef = new FileNameExtensionFilter("Image File Types", "PNG", "JPEG", "JPG", "GIF");
            fileChooser.addChoosableFileFilter(fef);
            fileChooser.setFileFilter(fef);

            int ret = fileChooser.showOpenDialog(null);

            if (ret == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                for (File f : files) {
                    Upload(f.getAbsolutePath());
                }

                createFiltered(global_ratings_filter.rating);
                updateCurrentView();

            }
        }
    }

    static class clearImages implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            all_images.clear();
            filtered_images.clear();
            updateCurrentView();

        }
    }

    public static void importFromSave() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(saveFileName));
            String f;
            while ((f = br.readLine()) != null) {
                String[] data = f.split(",");

                Upload(data[0]);
                imagefile im = all_images.get(all_images.size() - 1);
                im.rt.changeRating(data[1]);
            }
        }
        catch (Exception err) {
            System.out.println(err);
        }
    }

    public static void createAndShowGUI() {

        // create a window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    PrintWriter pw = new PrintWriter(saveFileName);
                    for (imagefile im: all_images) {
                        pw.println(im.path + "," + Integer.toString(im.rt.rating));
                    }
                    pw.close();
                }
                catch (Exception err) {
                    System.out.println(err);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

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

        // Create Settings Header
        JPanel settings = new JPanel();
        settings.setLayout(new FlowLayout());

        g_panel.setLayout(new WrapLayout());
        grid_scrollpane.setName("Grid");
        grid_scrollpane.getVerticalScrollBar().setUnitIncrement(15);

        l_panel.setLayout(new BoxLayout(l_panel, BoxLayout.Y_AXIS));
        list_scrollpane.setName("List");
        list_scrollpane.getVerticalScrollBar().setUnitIncrement(15);

        // Add Upload Button
        JButton upload_btn = new JButton("Upload Images");
        upload_btn.addActionListener(new chooseUploadFile());
        settings.add(upload_btn);

        // Add Filter Stars
        for (JButton jb : global_ratings_filter.stars) {
            jb.addActionListener(new changeRatingFilter());
            settings.add(jb);
        }

        // Add Clear Stars Button
        global_ratings_filter.clearstars.addActionListener(new changeRatingFilter());
        settings.add(global_ratings_filter.clearstars);

        // Add Clear Images Button
        JButton clear_imgs = new JButton("Clear Images");
        clear_imgs.addActionListener(new clearImages());
        settings.add(clear_imgs);

        frame.add(settings, "North");

        jtp.add("Grid View", grid_scrollpane);
        jtp.add("List View", list_scrollpane);
        frame.add(jtp);

        jtp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
             if (e.getSource() instanceof JTabbedPane) {
                 JTabbedPane pane = (JTabbedPane) e.getSource();
                 if (pane.getSelectedComponent().getName().equals("List")) {
                     makeListPanel();
                 }
                 else if (pane.getSelectedComponent().getName().equals("Grid")) {
                     makeGridPanel();
                 }
             }
            }
        });

        // set window behaviour and display it
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(600,750));
        frame.setSize(1000, 1000);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        createAndShowGUI();
        importFromSave();
        createFiltered(0);
        updateCurrentView();
    }
}



