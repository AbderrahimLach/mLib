package dev.mqzn.lib.menus;


public interface IMenu {


    /*
        Unique Id for every menu
     */

    String getUniqueName();


    /*
        Title for the menu

        Works for paginated menu and normal menu
     */

    String getTitle();

}
