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
package stubs;

import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author joses
 */
public class StubLocation extends Location{
    
    public StubLocation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
    
}
