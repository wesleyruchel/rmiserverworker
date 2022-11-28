import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkerRun implements Runnable {
    private boolean stopRun = false;
    private static RMIServerService rmiServerService;

    @Override
    public void run() {
        try {
            while (!stopRun) {
                ArrayList<Integer> job = rmiServerService.firstOrDefault();
                if (job != null){
                    rmiServerService.doJob(job);
                    System.out.println(rmiServerService.jobCompletedIn());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRun() {
        stopRun = true;
    }

    public static void main(String[] args) {
        try {
            rmiServerService = (RMIServerService) Naming.lookup("rmi://localhost:8080/Server");

            System.out.println(rmiServerService.connectionOk());

            WorkerRun task = new WorkerRun();
            new Thread(task).start();

            String exit = new Scanner(System.in).next();

            if (exit.equals("exit")) {
                task.stopRun();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
