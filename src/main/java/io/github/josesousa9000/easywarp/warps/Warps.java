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
package io.github.josesousa9000.easywarp.warps;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warps {

    private Map<String, Locations> warps;
    private final File warpsFile;
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public Warps(File file) throws IOException, FileNotFoundException{
        this.warps = new TreeMap();
        this.warpsFile = file;
        this.loadFromFile();
    }

    private void saveToFile() {
        try {
            Files.write(GSON.toJson(warps).getBytes(), this.warpsFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadFromFile() throws IOException, FileNotFoundException{
        try {
            this.warps = GSON.fromJson(FileUtils.readFileToString(warpsFile), new TypeToken<Map<String, Locations>>() {
            }.getType());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Locations getLocations(String player) {
        Locations lcts = warps.get(player);
        if (lcts == null) {
            lcts = new Locations();
            warps.put(player, lcts);
        }
        return lcts;
    }

    public void setWarp(String player, String name, Location l) {
        Locations lcts = this.getLocations(player);
        lcts.addWarp(name, l);
        this.saveToFile();
    }

    public Location getWarp(String player, String name) {
        Locations lcts = this.getLocations(player);
        return lcts.getWarp(name);
    }

    public boolean delWarp(String player, String name) {
        Locations lcts = this.getLocations(player);
        boolean val = lcts.deleteWarp(name);
        this.saveToFile();
        return val;
    }

    public void listWarps(Player player) {
        Locations lcts = this.getLocations(player.getName());
        player.sendMessage("[EasyWarp] Your memes:");
        for (Entry<String, Location> entry : lcts.entrySet()) {
            sendWarpInfo(player, entry);
        }

    }

    public void listWarps(Player player, int page) {
        Locations lcts = this.getLocations(player.getName());
        player.sendMessage(String.format("[EasyWarp] Page %d of your memes:\n", page));
        List entries = new ArrayList(lcts.entrySet());
        int start = 5 * (page - 1);
        for (int i = start; i < start + 5; i++) {
            try {
                java.util.Map.Entry entry = (java.util.Map.Entry) entries.get(i);
                sendWarpInfo(player, entry);
            } catch (IndexOutOfBoundsException ex) {
            }
        }

    }

    public void listWarps(Player player, String prefix) {
        Locations lcts = this.getLocations(player.getName());
        player.sendMessage("[EasyWarp] Your memes:");
        for (Entry<String, Location> entry : lcts.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                sendWarpInfo(player, entry);
            }
        }
    }

    private void sendWarpInfo(Player player, Entry<String, Location> entry) {
        Location l = entry.getValue();
        player.sendMessage(String.format(ChatColor.GREEN + "Name:" + ChatColor.WHITE + " %s | " + ChatColor.GREEN + "World:" + ChatColor.WHITE + "%s | X: %d | Y: %d | Z: %d\n",
                entry.getKey(), l.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));
    }
}
