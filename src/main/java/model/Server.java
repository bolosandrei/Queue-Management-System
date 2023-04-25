package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private int id;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(int id) {
        this.id = id;
        this.tasks = new ArrayBlockingQueue<>(9999);
        this.waitingPeriod = new AtomicInteger(0);
    }

    public BlockingQueue<Task> getServers() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return this.waitingPeriod;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        this.waitingPeriod.getAndAdd(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while (true) {
            if (!tasks.isEmpty()) {
                //se preia serviceTime-ul Task-ului din pole position-ul cozii
                int serviceTime = tasks.peek().getServiceTime();// take the next task from queue
                if (!Thread.interrupted()) {
                    for (int i = 0; i < serviceTime; i++) {//delay de *serviceTime* secunde
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        if (this.waitingPeriod.get() >= 1) {
                            this.waitingPeriod.decrementAndGet();
                        }
                        // decrementez serviceTime-ul task-ului din coada
                        assert tasks.peek() != null;
                        tasks.peek().setServiceTime(tasks.peek().getServiceTime() - 1);
                    }
                    try {//se scoate task ul din coada
                        tasks.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String s;
        s = "Coada " + this.id + ": ";
        for (Task e : this.tasks) {
            s = s + " " + e.toString();
        }
        return s;
    }
}
