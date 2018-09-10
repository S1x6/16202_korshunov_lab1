package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IpPresenter {
    private List<IpAddress> addresses;
    private Timer timer;
    public IpPresenter() {
        addresses = new ArrayList<>();
        timer = new Timer(true);
        timer.schedule(new AliveCheckerTimerTask(),0,6 * 1000);
        redraw();
    }

    public void addAddress(IpAddress ip) {
        if (addresses.contains(ip)) {
            addresses.get(addresses.indexOf(ip)).updateTime();
        } else {
            addresses.add(ip);
            System.out.println(ip);
        }
    }

    private void redraw() {
        System.out.println("---------------");
        addresses.forEach(System.out::println);
    }

    class AliveCheckerTimerTask extends TimerTask {

        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            boolean change = false;
            for (int i = 0; i < addresses.size(); ++i) {
                if (currentTime - addresses.get(i).getTime() > 6 * 1000) { // if we don't have reply from some ip for 6+ seconds, we delete it
                    addresses.remove(addresses.get(i));
                    change = true;
                }
            }
            if (change) redraw();
        }
    }
}
