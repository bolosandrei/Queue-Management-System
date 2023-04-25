package bussinesLogic;

import model.Task;
import model.Server;
import view.GUI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    private int N;
    private int S;
    private int totalTime;
    private int arrivMin;
    private int arrivMax;
    private int servMin;
    private int servMax;

    private Scheduler scheduler;
    private ArrayList<Task> tasks;
    private int peakMoment;
    private double avgWaiting;
    private final double avgService;

    private GUI gui;
    private FileWriter fw = new FileWriter("Log.txt", true);

    public SimulationManager(int n, int s, int totalTime, int arrivMin, int arrivMax, int servMin, int servMax, Scheduler scheduler, GUI gui) throws IOException {
        this.N = n;
        this.S = s;
        this.totalTime = totalTime;
        this.arrivMin = arrivMin;
        this.arrivMax = arrivMax;
        this.servMin = servMin;
        this.servMax = servMax;
        this.scheduler = new Scheduler(S);
        this.tasks = initList();
        this.avgService = avgService();
        this.gui = gui;
    }

    public int randIntBetween(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }

    public ArrayList<Task> initList() {
        ArrayList<Task> list = new ArrayList<>();
        for (int i = 1; i <= this.N; i++) {
            Task t = new Task(i, randIntBetween(this.arrivMin, this.arrivMax), randIntBetween(servMin, servMax));
            System.out.println(t.toString());
            list.add(t);
        }
        list.sort(new SortingMethodImplementation());
        return list;
    }

    public static class SortingMethodImplementation implements Comparator<Task> {
        @Override
        public int compare(Task t1, Task t2) {
            return t1.getArrivalTime() - t2.getArrivalTime();
        }
    }

    public double avgService() {
        double time = 0;
        for (Task t : this.tasks) {
            time += t.getServiceTime();
        }
        return time / this.N;
    }

    public String updateString(int time, String tasks, String servers) throws IOException {
        String s = "";
//        FileWriter fw = new FileWriter("Log.txt", true);
        s += "Momentul: " + time + "\n" + "Tasks: " + tasks + "\n" + servers;
//        this.fw.write(s);
        this.fw.append("\n" + "Momentul: " + time + "\n");
        this.fw.append("Tasks: " + tasks + "\n");
        this.fw.append(servers);
        return s;
    }

    public String printTasks() {
        String s = "";
        for (Task t : this.tasks) {
            s += t.toString() + " ";
        }
        return s;
    }

    public String printTimes(int peakMoment, double avgWaiting, double avgService) throws IOException {
        String s = "average waiting: " + avgWaiting + "\n" + "average Service: " + avgService + "\n" + "peak Moment: " + peakMoment + "\n";
//        FileWriter fw = new FileWriter("Log.txt", true);
        this.fw.append("average Waiting: " + avgWaiting + "\n");
        this.fw.append("average Service: " + avgService + "\n");
        this.fw.append("peak Moment: " + peakMoment + "\n------------------------------------------------------------------------\n");
//        fw.close();
        return s;
    }

    @Override
    public void run() {
        String s;
        try {
            this.fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AtomicInteger time = new AtomicInteger(0);
        int maxWaiting = 0;
        boolean k = true;
        while ((time.get() <= this.totalTime) && k) {
            if (tasks.isEmpty()) {
                boolean allServersAreEmpty = true;
                for (Server server : scheduler.getServers()) {
                    if (!server.getServers().isEmpty()) {
                        allServersAreEmpty = false;
                        break;
                    }
                }
                if (allServersAreEmpty) {
                    k = false;
                }
            }
            ArrayList<Task> crntTasks = new ArrayList<>();
            for (Task t : this.tasks) {
                if (t.getArrivalTime() == time.get()) {
                    scheduler.addTask(t);
                    crntTasks.add(t);
                }
            }
            this.tasks.removeAll(crntTasks);
            try {
                s = updateString(time.get(), printTasks(), scheduler.printServers());
                System.out.println(s);
                this.gui.setResult(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.avgWaiting += scheduler.getAvgWaitingPeriod();
            time.getAndIncrement();
            if (scheduler.getTotalWaitingPeriod().get() > maxWaiting) {
                this.peakMoment = time.get();
                maxWaiting = scheduler.getTotalWaitingPeriod().get();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.avgWaiting = scheduler.getTotalWaitingPeriod().get();
        try {
            String x = printTimes(this.peakMoment, this.avgWaiting / S, this.avgService);
            // ca sa nu scrie de 2 ori in fisier, am apelat functia doar o data
            this.gui.setResult("Momentul: " + time + "\n" + x); // actualizare in GUI
            System.out.println(x); // printare in consola
//            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
