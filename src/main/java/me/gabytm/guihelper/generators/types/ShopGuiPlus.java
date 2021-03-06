/*
 * Copyright 2020 GabyTM
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

package me.gabytm.guihelper.generators.types;

import me.gabytm.guihelper.GUIHelper;
import me.gabytm.guihelper.data.Config;
import me.gabytm.guihelper.generators.generators.IGeneratorPageSlot;
import me.gabytm.guihelper.utils.ItemUtil;
import me.gabytm.guihelper.utils.Message;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.stream.Collectors;

public final class ShopGuiPlus implements IGeneratorPageSlot {
    private GUIHelper plugin;
    private ConfigurationSection defaults;

    public ShopGuiPlus(final GUIHelper plugin) {
        this.plugin = plugin;
        defaults = plugin.getConfig().getConfigurationSection("ShopGUIPlus");
    }

    @Override
    public void generate(final Inventory gui, final Player player, final int page) {
        final long start = System.currentTimeMillis();
        final Config config = new Config("ShopGUIPlus", plugin);

        config.empty();

        for (int slot = 0; slot < gui.getSize(); slot++) {
            final ItemStack item = gui.getItem(slot);

            if (ItemUtil.isNull(item)) continue;

            final String path = "shops.GUIHelper.items.P" + page + "-" + slot;

            addItem(config.get().createSection(path), item, slot, page);
        }

        config.save();
        Message.CREATION_DONE.setDuration(System.currentTimeMillis() - start).send(player);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void addItem(final ConfigurationSection section, final ItemStack item, final int slot, final int page) {
        final ItemMeta meta = item.getItemMeta();

        section.set("type", defaults.getString("type"));
        section.set("item.material", item.getType().toString());
        section.set("item.quantity", item.getAmount());

        if (item.getDurability() > 0) {
            section.set("item.damage", item.getDurability());
        }

        if (ItemUtil.isMonsterEgg(item)) {
            section.set("item.damage", ((SpawnEggMeta) meta).getSpawnedType().getTypeId());
        }

        if (meta.hasDisplayName()) {
            section.set("item.name", ItemUtil.getDisplayName(meta));
        }

        if (meta.hasLore()) {
            section.set("item.lore", ItemUtil.getLore(meta));
        }

        if (meta.getItemFlags().size() > 0) {
            section.set("flags", meta.getItemFlags().stream().map(ItemFlag::toString).collect(Collectors.toList()));
        }

        section.set("buyPrice", defaults.getInt("buyPrice"));
        section.set("sellPrice", defaults.getInt("sellPrice"));
        section.set("slot", slot);
        section.set("page", page);
    }
}