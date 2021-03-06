package de.Keyle.MyPet.api.util.service.types;

import de.Keyle.MyPet.api.entity.MyPetType;
import de.Keyle.MyPet.api.util.inventory.IconMenuItem;
import de.Keyle.MyPet.api.util.service.ServiceContainer;

public abstract class EggIconService implements ServiceContainer {
    public abstract void updateIcon(MyPetType type, IconMenuItem icon);
}