package de.oliver.fancyholograms.pages;

import de.oliver.fancyholograms.FancyHolograms;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public final class HologramPageService {

    private final @NotNull FancyHolograms plugin;
    private final Map<String, Integer> pageIndexByHologram = new ConcurrentHashMap<>();
    private final AtomicBoolean papiWarningLogged = new AtomicBoolean(false);

    public HologramPageService(@NotNull FancyHolograms plugin) {
        this.plugin = plugin;
    }

    public boolean switchPage(@NotNull Hologram hologram, @NotNull PageDirection direction) {
        if (!(hologram.getData() instanceof TextHologramData textData)) {
            return false;
        }

        if (!textData.isClickable()) {
            return false;
        }

        List<List<String>> pages = textData.getPages();
        if (pages == null || pages.isEmpty()) {
            return false;
        }

        int pageCount = pages.size();
        String key = hologram.getData().getName().toLowerCase(Locale.ROOT);
        int currentIndex = pageIndexByHologram.getOrDefault(key, 0);
        int nextIndex = resolveNextIndex(currentIndex, pageCount, direction);

        pageIndexByHologram.put(key, nextIndex);
        applyPage(hologram, textData, pages, nextIndex);
        warnMissingPapiOnce();
        return true;
    }

    public void applyInitialDisplay(@NotNull Hologram hologram) {
        if (!(hologram.getData() instanceof TextHologramData textData)) {
            return;
        }

        List<List<String>> pages = textData.getPages();
        if (!textData.isClickable() || pages == null || pages.size() < 2) {
            return;
        }

        String key = hologram.getData().getName().toLowerCase(Locale.ROOT);
        int index = pageIndexByHologram.getOrDefault(key, 0);
        if (index < 0 || index >= pages.size()) {
            index = 0;
            pageIndexByHologram.put(key, 0);
        }

        applyPage(hologram, textData, pages, index);
    }

    public void clear() {
        pageIndexByHologram.clear();
    }

    private int resolveNextIndex(int currentIndex, int pageCount, PageDirection direction) {
        return switch (direction) {
            case PREV -> (currentIndex - 1 + pageCount) % pageCount;
            case NEXT -> (currentIndex + 1) % pageCount;
        };
    }

    private void applyPage(Hologram hologram, TextHologramData textData, List<List<String>> pages, int pageIndex) {
        List<String> pageLines = new ArrayList<>(pages.get(pageIndex));
        List<String> navigationLines = buildNavigation(textData, pageIndex, pages.size());
        if (!navigationLines.isEmpty()) {
            if (textData.getNavigationPosition() == TextHologramData.NavigationPosition.TOP) {
                pageLines.addAll(0, navigationLines);
            } else if (textData.getNavigationPosition() == TextHologramData.NavigationPosition.BOTTOM) {
                pageLines.addAll(navigationLines);
            }
        }

        textData.setDisplayedText(pageLines);
        hologram.queueUpdate();
        hologram.refreshForViewersInWorld();
    }

    private List<String> buildNavigation(TextHologramData textData, int pageIndex, int totalPages) {
        if (!textData.isNavigationEnabled()) {
            return List.of();
        }

        if (textData.getNavigationPosition() == TextHologramData.NavigationPosition.HIDDEN) {
            return List.of();
        }

        if (totalPages < 2 && !textData.isNavigationShowIfSingle()) {
            return List.of();
        }

        String prev = replacePageTokens(textData.getNavigationPrevText(), pageIndex, totalPages);
        String next = replacePageTokens(textData.getNavigationNextText(), pageIndex, totalPages);
        String separator = replacePageTokens(textData.getNavigationSeparator(), pageIndex, totalPages);

        String line = (prev + separator + next).trim();
        if (line.isEmpty()) {
            plugin.getFancyLogger().warn("Navigation line is empty for hologram: " + textData.getName());
            return List.of();
        }

        return List.of(line);
    }

    private String replacePageTokens(String value, int pageIndex, int totalPages) {
        if (value == null) {
            return "";
        }

        return value
                .replace("{page}", String.valueOf(pageIndex + 1))
                .replace("{pages}", String.valueOf(totalPages));
    }

    public enum PageDirection {
        NEXT,
        PREV
    }

    private void warnMissingPapiOnce() {
        if (papiWarningLogged.get()) {
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            if (papiWarningLogged.compareAndSet(false, true)) {
                plugin.getFancyLogger().warn("PlaceholderAPI not found. Hologram pages will render without placeholder replacement.");
            }
        }
    }
}
