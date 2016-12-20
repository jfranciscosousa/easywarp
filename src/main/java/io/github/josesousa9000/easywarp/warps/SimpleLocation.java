/*
 * Copyright 2016 josesousa.
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author josesousa
 */
class SimpleLocation {

  private final String world;
  private final float x;
  private final float y;
  private final float z;
  private final float yaw;
  private final float pitch;

  public SimpleLocation() {
    this.world = null;
    this.x = 0;
    this.y = 0;
    this.z = 0;
    this.yaw = 0;
    this.pitch = 0;
  }

  @JsonCreator
  public SimpleLocation(@JsonProperty("world") String world,
          @JsonProperty("x") float x,
          @JsonProperty("y") float y,
          @JsonProperty("z") float z,
          @JsonProperty("yaw") float yaw,
          @JsonProperty("pitch") float pitch) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public Location toLocation() {
    return new Location(this.getBukkitWorld(), x, y, z, yaw, pitch);
  }

  public static SimpleLocation fromLocation(Location l) {
    return new SimpleLocation(l.getWorld().getName(),
            l.getBlockX(),
            l.getBlockY(),
            l.getBlockZ(),
            l.getYaw(),
            l.getPitch());
  }

  public World getBukkitWorld() {
    return Bukkit.getWorld(world);
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getZ() {
    return z;
  }

  public float getYaw() {
    return yaw;
  }

  public float getPitch() {
    return pitch;
  }

}
