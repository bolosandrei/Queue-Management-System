package view;

import bussinesLogic.SimulationManager;

import java.awt.*;
import javax.swing.*;
import static java.lang.Integer.parseInt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    JFrame frame = new JFrame("Management of Queues");//fereastra - termopan
    JPanel p1 = new JPanel();
    JLabel label1 = new JLabel("Management of Queues");//panelul p1 e format doar dintr-un sir de caractere

    JPanel p2 = new JPanel();//pannel 2 contine 3 panneluri care sunt formate la randul lor dintr-un JLabel si un JTextField
    JPanel p21 = new JPanel();
    JPanel p22 = new JPanel();
    JPanel p23 = new JPanel();
    JPanel p24 = new JPanel();
    JPanel p25 = new JPanel();
    JPanel p26 = new JPanel();
    JPanel p27 = new JPanel();
    JPanel p31 = new JPanel();
    JPanel p32 = new JPanel();
    JLabel l2NrClients = new JLabel("Number of Clients/Tasks (N):");
    JTextField fi1 = new JTextField(13);
    JLabel l2NrQueues = new JLabel("Number of Queues/Servers (S):");
    JTextField fi2 = new JTextField(13);
    JLabel l2SimInterval = new JLabel("Simulation interval (seconds):");
    JTextField fi3 = new JTextField(13);
    JLabel l2ArrivalTime = new JLabel("Arrival time interval (seconds):");
    JLabel l2ArrivalTimeMin = new JLabel("Min:");
    JTextField fi51 = new JTextField(7);
    JLabel l2ArrivalTimeMax = new JLabel("Max:");
    JTextField fi52 = new JTextField(7);

    JLabel l2ServiceTime = new JLabel("Service time interval (seconds):");
    JLabel l2ServiceTimeMin = new JLabel("Min:");
    JTextField fi71 = new JTextField(7);
    JLabel l2ServiceTimeMax = new JLabel("Max:");
    JTextField fi72 = new JTextField(7);
    JTextArea fi31 = new JTextArea(12,45);

    JPanel p3 = new JPanel();
    JButton EnterButton = new JButton("Start");

    JPanel panel = new JPanel();//o sa vina adaugate p1, p2 si p3 in el, si el se afiseaza in fereastra

    public GUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(600,100);
        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.setSize(550, 700);
        p1.setBackground(new Color(99,172,206));
        label1.setFont(new Font("Book Antiqua", Font.BOLD + Font.ITALIC, 24));
        p1.add(label1);

        p2.setBackground(new Color(99,172,206));
        p2.setLayout(new GridLayout(5, 4, 4, 4));
        p21.setLayout(new BoxLayout(p21, BoxLayout.Y_AXIS));
        p22.setLayout(new BoxLayout(p22, BoxLayout.PAGE_AXIS));
        p23.setLayout(new BoxLayout(p23, BoxLayout.Y_AXIS));
        p24.setLayout(new BoxLayout(p24, BoxLayout.Y_AXIS));
        p25.setLayout(new FlowLayout(FlowLayout.CENTER));
        p26.setLayout(new BoxLayout(p26, BoxLayout.Y_AXIS));
        p27.setLayout(new FlowLayout(FlowLayout.CENTER));
        p21.add(l2NrClients);
        //p21.setBackground(new Color(19,142,246, 200));
        l2NrClients.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2NrClients.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi1.setHorizontalAlignment(SwingConstants.CENTER);
        p21.add(fi1);
        p22.add(l2NrQueues);
        l2NrQueues.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2NrQueues.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi2.setHorizontalAlignment(SwingConstants.CENTER);
        p22.add(fi2);
        p23.add(l2SimInterval);
        l2SimInterval.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2SimInterval.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi3.setHorizontalAlignment(SwingConstants.CENTER);
        p23.add(fi3);
        p24.add(l2ArrivalTime);
        l2ArrivalTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2ArrivalTime.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        p25.add(l2ArrivalTimeMin);
        l2ArrivalTimeMin.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi51.setHorizontalAlignment(SwingConstants.CENTER);
        p25.add(fi51);
        p25.add(l2ArrivalTimeMax);
        l2ArrivalTimeMax.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi52.setHorizontalAlignment(SwingConstants.CENTER);
        p25.add(fi52);
        p2.add(p21);
        p2.add(p22);
        p2.add(p23);
        p24.add(p25);
        p2.add(p24);
        p26.add(l2ServiceTime);
        l2ServiceTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        l2ServiceTime.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        p27.add(l2ServiceTimeMin);
        l2ServiceTimeMin.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi71.setHorizontalAlignment(SwingConstants.CENTER);
        p27.add(fi71);
        p27.add(l2ServiceTimeMax);
        l2ServiceTimeMax.setFont(new Font("JetBrains Mono", Font.BOLD, 13));
        fi72.setHorizontalAlignment(SwingConstants.CENTER);
        p27.add(fi72);
        p26.add(p27);
        p2.add(p26);

        EnterButton.setFont(new Font("JetBrains Mono", Font.BOLD, 17));
        EnterButton.setBackground(new Color(38, 249, 7));
        p32.setBackground(new Color(38, 249, 7,150));
        //p31.setLayout(new FlowLayout(FlowLayout.CENTER));
        EnterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        p32.setLayout(new FlowLayout(FlowLayout.CENTER));
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        //p31.setPreferredSize(new Dimension(30, 20));
        p31.add(EnterButton);
        //fi31.setSize(300,100);
//        fi31.setHorizontalAlignment(SwingConstants.CENTER);
        p32.add(fi31);
        JScrollPane sp = new JScrollPane(fi31,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        p32.add(sp);
        p3.add(p31);
        p3.add(p32);

        panel.setBackground(new Color(69,142,176));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);
        this.setContentPane(panel);
        this.setVisible(true);

    }
    public void buttonListener(ActionListener e) {
        EnterButton.addActionListener(e);
    }

    public int getNrTasks(){
        return parseInt(fi1.getText());
    }
    public int getNrServers(){
        return parseInt(fi2.getText());
    }
    public int getSimInterval(){
        return parseInt(fi3.getText());
    }
    public int getArrivMin(){
        return parseInt(fi51.getText());
    }
    public int getArrivMax(){
        return parseInt(fi52.getText());
    }
    public int getServMin(){
        return parseInt(fi71.getText());
    }
    public int getServMax(){
        return parseInt(fi72.getText());
    }
    public void setResult(String string){
        fi31.setText(string); // sa reseteze textul existent
//        fi31.append(string); // ca sa adauge la textul existent
    }

//    public static void main(String[] args){
//        GUI gui1 = new GUI();
//    }
}
