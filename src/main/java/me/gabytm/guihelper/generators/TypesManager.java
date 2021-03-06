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

package me.gabytm.guihelper.generators;

import me.gabytm.guihelper.GUIHelper;
import me.gabytm.guihelper.generators.types.*;

public final class TypesManager {
    private final ASkyBlock aSkyBlock;
    private final BetterGUI betterGUI;
    private final BossShopPro bossShopPro;
    private final BossShopProMenu bossShopProMenu;
    private final ChestCommands chestCommands;
    private final CratesPlus cratesPlus;
    private final CrazyCrates crazyCrates;
    private final CrazyEnvoy crazyEnvoy;
    private final DeluxeMenus deluxeMenus;
    //private GuiShop guiShop;
    private final LemonMobCoins lemonMobCoins;
    private final ShopGuiPlus shopGuiPlus;
    private final SuperLobbyDeluxe superLobbyDeluxe;

    public ASkyBlock aSkyBlock() { return aSkyBlock; }
    public BetterGUI betterGUI() { return betterGUI; }
    public BossShopPro bossShopPro() { return bossShopPro; }
    public BossShopProMenu bossShopProMenu() { return bossShopProMenu; }
    public ChestCommands chestCommands() { return chestCommands; }
    public CratesPlus cratesPlus() { return cratesPlus; }
    public CrazyEnvoy crazyEnvoy() { return crazyEnvoy; }
    public CrazyCrates crazyCrates() { return crazyCrates; }
    public DeluxeMenus deluxeMenus() { return deluxeMenus; }
    public LemonMobCoins lemonMobCoins() { return lemonMobCoins; }
    public ShopGuiPlus shopGuiPlus() { return shopGuiPlus; }
    public SuperLobbyDeluxe superLobbyDeluxe() { return superLobbyDeluxe; }

    public TypesManager(GUIHelper plugin) {
        aSkyBlock = new ASkyBlock(plugin);
        betterGUI = new BetterGUI(plugin);
        bossShopPro = new BossShopPro(plugin);
        bossShopProMenu = new BossShopProMenu(plugin);
        chestCommands = new ChestCommands(plugin);
        cratesPlus = new CratesPlus(plugin);
        crazyCrates = new CrazyCrates(plugin);
        crazyEnvoy = new CrazyEnvoy(plugin);
        deluxeMenus = new DeluxeMenus(plugin);
        lemonMobCoins = new LemonMobCoins(plugin);
        shopGuiPlus = new ShopGuiPlus(plugin);
        superLobbyDeluxe = new SuperLobbyDeluxe(plugin);
    }
}
