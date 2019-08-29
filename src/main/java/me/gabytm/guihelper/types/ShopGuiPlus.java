/*
 * Copyright 2019 GabyTM
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package me.gabytm.guihelper.types;

import me.gabytm.guihelper.GUIHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.gabytm.guihelper.utils.StringUtils.colorize;

public class ShopGuiPlus {
    private GUIHelper plugin;

    ShopGuiPlus(GUIHelper plugin) {
        this.plugin = plugin;
    }

    /**
     * Generate items for a ShopGUI+ shop
     * @param gui the gui from where the items are took
     * @param player the command sender
     * @param page the shop page (default 1)
     */
    public void generate(Inventory gui, Player player, int page) {
        try {
            long start = System.currentTimeMillis();

            for (String key : plugin.getConfig().getKeys(false)) {
                plugin.getConfig().set(key, null);
            }

            plugin.getConfig().createSection("shops.GUIHelper.items");

            for (int slot = 0; slot < gui.getSize(); slot++) {
                if (gui.getItem(slot) != null && gui.getItem(slot).getType() != Material.AIR) {
                    String path = "shops.GUIHelper.items.P" + page + "-" + slot;
                    ItemStack item = gui.getItem(slot);
                    ItemMeta meta = item.getItemMeta();

                    addItem(path, item, meta, slot, page);
                }
            }

            plugin.saveConfig();
            player.sendMessage(colorize("&aDone! &7(" + (System.currentTimeMillis() - start) + "ms)"));
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(colorize("&cSomething went wrong, please check the console."));
        }
    }

    @SuppressWarnings("Duplicates")
    private void addItem(String path, ItemStack item, ItemMeta meta, int slot, int page) {
        plugin.getConfig().set(path + ".type", "item");
        plugin.getConfig().set(path + ".item.material", item.getType().toString());
        plugin.getConfig().set(path + ".item.quantity", item.getAmount());

        if (item.getDurability() > 0) plugin.getConfig().set(path + ".item.damage", item.getDurability());

        if (meta.hasDisplayName()) plugin.getConfig().set(path + ".item.name", meta.getDisplayName().replaceAll("§", "&"));

        if (meta.hasLore()) {
            List<String> lore = new ArrayList<>();

            for (String line : meta.getLore()) {
                lore.add(line.replaceAll("§", "&"));
            }

            plugin.getConfig().set(path + ".item.lore", lore);
        }

        plugin.getConfig().set(path + ".buyPrice", 10);
        plugin.getConfig().set(path + ".sellPrice", 10);
        plugin.getConfig().set(path + ".slot", slot);
        plugin.getConfig().set(path + ".page", page);
    }
}