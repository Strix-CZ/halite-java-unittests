public class Util {
    public static boolean getStringsHaveEqualLength(String[] rows) {
        if (rows.length == 0)
            return true;

        for (String row : rows) {
            if (row.length() != rows[0].length())
                return false;
        }

        return true;
    }
}
