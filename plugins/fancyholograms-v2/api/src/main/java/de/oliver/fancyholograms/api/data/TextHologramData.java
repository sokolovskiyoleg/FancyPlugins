package de.oliver.fancyholograms.api.data;

import de.oliver.fancyholograms.api.hologram.Hologram;
import de.oliver.fancyholograms.api.hologram.HologramType;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TextHologramData extends DisplayHologramData {

    public static final TextDisplay.TextAlignment DEFAULT_TEXT_ALIGNMENT = TextDisplay.TextAlignment.CENTER;
    public static final boolean DEFAULT_TEXT_SHADOW_STATE = false;
    public static final boolean DEFAULT_SEE_THROUGH = false;
    public static final int DEFAULT_TEXT_UPDATE_INTERVAL = -1;
    public static final boolean DEFAULT_CLICKABLE = true;
    public static final boolean DEFAULT_NAVIGATION_ENABLED = true;
    public static final boolean DEFAULT_NAVIGATION_SHOW_IF_SINGLE = false;
    public static final String DEFAULT_NAVIGATION_PREV_TEXT = "<<";
    public static final String DEFAULT_NAVIGATION_NEXT_TEXT = ">>";
    public static final String DEFAULT_NAVIGATION_SEPARATOR = " {page}/{pages} ";

    private List<String> text;
    private List<List<String>> pages = new ArrayList<>();
    private Color background;
    private TextDisplay.TextAlignment textAlignment = DEFAULT_TEXT_ALIGNMENT;
    private boolean textShadow = DEFAULT_TEXT_SHADOW_STATE;
    private boolean seeThrough = DEFAULT_SEE_THROUGH;
    private int textUpdateInterval = DEFAULT_TEXT_UPDATE_INTERVAL;
    private boolean clickable = DEFAULT_CLICKABLE;
    private boolean navigationEnabled = DEFAULT_NAVIGATION_ENABLED;
    private NavigationPosition navigationPosition = NavigationPosition.BOTTOM;
    private boolean navigationShowIfSingle = DEFAULT_NAVIGATION_SHOW_IF_SINGLE;
    private String navigationPrevText = DEFAULT_NAVIGATION_PREV_TEXT;
    private String navigationNextText = DEFAULT_NAVIGATION_NEXT_TEXT;
    private String navigationSeparator = DEFAULT_NAVIGATION_SEPARATOR;
    private String navigationNextNpc;
    private String navigationPrevNpc;

    /**
     * @param name     Name of hologram
     * @param location Location of hologram
     *                 Default values are already set
     */
    public TextHologramData(String name, Location location) {
        super(name, HologramType.TEXT, location);
        text = new ArrayList<>(List.of("Edit this line with /hologram edit " + name));
    }

    public List<String> getText() {
        return text;
    }

    public TextHologramData setText(List<String> text) {
        if (!Objects.equals(this.text, text)) {
            this.text = new ArrayList<>(text);
            if (pages.isEmpty()) {
                pages = new ArrayList<>(List.of(new ArrayList<>(this.text)));
            } else if (pages.size() == 1) {
                pages.set(0, new ArrayList<>(this.text));
            }
            setHasChanges(true);
        }

        return this;
    }

    public TextHologramData setDisplayedText(List<String> text) {
        if (!Objects.equals(this.text, text)) {
            this.text = new ArrayList<>(text);
            setHasChanges(true);
        }

        return this;
    }

    public List<List<String>> getPages() {
        return pages;
    }

    public TextHologramData setPages(List<List<String>> pages) {
        this.pages = copyPages(pages);

        if (!this.pages.isEmpty()) {
            this.text = new ArrayList<>(this.pages.get(0));
        }

        setHasChanges(true);
        return this;
    }

    public boolean isClickable() {
        return clickable;
    }

    public TextHologramData setClickable(boolean clickable) {
        if (this.clickable != clickable) {
            this.clickable = clickable;
            setHasChanges(true);
        }

        return this;
    }

    public boolean isNavigationEnabled() {
        return navigationEnabled;
    }

    public TextHologramData setNavigationEnabled(boolean navigationEnabled) {
        if (this.navigationEnabled != navigationEnabled) {
            this.navigationEnabled = navigationEnabled;
            setHasChanges(true);
        }

        return this;
    }

    public NavigationPosition getNavigationPosition() {
        return navigationPosition;
    }

    public TextHologramData setNavigationPosition(NavigationPosition navigationPosition) {
        if (navigationPosition == null) {
            navigationPosition = NavigationPosition.BOTTOM;
        }

        if (!Objects.equals(this.navigationPosition, navigationPosition)) {
            this.navigationPosition = navigationPosition;
            setHasChanges(true);
        }

        return this;
    }

    public boolean isNavigationShowIfSingle() {
        return navigationShowIfSingle;
    }

    public TextHologramData setNavigationShowIfSingle(boolean navigationShowIfSingle) {
        if (this.navigationShowIfSingle != navigationShowIfSingle) {
            this.navigationShowIfSingle = navigationShowIfSingle;
            setHasChanges(true);
        }

        return this;
    }

    public String getNavigationPrevText() {
        return navigationPrevText;
    }

    public TextHologramData setNavigationPrevText(String navigationPrevText) {
        String value = navigationPrevText == null ? "" : navigationPrevText;
        if (!Objects.equals(this.navigationPrevText, value)) {
            this.navigationPrevText = value;
            setHasChanges(true);
        }

        return this;
    }

    public String getNavigationNextText() {
        return navigationNextText;
    }

    public TextHologramData setNavigationNextText(String navigationNextText) {
        String value = navigationNextText == null ? "" : navigationNextText;
        if (!Objects.equals(this.navigationNextText, value)) {
            this.navigationNextText = value;
            setHasChanges(true);
        }

        return this;
    }

    public String getNavigationSeparator() {
        return navigationSeparator;
    }

    public TextHologramData setNavigationSeparator(String navigationSeparator) {
        String value = navigationSeparator == null ? "" : navigationSeparator;
        if (!Objects.equals(this.navigationSeparator, value)) {
            this.navigationSeparator = value;
            setHasChanges(true);
        }

        return this;
    }

    public String getNavigationNextNpc() {
        return navigationNextNpc;
    }

    public TextHologramData setNavigationNextNpc(String navigationNextNpc) {
        if (!Objects.equals(this.navigationNextNpc, navigationNextNpc)) {
            this.navigationNextNpc = navigationNextNpc;
            setHasChanges(true);
        }

        return this;
    }

    public String getNavigationPrevNpc() {
        return navigationPrevNpc;
    }

    public TextHologramData setNavigationPrevNpc(String navigationPrevNpc) {
        if (!Objects.equals(this.navigationPrevNpc, navigationPrevNpc)) {
            this.navigationPrevNpc = navigationPrevNpc;
            setHasChanges(true);
        }

        return this;
    }

    public void addLine(String line) {
        text.add(line);
        setHasChanges(true);
    }

    public void removeLine(int index) {
        text.remove(index);
        setHasChanges(true);
    }

    public Color getBackground() {
        return background;
    }

    public TextHologramData setBackground(Color background) {
        if (!Objects.equals(this.background, background)) {
            this.background = background;
            setHasChanges(true);
        }

        return this;
    }

    public TextDisplay.TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public TextHologramData setTextAlignment(TextDisplay.TextAlignment textAlignment) {
        if (!Objects.equals(this.textAlignment, textAlignment)) {
            this.textAlignment = textAlignment;
            setHasChanges(true);
        }

        return this;
    }

    public boolean hasTextShadow() {
        return textShadow;
    }

    public TextHologramData setTextShadow(boolean textShadow) {
        if (this.textShadow != textShadow) {
            this.textShadow = textShadow;
            setHasChanges(true);
        }

        return this;
    }

    public boolean isSeeThrough() {
        return seeThrough;
    }

    public TextHologramData setSeeThrough(boolean seeThrough) {
        if (this.seeThrough != seeThrough) {
            this.seeThrough = seeThrough;
            setHasChanges(true);
        }

        return this;
    }

    public int getTextUpdateInterval() {
        return textUpdateInterval;
    }

    public TextHologramData setTextUpdateInterval(int textUpdateInterval) {
        if (this.textUpdateInterval != textUpdateInterval) {
            this.textUpdateInterval = textUpdateInterval;
            setHasChanges(true);
        }

        return this;
    }

    @Override
    public boolean read(ConfigurationSection section, String name) {
        super.read(section, name);
        List<String> textFromConfig = section.getStringList("text");

        clickable = section.getBoolean("clickable", DEFAULT_CLICKABLE);
        pages = readPages(section);
        if (pages.isEmpty() && !textFromConfig.isEmpty()) {
            pages = new ArrayList<>(List.of(new ArrayList<>(textFromConfig)));
        }

        if (pages.isEmpty()) {
            pages = new ArrayList<>(List.of(new ArrayList<>(List.of("Could not load hologram text"))));
            //TODO: maybe return false here?
        }

        text = new ArrayList<>(pages.get(0));

        readNavigation(section);

        textShadow = section.getBoolean("text_shadow", DEFAULT_TEXT_SHADOW_STATE);
        seeThrough = section.getBoolean("see_through", DEFAULT_SEE_THROUGH);
        textUpdateInterval = section.getInt("update_text_interval", DEFAULT_TEXT_UPDATE_INTERVAL);

        String textAlignmentStr = section.getString("text_alignment", DEFAULT_TEXT_ALIGNMENT.name().toLowerCase());
        textAlignment = switch (textAlignmentStr.toLowerCase(Locale.ROOT)) {
            case "right" -> TextDisplay.TextAlignment.RIGHT;
            case "left" -> TextDisplay.TextAlignment.LEFT;
            default -> TextDisplay.TextAlignment.CENTER;
        };

        background = null;
        String backgroundStr = section.getString("background", null);
        if (backgroundStr != null) {
            if (backgroundStr.equalsIgnoreCase("transparent")) {
                background = Hologram.TRANSPARENT;
            } else if (backgroundStr.startsWith("#")) {
                background = Color.fromARGB((int) Long.parseLong(backgroundStr.substring(1), 16));
                //backwards compatibility, make rgb hex colors solid color -their alpha is 0 by default-
                if (backgroundStr.length() == 7) background = background.setAlpha(255);
            } else {
                background = Color.fromARGB(NamedTextColor.NAMES.value(backgroundStr.toLowerCase(Locale.ROOT).trim().replace(' ', '_')).value() | 0xC8000000);
            }
        }

        return true;
    }

    @Override
    public boolean write(ConfigurationSection section, String name) {
        super.write(section, name);
        section.set("text", null);
        section.set("clickable", clickable);
        writePages(section);
        writeNavigation(section);
        section.set("text_shadow", textShadow);
        section.set("see_through", seeThrough);
        section.set("text_alignment", textAlignment.name().toLowerCase(Locale.ROOT));
        section.set("update_text_interval", textUpdateInterval);

        final String color;
        if (background == null) {
            color = null;
        } else if (background == Hologram.TRANSPARENT) {
            color = "transparent";
        } else {
            NamedTextColor named = background.getAlpha() == 255 ? NamedTextColor.namedColor(background.asRGB()) : null;
            color = named != null ? named.toString() : '#' + Integer.toHexString(background.asARGB());
        }

        section.set("background", color);

        return true;
    }

    @Override
    public TextHologramData copy(String name) {
        TextHologramData textHologramData = new TextHologramData(name, getLocation());
        textHologramData
                .setText(this.getText())
                .setPages(this.getPages())
                .setBackground(this.getBackground())
                .setTextAlignment(this.getTextAlignment())
                .setTextShadow(this.hasTextShadow())
                .setSeeThrough(this.isSeeThrough())
                .setTextUpdateInterval(this.getTextUpdateInterval())
                .setClickable(this.isClickable())
                .setNavigationEnabled(this.isNavigationEnabled())
                .setNavigationPosition(this.getNavigationPosition())
                .setNavigationShowIfSingle(this.isNavigationShowIfSingle())
                .setNavigationPrevText(this.getNavigationPrevText())
                .setNavigationNextText(this.getNavigationNextText())
                .setNavigationSeparator(this.getNavigationSeparator())
                .setNavigationNextNpc(this.getNavigationNextNpc())
                .setNavigationPrevNpc(this.getNavigationPrevNpc())
                .setScale(this.getScale())
                .setShadowRadius(this.getShadowRadius())
                .setShadowStrength(this.getShadowStrength())
                .setBillboard(this.getBillboard())
                .setTranslation(this.getTranslation())
                .setBrightness(this.getBrightness())
                .setVisibilityDistance(this.getVisibilityDistance())
                .setVisibility(this.getVisibility())
                .setPersistent(this.isPersistent())
                .setLinkedNpcName(this.getLinkedNpcName());

        return textHologramData;
    }

    private List<List<String>> readPages(ConfigurationSection section) {
        ConfigurationSection textSection = section.getConfigurationSection("text");
        if (textSection != null && textSection.isConfigurationSection("pages")) {
            ConfigurationSection pagesSection = textSection.getConfigurationSection("pages");
            return readPagesSection(pagesSection);
        }

        if (section.isConfigurationSection("pages")) {
            ConfigurationSection pagesSection = section.getConfigurationSection("pages");
            return readPagesSection(pagesSection);
        }

        List<?> rawPages = section.getList("pages");
        if (rawPages == null) {
            return new ArrayList<>();
        }

        List<List<String>> parsed = new ArrayList<>();

        for (Object page : rawPages) {
            if (page instanceof List<?> pageLines) {
                List<String> lines = new ArrayList<>();
                for (Object line : pageLines) {
                    if (line != null) {
                        lines.add(String.valueOf(line));
                    }
                }
                if (!lines.isEmpty()) {
                    parsed.add(lines);
                }
            } else if (page instanceof String line) {
                parsed.add(new ArrayList<>(List.of(line)));
            }
        }

        return parsed;
    }

    private void writePages(ConfigurationSection section) {
        List<List<String>> toWrite = pages.isEmpty() ? List.of(text) : pages;
        section.set("pages", null);
        ConfigurationSection textSection = section.createSection("text");
        ConfigurationSection pagesSection = textSection.createSection("pages");

        int index = 1;
        for (List<String> page : toWrite) {
            pagesSection.set(String.valueOf(index++), page);
        }
    }

    private void readNavigation(ConfigurationSection section) {
        ConfigurationSection textSection = section.getConfigurationSection("text");
        ConfigurationSection navigation = textSection != null ? textSection.getConfigurationSection("navigation") : null;
        if (navigation == null) {
            navigation = section.getConfigurationSection("navigation");
        }

        if (navigation == null) {
            navigationEnabled = DEFAULT_NAVIGATION_ENABLED;
            navigationPosition = NavigationPosition.BOTTOM;
            navigationShowIfSingle = DEFAULT_NAVIGATION_SHOW_IF_SINGLE;
            navigationPrevText = DEFAULT_NAVIGATION_PREV_TEXT;
            navigationNextText = DEFAULT_NAVIGATION_NEXT_TEXT;
            navigationSeparator = DEFAULT_NAVIGATION_SEPARATOR;
            return;
        }

        navigationEnabled = navigation.getBoolean("enabled", DEFAULT_NAVIGATION_ENABLED);
        navigationShowIfSingle = navigation.getBoolean("showIfSingle", DEFAULT_NAVIGATION_SHOW_IF_SINGLE);
        navigationPrevText = navigation.getString("prevText", DEFAULT_NAVIGATION_PREV_TEXT);
        navigationNextText = navigation.getString("nextText", DEFAULT_NAVIGATION_NEXT_TEXT);
        navigationSeparator = navigation.getString("separator", DEFAULT_NAVIGATION_SEPARATOR);

        ConfigurationSection npcsSection = navigation.getConfigurationSection("npcs");
        if (npcsSection != null) {
            navigationNextNpc = npcsSection.getString("next", null);
            navigationPrevNpc = npcsSection.getString("prev", null);
        } else {
            navigationNextNpc = null;
            navigationPrevNpc = null;
        }

        String positionValue = navigation.getString("position", NavigationPosition.BOTTOM.name()).toUpperCase(Locale.ROOT);
        navigationPosition = NavigationPosition.safeValueOf(positionValue);
    }

    private void writeNavigation(ConfigurationSection section) {
        ConfigurationSection textSection = section.getConfigurationSection("text");
        if (textSection == null) {
            textSection = section.createSection("text");
        }

        ConfigurationSection navigation = textSection.createSection("navigation");
        navigation.set("enabled", navigationEnabled);
        navigation.set("position", navigationPosition.name().toLowerCase(Locale.ROOT));
        navigation.set("showIfSingle", navigationShowIfSingle);
        navigation.set("prevText", navigationPrevText);
        navigation.set("nextText", navigationNextText);
        navigation.set("separator", navigationSeparator);

        if ((navigationNextNpc != null && !navigationNextNpc.isBlank()) || (navigationPrevNpc != null && !navigationPrevNpc.isBlank())) {
            ConfigurationSection npcsSection = navigation.createSection("npcs");
            npcsSection.set("next", navigationNextNpc);
            npcsSection.set("prev", navigationPrevNpc);
        }
    }

    private List<List<String>> readPagesSection(ConfigurationSection pagesSection) {
        if (pagesSection == null) {
            return new ArrayList<>();
        }

        List<String> keys = new ArrayList<>(pagesSection.getKeys(false));
        keys.sort(Comparator.comparingInt(this::safeParsePageIndex));

        List<List<String>> parsed = new ArrayList<>();
        for (String key : keys) {
            List<String> lines = pagesSection.getStringList(key);
            if (!lines.isEmpty()) {
                parsed.add(new ArrayList<>(lines));
            }
        }

        return parsed;
    }

    private int safeParsePageIndex(String key) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException ignored) {
            return Integer.MAX_VALUE;
        }
    }

    private List<List<String>> copyPages(List<List<String>> pages) {
        List<List<String>> copied = new ArrayList<>();
        if (pages == null) {
            return copied;
        }

        for (List<String> page : pages) {
            copied.add(new ArrayList<>(page));
        }

        return copied;
    }

    public enum NavigationPosition {
        TOP,
        BOTTOM,
        HIDDEN;

        public static NavigationPosition safeValueOf(String value) {
            try {
                return value == null ? BOTTOM : NavigationPosition.valueOf(value);
            } catch (IllegalArgumentException ignored) {
                return BOTTOM;
            }
        }
    }
}
