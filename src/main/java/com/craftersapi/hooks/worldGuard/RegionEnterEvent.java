package com.craftersapi.hooks.worldGuard;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class RegionEnterEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final ArrayList<String> region;
    private final Player player;

    public RegionEnterEvent(ArrayList<String> regions, Player player) {
        this.region = regions;
        this.player = player;
    }

    public ArrayList<String> getRegions() {
        return region;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
