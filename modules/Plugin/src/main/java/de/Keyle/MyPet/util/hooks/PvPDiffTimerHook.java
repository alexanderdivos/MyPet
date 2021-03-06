/*
 * This file is part of mypet-plugin_main
 *
 * Copyright (C) 2011-2016 Keyle
 * mypet-plugin_main is licensed under the GNU Lesser General Public License.
 *
 * mypet-plugin_main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mypet-plugin_main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.util.hooks;

import br.net.fabiozumbi12.PvPDiffTimer.Worlds;
import de.Keyle.MyPet.api.Configuration;
import de.Keyle.MyPet.api.util.hooks.PluginHookName;
import de.Keyle.MyPet.api.util.hooks.types.PlayerVersusPlayerHook;
import org.bukkit.World;
import org.bukkit.entity.Player;

@PluginHookName("PvPDiffTimer")
public class PvPDiffTimerHook implements PlayerVersusPlayerHook {
    @Override
    public boolean onEnable() {
        return Configuration.Hooks.USE_PvPDiffTimer;
    }

    @Override
    public boolean canHurt(Player attacker, Player defender) {
        try {
            World w = attacker.getWorld();
            if (!attacker.hasPermission("pvpdifftimer.bypass") && Worlds.isEnabled(w) && !Worlds.isNight(w)) {
                return false;
            }
        } catch (Throwable ignored) {
        }
        return true;
    }
}