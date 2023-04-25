package view;

import bussinesLogic.Scheduler;
import bussinesLogic.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller {
    private static GUI view1;
    public Controller(GUI gui){
        view1 = gui;
        view1.buttonListener(new Controller.buttonListener());
    }
    static class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("START");
            int N = view1.getNrTasks();
            int S = view1.getNrServers();
            int totalTime = view1.getSimInterval();
            int arrivMin = view1.getArrivMin();
            int arrivMax = view1.getArrivMax();
            int servMin = view1.getServMin();
            int servMax = view1.getServMax();
            Scheduler scheduler= new Scheduler(S);
            Thread t = null;
            try {
                t = new Thread(new SimulationManager(N,S,totalTime,arrivMin,arrivMax,servMin,servMax, scheduler,view1));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            t.start();
        }
    }
}
