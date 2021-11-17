package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.MenuPage;
import dev.mqzn.lib.menus.PaginatedMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TestSimplePaginatedMenu extends PaginatedMenu {

    public TestSimplePaginatedMenu(Plugin plugin, UUID viewer) {
        super(plugin, viewer);
    }

    @Override
    public void setPages(UUID uuid) {


        ItemStack item1 = new ItemBuilder(Material.BEACON, 1)
                .setDisplay("&9HELLO").build();
        ItemStack item2 = new ItemBuilder(Material.DIAMOND, 1)
                .setDisplay("&aWelcome").build();

        MenuPage<TestSimplePaginatedMenu> page = new MenuPage<>(plugin,1, this, 3);
        page.setItem(new MenuItem(item1, 13, (player, item) -> {
            player.closeInventory();
            player.sendMessage(Translator.color("&9HELLO &7" + player.getName()));
        }));

        page.setItem(new MenuItem(item2, 15, (p, item) -> {
            p.closeInventory();
            p.sendMessage(Translator.color("&aWelcome &7" + p.getName()));

        }));

        this.addDistinctPage(page);

        /*new PageBuilder<>(1, this).setRows(3)

                .setItems(
                        new MenuItem(it, 13, (p, item) -> {
                    p.closeInventory();
                    p.sendMessage(Translator.color("&9HELLO &7" + p.getName()));
                }),
                        new MenuItem(it2, 15, (p, item) -> {
                    p.closeInventory();
                    p.sendMessage(Translator.color("&aWelcome &7" + p.getName()));

                })).buildPage(map);*/

    }

    @Override
    public String getUniqueName() {
        return "Test-Simple-Paginated";
    }

    @Override
    public String getTitle() {
        return Translator.color("&9Simple-Paginated");
    }


}
