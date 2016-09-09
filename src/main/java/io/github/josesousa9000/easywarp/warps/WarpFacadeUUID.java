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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WarpFacadeUUID implements WarpFacade {

    private HashMap<String, Locations> warps;
    private final File warpsFile;
    private final Serializer serializer;

    public WarpFacadeUUID(File file) throws IOException, FileNotFoundException {
        this.warps = new HashMap<>();
        this.warpsFile = file;
        this.serializer = new Serializer();
        this.warps = this.serializer.loadFromFile(file);
    }

    private Locations getLocations(String player) {
        Locations lcts = warps.get(player);
        if (lcts == null) {
            lcts = new Locations();
            warps.put(player, lcts);
        }
        return lcts;
    }

    @Override
    public void setWarp(Player player, String name, Location l) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
        lcts.addWarp(name, l);
        this.serializer.saveToFile(this.warps, warpsFile);
    }

    @Override
    public Location getWarp(Player player, String name) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
        return lcts.getWarp(name);
    }

    @Override
    public boolean delWarp(Player player, String name) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
        boolean val = lcts.deleteWarp(name);
        this.serializer.saveToFile(this.warps, warpsFile);
        return val;
    }

    @Override
    public void listWarps(Player player) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
        player.sendMessage("[EasyWarp] Your memes:");
        for (Entry<String, Location> entry : lcts.entrySet()) {
            sendWarpInfo(player, entry);
        }

    }

    @Override
    public void listWarps(Player player, int page) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
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

    @Override
    public void listWarps(Player player, String prefix) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
        player.sendMessage("[EasyWarp] Your memes:");
        for (Entry<String, Location> entry : lcts.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                sendWarpInfo(player, entry);
            }
        }
    }

    @Override
    public List<String> getWarpsByPrefix(Player player, String prefix) {
        Locations lcts = this.getLocations(player.getUniqueId().toString());
        List<String> warpList = new LinkedList<>();
        for (Entry<String, Location> entry : lcts.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                warpList.add(entry.getKey());
            }
        }
        return warpList;
    }

    private void sendWarpInfo(Player player, Entry<String, Location> entry) {
        Location l = entry.getValue();
        player.sendMessage(String.format(ChatColor.GREEN + "Name:" + ChatColor.WHITE + " %s | " + ChatColor.GREEN + "World:" + ChatColor.WHITE + "%s | X: %d | Y: %d | Z: %d\n",
                entry.getKey(), l.getWorld().getName(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));
    }
}
