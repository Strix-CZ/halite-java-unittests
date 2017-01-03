public class Location {
    public final int x;
    public final int y;

    public Location(int x_, int y_) {
        x = x_;
        y = y_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ']';
    }
}
