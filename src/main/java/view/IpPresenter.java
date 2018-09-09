package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IpPresenter {
    private List<IpAddress> addresses;
    private String selfIp;
    private Timer timer;
    public IpPresenter(String selfIp) {
        this.selfIp = selfIp;
        addresses = new ArrayList<>();
        timer = new Timer(true);
        timer.schedule(new AliveCheckerTimerTask(),0,6 * 1000);
    }

    public void addAddress(IpAddress ip) {
        if (ip.getIp().equals(selfIp)) return;
        if (addresses.contains(ip)) {
            addresses.get(addresses.indexOf(ip)).updateTime();
        } else {
            addresses.add(ip);
            System.out.println(ip);
        }
    }

    public void removeAddress(IpAddress ip) {
        addresses.remove(ip);
        redraw();
    }

    public void clear() {
        addresses.clear();
        redraw();
    }

    private void redraw() {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(selfIp);
        addresses.forEach(System.out::println);
    }

    class AliveCheckerTimerTask extends TimerTask {

        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            for (int i = 0; i < addresses.size(); ++i) {
                if (currentTime - addresses.get(i).getTime() > 6 * 1000) {
                    addresses.remove(addresses.get(i));
                }
            }
        }
    }
}
