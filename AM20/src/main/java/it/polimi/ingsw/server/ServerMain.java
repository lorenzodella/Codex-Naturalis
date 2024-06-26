package it.polimi.ingsw.server;

import it.polimi.ingsw.server.rmi.ServerRMI;
import it.polimi.ingsw.server.socket.ServerSKT;

import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class is the main class of the server side of the application.
 * It creates the server manager, the RMI server and the socket server.
 */
public class ServerMain
{
    public static void main(String[] args) {

        if(args.length != 2){
            System.err.println("Usage: java -jar AM20-server.jar <portRMI> <portSKT>");
            System.exit(1);
        }

        try {
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            System.out.println("Server address: "+InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            System.err.println("Error getting local address");
        }

        ServerManager manager = new ServerManager();

        //--RMI server--
        try {

            ServerRMI obj = new ServerRMI(manager);
            //Loggable stub = (Loggable) UnicastRemoteObject.exportObject(obj, Integer.parseInt(args[0]));

            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));

            registry.bind("Loggable", obj);

            System.err.println("Server RMI ready");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Error creating RMI server");
        }

        //--Socket server--
        ServerSKT serverSKT = new ServerSKT(Integer.parseInt(args[1]), manager);
        System.err.println("Server SKT ready");
        serverSKT.startServer();
    }



}
