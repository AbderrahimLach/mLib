package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;
import dev.mqzn.lib.commands.api.args.IP;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPParser implements ArgumentParser<IP> {


    @Override
    public boolean matches(String arg) {

        String range = "(\\d{1,2}|([01])\\" + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex = range + "\\." + range + "\\." + range + "\\." + range;

        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false
        if (arg == null) return false;

        Matcher m = p.matcher(arg);
        return m.matches();

    }

    @Override
    public IP parse(String arg) {

        if(!matches(arg)) {
            return null;
        }

        return IP.from(arg);
    }



}
