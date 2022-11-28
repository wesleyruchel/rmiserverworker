import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            RMIServerService ss = new ServerService();
            LocateRegistry.createRegistry(8080);
            Naming.rebind("rmi://localhost:8080/Server", ss);
            System.out.println("Server is running!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
