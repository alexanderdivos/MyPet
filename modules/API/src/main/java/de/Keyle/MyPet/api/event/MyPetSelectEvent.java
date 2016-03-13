/*
 * This file is part of MyPet
 *
 * Copyright © 2011-2016 Keyle
 * MyPet is licensed under the GNU Lesser General Public License.
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.api.event;

import de.Keyle.MyPet.api.entity.StoredMyPet;
import de.Keyle.MyPet.api.player.MyPetPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MyPetSelectEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    boolean isCancelled = false;

    public enum NewStatus {
        Active, Inactive
    }

    private final StoredMyPet myPet;
    private final NewStatus newStatus;

    public MyPetSelectEvent(StoredMyPet mypet, NewStatus newStatus) {
        this.myPet = mypet;
        this.newStatus = newStatus;
    }

    public StoredMyPet getMyPet() {
        return myPet;
    }

    public NewStatus getNewStatus() {
        return newStatus;
    }

    public MyPetPlayer getOwner() {
        if (myPet != null) {
            return myPet.getOwner();
        }
        return null;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
}