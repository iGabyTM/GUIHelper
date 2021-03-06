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

package me.gabytm.guihelper;

import com.google.common.base.Enums;
import me.gabytm.guihelper.commands.*;
import me.gabytm.guihelper.data.InventoryManager;
import me.gabytm.guihelper.generators.TypesManager;
import me.gabytm.guihelper.listeners.InventoryCloseListener;
import me.gabytm.guihelper.template.TemplateManager;
import me.gabytm.guihelper.utils.StringUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class GUIHelper extends JavaPlugin {
    private final InventoryManager inventoryManager = new InventoryManager();
    private final TemplateManager templateManager = new TemplateManager();
    private final TypesManager typesManager = new TypesManager(this);
    private final String version = getDescription().getVersion();
    public static final String PERMISSION = "guihelper.use";
    public static final boolean ONE_DOT_EIGHT = !Enums.getIfPresent(Material.class, "BEETROOT").isPresent();

    @Override
    public void onEnable() {
        new Metrics(this, 5497);
        saveDefaultConfig();
        templateManager.loadTemplates(getConfig().getConfigurationSection("templates"));

        StringUtil.consoleText(getServer().getConsoleSender(), version);

        loadCommands();
        loadEvents();
    }

    private void loadCommands() {
        final CommandTabCompleter tabCompleter = new CommandTabCompleter(templateManager);

        getCommand("ghcreate").setExecutor(new CreateCommand(typesManager, inventoryManager));
        getCommand("ghcreate").setTabCompleter(tabCompleter);
        getCommand("ghempty").setExecutor(new EmptyCommand(inventoryManager));
        getCommand("ghhelp").setExecutor(new HelpCommand(version));
        getCommand("ghinfo").setExecutor(new InfoCommand());
        getCommand("ghlist").setExecutor(new ListCommand(version));
        getCommand("ghreload").setExecutor(new ReloadCommand(this, templateManager));
        getCommand("ghtemplate").setExecutor(new TemplateCommand(this, inventoryManager, templateManager));
        getCommand("ghtemplate").setTabCompleter(tabCompleter);
    }

    private void loadEvents() {
        Stream.of(
                new InventoryCloseListener(version)
        ).forEach(event -> getServer().getPluginManager().registerEvents(event, this));
    }
}
