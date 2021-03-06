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
import me.gabytm.guihelper.generators.generators.IGeneratorPage;
import me.gabytm.guihelper.utils.ItemUtil;
import me.gabytm.guihelper.utils.Message;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CrazyEnvoy implements IGeneratorPage {
    private GUIHelper plugin;
    private ConfigurationSection defaults;

    public CrazyEnvoy(final GUIHelper plugin) {
        this.plugin = plugin;
        defaults = plugin.getConfig().getConfigurationSection("CrazyCrates");
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void generate(final Inventory gui, final Player player, final int page) {
        final long start = System.currentTimeMillis();
        final Config config = new Config("CrazyEnvoy/tiers", plugin);

        config.empty();

        for (int slot = 0; slot < gui.getSize(); slot++) {
            final ItemStack item = gui.getItem(slot);

            if (ItemUtil.isNull(item)) continue;

            String path = "Prizes." + (page > 1 ? slot + 1 + (53 * (page - 1)) : slot);

            addItem(config.get().createSection(path), item);
        }

        config.save();
        Message.CREATION_DONE.setDuration(System.currentTimeMillis() - start).send(player);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void addItem(final ConfigurationSection section, final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        final StringBuilder rewardItem = new StringBuilder();
        final StringBuilder rewardItemMaterial = new StringBuilder();
        final StringBuilder rewardItemAmount = new StringBuilder();
        final StringBuilder rewardItemDisplayName = new StringBuilder();
        final StringBuilder rewardItemLore = new StringBuilder();
        final StringBuilder rewardItemEnchantments = new StringBuilder();
        final List<String> rewardItemsList = new ArrayList<>();

        if (meta.hasDisplayName()) {
            rewardItemDisplayName.append(", Name:").append(ItemUtil.getDisplayName(meta));
        }

        rewardItemMaterial.append("Item:").append(item.getType().toString());

        if (item.getDurability() > 0) {
            rewardItemMaterial.append(":").append(item.getDurability());
        }

        if (ItemUtil.isMonsterEgg(item)) {
            rewardItemMaterial.append(":").append(((SpawnEggMeta) meta).getSpawnedType().getTypeId());
        }

        rewardItemAmount.append(", Amount:").append(item.getAmount());

        if (meta.hasLore()) {
            rewardItemLore.append(", Lore:");
            ItemUtil.getLore(meta).forEach(line -> rewardItemLore.append(line).append(","));
        }

        if (meta.hasEnchants()) {
            setEnchantments(meta.getEnchants(), rewardItemEnchantments);
        }

        if (meta instanceof EnchantmentStorageMeta) {
            final EnchantmentStorageMeta esm = (EnchantmentStorageMeta) meta;
            setEnchantments(esm.getStoredEnchants(), rewardItemEnchantments);
        }

        section.set("Chance", defaults.getInt("Chance", 10));
        section.set("Drop-Items", defaults.getBoolean("Drop-Items"));
        rewardItem.append(rewardItemMaterial.toString()).append(rewardItemAmount.toString());

        if (rewardItemDisplayName.length() > 0) {
            rewardItem.append(rewardItemDisplayName.toString());
        }

        if (rewardItemLore.length() > 0) {
            rewardItem.append(rewardItemLore.toString(), 0, rewardItemLore.length() - 1);
        }

        if (rewardItemEnchantments.length() > 0) {
            rewardItem.append(rewardItemEnchantments.toString());
        }

        rewardItemsList.add(rewardItem.toString());
        section.set("Items", rewardItemsList);
    }

    public void setEnchantments(final Map<Enchantment, Integer> enchantments, final StringBuilder builder) {
        enchantments.forEach((enchantment, level) -> builder.append(", ").append(enchantment.getName()).append(":").append(level));
    }
}