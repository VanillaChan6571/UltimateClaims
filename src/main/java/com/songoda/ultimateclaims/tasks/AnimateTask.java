package com.songoda.ultimateclaims.tasks;

import com.songoda.core.library.compatibility.ParticleHandler;
import com.songoda.ultimateclaims.UltimateClaims;
import com.songoda.ultimateclaims.claim.Claim;
import com.songoda.ultimateclaims.claim.PowerCell;
import com.songoda.core.library.compatibility.ServerVersion;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class AnimateTask extends BukkitRunnable {

    private static AnimateTask instance;
    private static UltimateClaims plugin;

    public AnimateTask(UltimateClaims plug) {
        plugin = plug;
    }

    public static AnimateTask startTask(UltimateClaims plug) {
        plugin = plug;
        if (instance == null) {
            instance = new AnimateTask(plugin);
            instance.runTaskTimerAsynchronously(plugin, 0, 5);
        }

        return instance;
    }

    @Override
    public void run() {
        for (Claim claim : new ArrayList<>(plugin.getClaimManager().getRegisteredClaims())) {
            PowerCell powerCell = claim.getPowerCell();

            if (!powerCell.hasLocation()) continue;
            Location location = powerCell.getLocation().add(.5, .5, .5);

            int x = location.getBlockX() >> 4;
            int z = location.getBlockZ() >> 4;

            if (!location.getWorld().isChunkLoaded(x, z)) {
                continue;
            }
            int red = (powerCell.getCurrentPower() >= 0 ? 5 : 255);
            int green = (powerCell.getCurrentPower() >= 0 ? 255 : 5);
            ParticleHandler.redstoneParticles(location, red, green, 5, 1.3F, 2, 1);
        }
    }
}
