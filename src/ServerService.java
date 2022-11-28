import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ServerService extends UnicastRemoteObject implements RMIServerService {
    LinkedList<ArrayList<Integer>> jobsAvailable;
    private long started;
    private long completed;

    ServerService() throws RemoteException {
        super();
        jobsAvailable = new LinkedList<>();
        setJobsAvailable(10, 10000);
    }

    @Override
    public String connectionOk() throws RemoteException {
        return "ConnectionOK";
    }

    @Override
    public ArrayList<Integer> firstOrDefault() throws RemoteException {
        return jobsAvailable.size() > 0 ? jobsAvailable.remove() : null;
    }

    @Override
    public void doJob(ArrayList<Integer> listToSort) throws RemoteException {
        setStarted(System.currentTimeMillis());
        System.out.println(sort(listToSort));
        setCompleted(System.currentTimeMillis());
    }

    @Override
    public String jobCompletedIn() {
        return "Job completed in " + (getCompleted() - getStarted());
    }

    public void setJobsAvailable(int nJobs, int nElements) {
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < nJobs; ++i) {
            numbers.clear();
            for (int j = 1; j < nElements + 1; ++j) {
                numbers.add(j);
            }
            Collections.shuffle(numbers, new SecureRandom());
            jobsAvailable.add(numbers);
        }

        System.out.println("The Server generate a list of " + jobsAvailable.size() + " jobs with "
                + nElements + " elements each.");

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

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public long getCompleted() {
        return completed;
    }

    public void setCompleted(long completed) {
        this.completed = completed;
    }
}
