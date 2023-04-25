package view;

public class Start {
    public static void main(String [] args){
        GUI view1 = new GUI();
        view1.setVisible(true);
        Controller controller1 = new Controller(view1);
    }

}
