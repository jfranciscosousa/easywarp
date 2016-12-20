/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.mockito.Mockito;

/**
 *
 * @author jose
 */
public class Mocks {

  public static Player mockPlayer() {
    Player player = Mockito.mock(Player.class);
    Mockito.when(player.getName()).thenReturn("test");
    Mockito.when(player.getUniqueId()).thenReturn(UUID.randomUUID());
    return player;
  }

  public static World mockWorld() {
    World world = Mockito.mock(World.class);
    Mockito.when(world.getName()).thenReturn("world");
    return world;
  }

  private Mocks() {
  }
}
