package connection;

import view.IpAddress;
import view.IpPresenter;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Connection implements Closeable {

    private MulticastSocket socket;
    private InetAddress groupIp;
    private static byte[] msgAsk = {1};
    private static byte[] msgAnswer = {2};

    public Connection(String ip) throws IOException {
        groupIp = InetAddress.getByName(ip);
        socket = new MulticastSocket(6790);
        socket.joinGroup(groupIp);
        socket.setSoTimeout(3000);
    }

    public void listen() throws IOException {
        byte[] buf = new byte[1];
        DatagramPacket hi = new DatagramPacket(msgAsk, msgAsk.length, groupIp, 6790);
        socket.send(hi);
        IpPresenter presenter = new IpPresenter();
        DatagramPacket rcvPacket = new DatagramPacket(buf, buf.length);
        while (true) {
            try {
                socket.receive(rcvPacket);
                if (Arrays.equals(rcvPacket.getData(), msgAsk)) { // update state or add ip, answer back
                    presenter.addAddress(new IpAddress(rcvPacket.getAddress().getHostAddress()));
                    hi = new DatagramPacket(msgAnswer, msgAnswer.length, rcvPacket.getAddress(), 6790);
                    socket.send(hi);
                    //System.out.println("sending 1");
                } else if (Arrays.equals(rcvPacket.getData(), msgAnswer)) { // update state or add ip
                    presenter.addAddress(new IpAddress(rcvPacket.getAddress().getHostAddress()));
                }
            } catch (SocketTimeoutException e) { // ask who is alive
                hi = new DatagramPacket(msgAsk, msgAsk.length, groupIp, 6790);
                socket.send(hi);
                //System.out.println("sending 2");
            }
        }
    }

    @Override
    public void close() {
        socket.close();
    }
}
