package main;

import connection.Connection;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You have to provide an ip address of group to join as the only argument");
            return;
        }

        try {
            new Connection(args[0]).listen();
        } catch (UnknownHostException e) {
            System.out.println("Wrong address format. Please provide a valid Ipv4 or Ipv6 address");
        } catch (SocketException e) {
            System.out.println("Provided Ip address is not a multicast address");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
