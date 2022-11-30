import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIServerService extends Remote {
    String connectionOk() throws RemoteException;

    ArrayList<Integer> deliveryAJob() throws RemoteException;

    void showJobDone(ArrayList<Integer> done) throws RemoteException;

}