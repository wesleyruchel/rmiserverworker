import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ServerService extends UnicastRemoteObject implements RMIServerService {
    @Serial
    private static final long serialVersionUID = -8752920138287505308L;
    LinkedList<ArrayList<Integer>> jobsAvailable;

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
    public ArrayList<Integer> deliveryAJob() throws RemoteException {
        return jobsAvailable.size() > 0 ? jobsAvailable.remove() : null;
    }

    @Override
    public void showJobDone(ArrayList<Integer> done) throws RemoteException {
        System.out.println(done);
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
}
