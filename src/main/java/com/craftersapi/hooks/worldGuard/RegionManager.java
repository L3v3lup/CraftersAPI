package com.craftersapi.hooks.worldGuard;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RegionManager implements Listener {


    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    /*
    public RegionJoinListener() {
        Bukkit.getScheduler().runTaskTimer(CraftersParadise.getPlugin(), () -> {

            Collection<? extends Player> playerList = Bukkit.getOnlinePlayers();

            for (Player player : playerList) {
                final ArrayList<String> regionArrayList = new ArrayList<>();
                Location location = player.getLocation();
                RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

                if (regions != null) {
                    ApplicableRegionSet set = regions.getApplicableRegions(BukkitAdapter.asBlockVector(location));
                    for (ProtectedRegion singleRegion : set) {
                        regionArrayList.add(singleRegion.getId() + "in world" + location.getWorld());
                    }
                    Bukkit.getPluginManager().callEvent(new RegionEnterEvent(regionArrayList, player));
                }
            }
        }, 0, 20);
    }
     */

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent moveEvent) {

        Location to = moveEvent.getTo();
        Location from = moveEvent.getFrom();
        final ArrayList<String> regionArrayList = new ArrayList<>();

        if (to.equals(from)) return;

        Set<String> oldRegions = getEntityInRegion(from);
        Set<String> newRegions = getEntityInRegion(to);

        for (String newRegion : newRegions) {
            if (!oldRegions.contains(newRegion)) {
                regionArrayList.add(newRegion);
                Bukkit.getPluginManager().callEvent(new RegionEnterEvent(regionArrayList, moveEvent.getPlayer()));
            }
        }
    }

    public Set<String> getEntityInRegion(Location location) {

        Set<String> regionArrayList = new HashSet<>();
        com.sk89q.worldguard.protection.managers.RegionManager regions = container.get(BukkitAdapter.adapt(location.getWorld()));

        if (regions != null) {
            ApplicableRegionSet set = regions.getApplicableRegions(BukkitAdapter.asBlockVector(location));
            for (ProtectedRegion singleRegion : set) {
                regionArrayList.add(singleRegion.getId() + " in world " + location.getWorld().getName());
            }
        }
        return regionArrayList;
    }
}
