import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    int count = 0;
    JFrame frame;
    JButton button;
    JLabel label;

    public GUI(){
        frame =  new JFrame();

        button = new JButton("Click Me: 0");
        button.addActionListener(this);

        label = new JLabel("Number of clicks");


        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("My First GUI");
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args){
        GUI g = new GUI();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
    }
}
