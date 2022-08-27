package com.sec.blog.util;

import net.ipip.ipdb.City;
import net.ipip.ipdb.IPFormatException;
import net.ipip.ipdb.InvalidDatabaseException;

import java.io.IOException;

public class IPdbUtils {
    static City city;

    static {
        try {
            city = new City(IPdbUtils.class.getClassLoader().getResource("ipipfree.ipdb").getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IPdbUtils(){}

    public static String find(String ip) {
        try {
            return city.find(ip, "CN")[1];
        } catch (IPFormatException e) {
            e.printStackTrace();
        } catch (InvalidDatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
