package view;

public class IpAddress {
    private String ip;
    private long time;

    public IpAddress(String ip) {
        this.ip = ip;
        updateTime();
    }

    public String getIp() {
        return ip;
    }

    public void updateTime() {
        time = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof IpAddress) && this.ip.equals(((IpAddress) obj).ip));
    }

    @Override
    public String toString() {
        return ip;
    }

    public long getTime() {
        return time;
    }
}
