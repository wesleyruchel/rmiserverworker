import java.io.Serial;
import java.io.Serializable;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkerRun implements Runnable, Serializable {
    @Serial
    private static final long serialVersionUID = -4673968256406273836L;
    private boolean stopRun = false;
    private static RMIServerService rmiServerService;

    @Override
    public void run() {
        try {
            while (!stopRun) {
                ArrayList<Integer> job = rmiServerService.deliveryAJob();
                if (job != null) {
                    long startWork = System.currentTimeMillis();
                    ArrayList<Integer> jobDone = sort(job);
                    long finishWork = System.currentTimeMillis();
                    System.out.println("Job completed in " + (finishWork - startWork));
                    rmiServerService.showJobDone(jobDone);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRun() {
        stopRun = true;
    }

    private ArrayList<Integer> sort(ArrayList<Integer> numbers) {
        boolean change = true;
        int aux;
        while (change) {
            change = false;
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (numbers.get(i) > numbers.get(i + 1)) {
                    aux = numbers.get(i);
                    numbers.set(i, numbers.get(i + 1));
                    numbers.set(i + 1, aux);
                    change = true;
                }
            }
        }
        return numbers;
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
