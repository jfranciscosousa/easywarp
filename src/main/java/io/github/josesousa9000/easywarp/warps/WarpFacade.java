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

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author jose
 */
public interface WarpFacade {

    public void setWarp(Player player, String name, Location l);

    public Location getWarp(Player player, String name);

    public boolean delWarp(Player player, String name);

    public void listWarps(Player player);

    public void listWarps(Player player, int page);

    public void listWarps(Player player, String prefix);
    
    public List<String> getWarpsByPrefix(Player player, String prefix);
}
