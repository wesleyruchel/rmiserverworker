import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServerService extends Remote {
    String connectionOk() throws RemoteException;

    ArrayList<Integer> firstOrDefault() throws RemoteException;

    void doJob(ArrayList<Integer> listToSort) throws RemoteException;

    String jobCompletedIn() throws RemoteException;
}