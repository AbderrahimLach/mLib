package dev.mqzn.lib.commands.api.args;


public class IP {


    private final String ip;

    private IP(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return ip;
    }

    public static IP from(String ip) {
        return new IP(ip);
    }


}
