package dev.mqzn.lib.menus;

import java.util.Comparator;

public class MenuComparator implements Comparator<IMenu> {

    @Override
    public int compare(IMenu m1, IMenu m2) {
        return m1.getUniqueName()
                .compareToIgnoreCase(m2.getUniqueName());
    }


    public static boolean quickComparison(IMenu m1, IMenu m2) {
        return m1.getUniqueName().equalsIgnoreCase(m2.getUniqueName());
    }

}
