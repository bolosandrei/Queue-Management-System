package bussinesLogic;

import model.Server;
import model.Task;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    private ArrayList<Server> servers;
    private int maxClientsPerServers;
    private int maxNoServers;

    public Scheduler(int maxNoServers) {
        this.servers = new ArrayList<>();
        for (int i = 1; i <= maxNoServers; i++) {
            Server server = new Server(i);
            Thread thread = new Thread(server);
            thread.start();
            this.servers.add(server);
        }
    }

    public List<Server> getServers() {
        return servers;
    }

    public AtomicInteger getTotalWaitingPeriod() {
        AtomicInteger time = new AtomicInteger(0);
        for (Server s : servers) {
            time.addAndGet(s.getWaitingPeriod().get());
        }
        return time;
    }

    public double getAvgWaitingPeriod() {
        if (servers.size() != 0) {
            return (double) (getTotalWaitingPeriod().get()) / servers.size();//maxNoServers
        } else
            return 0;
    }

    public String printServers() {
        String s = "";
        for (Server server : servers) {
            s += server.toString();
            s = s + "\n";
        }
        return s;
    }

    public void addTask(Task task) {
        this.servers.get(0).addTask(task);
        this.servers.get(0).getWaitingPeriod().addAndGet(task.getServiceTime());
        this.servers.sort(new SortServersByWaitingPeriod());
    }

    public static class SortServersByWaitingPeriod implements Comparator<Server> {
        @Override
        public int compare(Server s1, Server s2) {
            return s1.getWaitingPeriod().get() - s2.getWaitingPeriod().get();
        }
    }
}
