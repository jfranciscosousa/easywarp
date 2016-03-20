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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.bukkit.Location;

class Locations {

    private final Map<String, SimpleLocation> warps;

    public Locations() {
        this.warps = new TreeMap();
    }

    public boolean addWarp(String name, Location l) {
        return warps.put(name, SimpleLocation.fromLocation(l)) == null;
    }

    public Location getWarp(String name) {
        SimpleLocation l = warps.get(name);
        if (l == null) {
            return null;
        } else {
            return l.toLocation();
        }
    }

    public Set<Entry<String, Location>> entrySet() {
        return this.toMap().entrySet();
    }

    public boolean deleteWarp(String name) {
        return warps.remove(name) != null;
    }

    public Map<String, Location> toMap() {
        Map<String, Location> map = new HashMap<>();
        for (Entry<String, SimpleLocation> entry : this.warps.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toLocation());
        }
        return map;
    }
}
