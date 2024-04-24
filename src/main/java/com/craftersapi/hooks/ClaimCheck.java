package com.craftersapi.hooks;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Supplier;

public class ClaimCheck  {
    private final Plugin griefPreventionPlugin = Bukkit.getPluginManager().getPlugin("Griefprevention");

    public static GriefPrevention getAPI() {
        return JavaPlugin.getPlugin(GriefPrevention.class);
    }

    public boolean canBreakBlock(Location blockLocation, Player player, Event event) {
        if (griefPreventionPlugin == null || !(griefPreventionPlugin instanceof GriefPrevention)) {
            Bukkit.getLogger().warning("Griefprevention Hook not found. Is it installed?");
            return true;
        }

        GriefPrevention api = getAPI();
        Claim claim = api.dataStore.getClaimAt(blockLocation, true, null);
        if (claim != null) {
            Supplier<String> canBuild = claim.checkPermission(player, ClaimPermission.Inventory, event);
            return canBuild == null;
        }
        return true;
    }
}