/*
 * This file is part of MyPet
 *
 * Copyright (C) 2011-2016 Keyle
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

package de.Keyle.MyPet.util.locale;

import de.Keyle.MyPet.MyPetPlugin;
import de.Keyle.MyPet.util.BukkitUtil;
import de.Keyle.MyPet.util.Colorizer;
import de.Keyle.MyPet.util.logger.DebugLogger;
import de.Keyle.MyPet.util.player.MyPetPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Translation {
    private static Translation instance = null;

    private Map<String, Language> languages = new HashMap<>();

    private Translation() {
    }

    public static void init() {
        instance = new Translation();
    }

    public static String getString(String key, Player player) {
        if (player == null) {
            return key;
        }

        return getString(key, BukkitUtil.getPlayerLanguage(player));
    }

    public static String getString(String key, CommandSender sender) {
        if (sender == null) {
            return key;
        }
        if (sender instanceof Player) {
            return getString(key, (Player) sender);
        }

        return getString(key, "en");
    }

    public static String getString(String key, MyPetPlayer player) {
        if (player == null) {
            return key;
        }

        return getString(key, player.getLanguage());
    }

    public static String getString(String key, String localeString) {
        if (instance == null) {
            return key;
        }
        return instance.getText(key, localeString);
    }

    public String getText(String key, String localeString) {
        String[] codes = localeString.toLowerCase().split("_");

        String languageCode = codes[0];

        if (!languages.containsKey(languageCode)) {
            languages.put(languageCode, new Language(languageCode));
        }

        Language language = languages.get(languageCode);

        if (codes.length >= 2) {
            return Colorizer.setColors(language.translate(key, codes[1]));
        }
        return Colorizer.setColors(language.translate(key));
    }

    public static ResourceBundle loadLocale(String localeString) {

        JarFile jarFile;
        try {
            jarFile = new JarFile(MyPetPlugin.getPlugin().getFile());
        } catch (IOException ignored) {
            return null;
        }

        ResourceBundle newLocale = null;
        try {
            JarEntry jarEntry = jarFile.getJarEntry("locale/MyPet_" + localeString + ".properties");
            if (jarEntry != null) {
                java.util.ResourceBundle defaultBundle = new PropertyResourceBundle(new InputStreamReader(jarFile.getInputStream(jarEntry), "UTF-8"));
                newLocale = new ResourceBundle(defaultBundle);
            } else {
                throw new IOException();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            DebugLogger.printThrowable(e);
        } catch (IOException ignored) {
        }

        File localeFile = new File(MyPetPlugin.getPlugin().getDataFolder() + File.separator + "locale" + File.separator + "MyPet_" + localeString + ".properties");
        if (localeFile.exists()) {
            if (newLocale == null) {
                newLocale = new ResourceBundle();
            }
            try {
                java.util.ResourceBundle optionalBundle = new PropertyResourceBundle(new InputStreamReader(new FileInputStream(localeFile), "UTF-8"));
                newLocale.addExtensionBundle(optionalBundle);
            } catch (IOException e) {
                e.printStackTrace();
                DebugLogger.printThrowable(e);
            }
        }
        return newLocale;
    }
}