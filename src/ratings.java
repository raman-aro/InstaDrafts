import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class ratings {

    // Class specific components (Static)
    public static ImageIcon empty_icon = new ImageIcon("src/emptystar.png");
    public static ImageIcon filled_icon = new ImageIcon("src/filledstar.png");

    static {
        Image scaled_empty_star = empty_icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        empty_icon = new ImageIcon(scaled_empty_star);

        Image scaled_filled_star = filled_icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        filled_icon = new ImageIcon(scaled_filled_star);
    }


    // Instance specific components
    int rating  = 0;
    ArrayList<JButton> stars = new ArrayList();
    JButton clearstars = null;

    public void clearRating() {
        rating = 0;
        for (JButton jb : stars){
            jb.setIcon(empty_icon);
        }
    }

    public void changeRating (String rating_num) {
        int rating_num_int = Integer.parseInt(rating_num);
        clearRating();
        rating = rating_num_int;
        for (int i = 0; i < rating_num_int; i++) {
            stars.get(i).setIcon(filled_icon);
        }
    }

    class changeRatingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton parent = (JButton) e.getSource();
            changeRating(parent.getName());
        }
    }

    ratings() {
        try {

            for (int i = 0; i < 5; i++){
                JButton star = new JButton();
                star.setName(Integer.toString(i+1));
                star.addActionListener(new changeRatingListener());
                star.setIcon(empty_icon);
                stars.add(star);
            }

            clearstars = new JButton("Clear Rating");
            clearstars.setName("0");
            clearstars.addActionListener(new changeRatingListener());
        }
        catch (Exception e) {
            System.out.println(e);
        }


    }


}
