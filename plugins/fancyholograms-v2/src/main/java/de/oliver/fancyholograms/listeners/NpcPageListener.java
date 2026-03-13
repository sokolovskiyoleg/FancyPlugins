package de.oliver.fancyholograms.listeners;

import de.oliver.fancyholograms.FancyHolograms;
import de.oliver.fancyholograms.pages.HologramPageService;
import de.oliver.fancyholograms.pages.HologramPageService.PageDirection;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import de.oliver.fancynpcs.api.actions.ActionTrigger;
import de.oliver.fancynpcs.api.events.NpcInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class NpcPageListener implements Listener {

    private final @NotNull HologramPageService pageService;

    public NpcPageListener(@NotNull FancyHolograms plugin) {
        this.pageService = plugin.getHologramPageService();
        this.plugin = plugin;
    }

    private final @NotNull FancyHolograms plugin;

    @EventHandler(ignoreCancelled = true)
    public void onNpcInteract(@NotNull final NpcInteractEvent event) {
        ActionTrigger trigger = event.getInteractionType();
        if (trigger == ActionTrigger.CUSTOM) {
            return;
        }

        String npcName = event.getNpc().getData().getName();
        String npcKey = npcName.toLowerCase(Locale.ROOT);

        for (Hologram hologram : plugin.getHologramsManager().getHolograms()) {
            if (!(hologram.getData() instanceof TextHologramData textData)) {
                continue;
            }

            String nextNpc = normalize(textData.getNavigationNextNpc());
            String prevNpc = normalize(textData.getNavigationPrevNpc());

            if (nextNpc != null && nextNpc.equals(npcKey)) {
                pageService.switchPage(hologram, PageDirection.NEXT);
            }

            if (prevNpc != null && prevNpc.equals(npcKey)) {
                pageService.switchPage(hologram, PageDirection.PREV);
            }
        }
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.toLowerCase(Locale.ROOT);
    }
}
