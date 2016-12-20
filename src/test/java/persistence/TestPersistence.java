/*
 * Copyright 2016 joses.
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
package persistence;

import io.github.josesousa9000.easywarp.warps.WarpsAPI;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author joses
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public class TestPersistence {

  private static final String FILE_PATH = "test.db";

  @BeforeClass
  public static void setup() throws IOException {
    World world = Mocks.mockWorld();
    PowerMockito.mockStatic(Bukkit.class);
    Mockito.when(Bukkit.getWorld("world")).thenReturn(world);
  }

  @Before
  public void newFile() throws IOException {
    File file = new File(FILE_PATH);
    file.createNewFile();
  }

  @After
  public void deleteFile() {
    File file = new File(FILE_PATH);
    file.delete();
  }

  @Test
  public void testSetWarp() throws IOException {
    WarpsAPI warps = new WarpsAPI(new File("test.db"), true);
    Player player = Mocks.mockPlayer();
    Location location = new Location(Mocks.mockWorld(), 1, 2, 3);
    warps.setWarp(player, "test", location);
    WarpsAPI otherWarps = new WarpsAPI(new File("test.db"), true);
    Location otherLocation = otherWarps.getWarp(player, "test");

    assertLocationEquals(location, otherLocation);
  }

  @Test
  public void testDelWarp() throws IOException {
    WarpsAPI warps = new WarpsAPI(new File("test.db"), true);
    Player player = Mocks.mockPlayer();
    Location location = new Location(Mocks.mockWorld(), 1, 2, 3);
    warps.setWarp(player, "test", location);
    warps.delWarp(player, "test");

    WarpsAPI otherWarps = new WarpsAPI(new File("test.db"), true);
    Location otherLocation = otherWarps.getWarp(player, "test");

    Assert.assertNull(otherLocation);
  }

  public void assertLocationEquals(Location location, Location otherLocation) {
    Assert.assertEquals(location.getX(), otherLocation.getX(), 0.01);
    Assert.assertEquals(location.getY(), otherLocation.getY(), 0.01);
    Assert.assertEquals(location.getZ(), otherLocation.getZ(), 0.01);
    Assert.assertEquals(location.getYaw(), otherLocation.getYaw(), 0.01);
    Assert.assertEquals(location.getPitch(), otherLocation.getPitch(), 0.01);
    Assert.assertEquals(location.getWorld().getName(), otherLocation.getWorld().getName());
  }
}
