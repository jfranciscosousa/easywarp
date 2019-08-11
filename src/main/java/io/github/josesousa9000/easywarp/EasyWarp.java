/*
 * Copyright 2015 josesousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.josesousa9000.easywarp;

import io.github.josesousa9000.easywarp.warps.WarpsAPI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyWarp extends JavaPlugin {

  private WarpCommands commandExecutor;
  private Economy economy;
  private Permission permission;
  private WarpsAPI warps;

  public EasyWarp() {
    // empty constructor needed by Bukkit
  }

  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    economy = rsp.getProvider();
    return economy != null;
  }

  private boolean setupPermissions() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    if (rsp == null) {
      return false;
    }
    permission = rsp.getProvider();
    return permission != null;
  }

  @Override
  public void onEnable() {
    try {
      this.saveDefaultConfig();
      String account = this.getConfig().getString("account");
      boolean usePlayerName = this.getConfig().getBoolean("player_names");

      this.warps = new WarpsAPI(new File(getDataFolder(), "warps.db"), usePlayerName);

      if (!setupEconomy() || !this.getConfig().getBoolean("economy")) {
        getLogger().info("EasyWarp not using economy!");
      }
      if (!setupPermissions()) {
        getLogger().info("EasyWarp not using permissions!");
      }

      this.commandExecutor = new WarpCommands(warps, economy, permission, account);
    } catch (IOException ex) {
      getLogger().info("Couldn't open warps.yml! Starting anew!");
      getLogger().log(Level.WARNING, "Oops! ", ex);
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    boolean result = true;
    if (sender instanceof Player) {
      Player player = (Player) sender;
      switch (command.getName()) {
      case "setwarp":
        result = this.commandExecutor.setWarp(args, player);
        break;
      case "warp":
        result = this.commandExecutor.useWarp(args, player);
        break;
      case "listwarp":
        result = this.commandExecutor.listWarp(args, player);
        break;
      case "delwarp":
        result = this.commandExecutor.deleteWarp(args, player);
        break;
      case "warpbank":
        result = this.commandExecutor.changeBankAccount(args, sender);
        break;
      default:
        break;
      }
    } else {
      result = true;
      sender.sendMessage("Only a player can execute this command");
    }
    return result;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> result = new ArrayList<>();
    if (sender instanceof Player) {
      Player player = (Player) sender;
      String cmd = command.getName();
      if ("warp".equals(cmd) || "delwarp".equals(cmd)) {
        result = this.warps.getWarpsByPrefix(player, args[0]);
      }
    }
    return result;
  }

}
