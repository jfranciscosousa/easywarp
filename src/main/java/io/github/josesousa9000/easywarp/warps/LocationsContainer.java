/*
 * Copyright 2016 jose.
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

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.bukkit.entity.Player;

/**
 *
 * @author jose
 */
class LocationsContainer {

  private final Map<String, Locations> warps;

  LocationsContainer(Map<String, Locations> warps) {
    this.warps = warps;
  }

  Locations getLocations(Player player) {
    String playerID = player.getName();
    Locations lcts = warps.get(playerID);
    if (lcts == null) {
      lcts = new Locations();
      warps.put(playerID, lcts);
    }
    return lcts;
  }

  Locations putLocations(Player player, Locations locations) {
    return this.warps.put(player.getUniqueId().toString(), locations);
  }

  Map<String, Locations> getLocationsMap() {
    return ImmutableMap.copyOf(warps);
  }
}
