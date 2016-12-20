/*
 * Copyright 2015 José Francisco.
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
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author José Francisco
 */
public class WarpCommands {

  private static final String BASIC_PERMISSION = "easywarp.basic";

  private final WarpsAPI warps;
  private final Economy economy;
  private final Permission permission;
  private String account;

  public WarpCommands(WarpsAPI warps, Economy economy, Permission permission, String account) {
    this.warps = warps;
    this.economy = economy;
    this.permission = permission;
    this.account = account;
  }

  public boolean setWarp(String[] args, Player player) {
    if (permission != null && !permission.has(player, BASIC_PERMISSION)) {
      player.sendMessage(ChatColor.RED + WarpMessages.NO_PERMISSION_MESSAGE);
      return true;
    }
    if (args.length != 1) {
      return false;
    }
    warps.setWarp(player, args[0], player.getLocation());
    player.sendMessage("[EasyWarp] Nice meme!");
    return true;
  }

  public boolean listWarp(String[] args, Player player) {
    if (permission != null && !permission.has(player, BASIC_PERMISSION)) {
      player.sendMessage(ChatColor.RED + WarpMessages.NO_PERMISSION_MESSAGE);
      return true;
    }
    if (args.length == 0) {
      this.warps.listWarps(player);
      return true;
    } else if (args.length == 1) {
      try {
        this.warps.listWarps(player, Integer.parseInt(args[0]));
      } catch (NumberFormatException ex) {
        this.warps.listWarps(player, args[0]);
      }
      return true;
    }
    return false;
  }

  public boolean useWarp(String[] args, Player player) {
    if (permission != null && !permission.has(player, BASIC_PERMISSION)) {
      player.sendMessage(ChatColor.RED + WarpMessages.NO_PERMISSION_MESSAGE);
      return true;
    }
    if (args.length != 1) {
      return false;
    }
    Location dest = warps.getWarp(player, args[0]);
    if (dest == null) {
      player.sendMessage("[EasyWarp] This meme doesn't exist!");
    } else {
      if (economy != null) {
        if (economy.withdrawPlayer(player, 10D).type == EconomyResponse.ResponseType.FAILURE) {
          player.sendMessage("[EasyWarp] You don't have enough money!");
          return true;
        }
        economy.bankDeposit(account, 10D);
      }
      player.teleport(dest);
      player.sendMessage("[EasyWarp] WOWOWOWOWOWOWOWOWWOW!");
    }
    return true;
  }

  public boolean deleteWarp(String[] args, Player player) {
    if (permission != null && !permission.has(player, BASIC_PERMISSION)) {
      player.sendMessage(ChatColor.RED + WarpMessages.NO_PERMISSION_MESSAGE);
      return true;
    }
    if (args.length != 1) {
      return false;
    }
    if (warps.delWarp(player, args[0])) {
      player.sendMessage("[EasyWarp] You just deleted THAT meme!");
    } else {
      player.sendMessage("[EasyWarp] You do not have this meme!");
    }
    return true;
  }

  public boolean changeBankAccount(String[] args, CommandSender sender) {
    if (permission != null && !permission.has(sender, "easywarp.change.account")) {
      sender.sendMessage(ChatColor.RED + WarpMessages.NO_PERMISSION_MESSAGE);
    }
    if (args.length != 1) {
      return false;
    }
    if (economy.bankBalance(args[0]).transactionSuccess()) {
      account = args[0];
    } else {
      sender.sendMessage("This bank account doesn't exist!");
    }
    return true;
  }
}
