import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by Honza on 03.01.2017.
 */
public class GameMapFactory {
    public static final char SEPARATOR = ' ';

    public static final int MY_ID = 1;
    public static final int ENEMY_1 = 2;
    public static final int ENEMY_2 = 3;
    public static final int ENEMY_3 = 4;
    public static final int DEFAULT_OWNER = Site.NO_OWNER;

    public static final int NO_STRENGTH = 0;
    public static final int SMALL_STRENGTH = 1;
    public static final int MEDIUM_STRENGTH = 10;
    public static final int BIG_STRENGTH = 50;
    public static final int HUGE_STRENGTH = 100;
    public static final int MAX_STRENGTH = Site.MAX_STRENGTH;
    public static final int DEFAULT_STRENGTH = MEDIUM_STRENGTH;

    public static final int SMALL_PRODUCTION = 1;
    public static final int MEDIUM_PRODUCTION = 2;
    public static final int BIG_PRODUCTION = 5;
    public static final int DEFAULT_PRODUCTION = SMALL_PRODUCTION;

    private static final Map<Character, Consumer<Site>> siteModifiers = new HashMap<>();
    private static final int SITE_CHARACTERS = 4;

    static {
        siteModifiers.put(SEPARATOR, (site) -> {});

        siteModifiers.put('A', (site) -> site.setOwner(MY_ID));
        siteModifiers.put('B', (site) -> site.setOwner(ENEMY_1));
        siteModifiers.put('C', (site) -> site.setOwner(ENEMY_2));
        siteModifiers.put('D', (site) -> site.setOwner(ENEMY_3));

        siteModifiers.put('0', (site) -> site.setStrength(NO_STRENGTH));
        siteModifiers.put('1', (site) -> site.setStrength(SMALL_STRENGTH));
        siteModifiers.put('2', (site) -> site.setStrength(MEDIUM_STRENGTH));
        siteModifiers.put('3', (site) -> site.setStrength(BIG_STRENGTH));
        siteModifiers.put('4', (site) -> site.setStrength(HUGE_STRENGTH));
        siteModifiers.put('5', (site) -> site.setStrength(MAX_STRENGTH));

        siteModifiers.put('.', (site) -> site.setProduction(SMALL_PRODUCTION));
        siteModifiers.put('-', (site) -> site.setProduction(MEDIUM_PRODUCTION));
        siteModifiers.put('+', (site) -> site.setProduction(BIG_PRODUCTION));
    }

    public static GameMap create(String string) {
        return new GameMapFactory().createInternal(string);
    }

    private GameMap gameMap;
    private String[] rows;
    private int height;
    private int width;

    private GameMap createInternal(String string) {
        splitIntoRows(string);
        parseDimmensions();
        gameMap = new GameMap(width, height);
        parseSites();
        return gameMap;
    }

    private void splitIntoRows(String string) {
        rows = string.split("\n");
        if (!Util.getStringsHaveEqualLength(rows))
            throw new IllegalArgumentException("All rows must have equal length");
    }

    private void parseDimmensions() {
        height = rows.length;

        if (height == 0)
            throw new IllegalArgumentException("Game map must not be empty");

        int widthCharacters = rows[0].length();
        int expectedLengthDifference = (widthCharacters + 1) % SITE_CHARACTERS;
        if (expectedLengthDifference != 0)
            throw new UnexpectedRowLength(expectedLengthDifference);
        width = (widthCharacters + 1) / SITE_CHARACTERS;
    }

    private void parseSites() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                String siteString = getSiteString(x, y);
                Site site = gameMap.getSite(x, y);
                applySiteDefaults(site);
                modifySite(site, siteString);
            }
        }
    }

    private String getSiteString(int x, int y) {
        String row = rows[y];

        int startIndex = x * SITE_CHARACTERS;
        int endIndex = startIndex + SITE_CHARACTERS;

        char separator = (x < width - 1) ? row.charAt(endIndex - 1) : SEPARATOR;
        if (separator != SEPARATOR)
            throw new UnexpectedCharacterInsteadOfSeparator(row, endIndex - 1, separator);

        return row.substring(startIndex, endIndex - 1);
    }

    private void applySiteDefaults(Site site) {
        site.setOwner(DEFAULT_OWNER);
        site.setStrength(DEFAULT_STRENGTH);
        site.setProduction(DEFAULT_PRODUCTION);
    }

    private void modifySite(Site site, String siteString) {
        for (char modifierChar : siteString.toCharArray()) {
            Consumer<Site> modifier = siteModifiers.get(modifierChar);
            if (modifier == null)
                throw new IllegalArgumentException("Unknown character " + modifierChar);

            modifier.accept(site);
        }
    }

    public static class UnexpectedRowLength extends IllegalArgumentException {
        public UnexpectedRowLength(int lengthDifference) {
            super("Unexpected row length. " +
                    "It can be either shorter by " + lengthDifference + " characters " +
                    "or long by " + (SITE_CHARACTERS - lengthDifference) + " characters.");
        }
    }

    public static class UnexpectedCharacterInsteadOfSeparator extends IllegalArgumentException {
        public UnexpectedCharacterInsteadOfSeparator(String row, int index, char unexpectedCharacter) {
            super("Expecting a separating character at row '" + row + "' index " + index + ". Instead I got '" + unexpectedCharacter + "'");
        }
    }
}
