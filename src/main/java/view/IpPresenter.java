package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IpPresenter {
    private List<IpAddress> addresses;
   // private String selfIp;
    private Timer timer;
    public IpPresenter() {
       // this.selfIp = selfIp + " <- self";
        addresses = new ArrayList<>();
        timer = new Timer(true);
        timer.schedule(new AliveCheckerTimerTask(),0,6 * 1000);
        redraw();
    }

    public boolean addAddress(IpAddress ip) {
        //if (ip.getIp().equals(selfIp)) return false;
        if (addresses.contains(ip)) {
            addresses.get(addresses.indexOf(ip)).updateTime();
            return false;
        } else {
            addresses.add(ip);
            System.out.println(ip);
        }
        return true;
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
        System.out.println("---------------");
       // System.out.println(selfIp);
        addresses.forEach(System.out::println);
    }

    class AliveCheckerTimerTask extends TimerTask {

        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            boolean change = false;
            for (int i = 0; i < addresses.size(); ++i) {
                if (currentTime - addresses.get(i).getTime() > 6 * 1000) {
                    addresses.remove(addresses.get(i));
                    change = true;
                }
            }
            if (change) redraw();
        }
    }
}
