package it.polimi.ingsw.server;

import it.polimi.ingsw.server.rmi.ServerRMI;
import it.polimi.ingsw.server.socket.ServerSKT;

import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// to test against console:
//  /usr/bin/nc 127.0.0.1 1234
// and type in console: server will receive.
// it will NOT block socket (for now..) when timeout.

public class ServerMain
{
    //TODO: ELEONORA
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

        try {

            ServerRMI obj = new ServerRMI(manager);
            //Loggable stub = (Loggable) UnicastRemoteObject.exportObject(obj, Integer.parseInt(args[0]));

            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));

            registry.bind("Loggable", obj);

            System.err.println("Server RMI ready");
        } catch (RemoteException | AlreadyBoundException e) {
            System.err.println("Error creating RMI server");
        }

        //---------------

        ServerSKT serverSKT = new ServerSKT(Integer.parseInt(args[1]), manager);
        System.err.println("Server SKT ready");
        serverSKT.startServer();
    }



}
