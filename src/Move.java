public class Move {
    public Location loc;
    public Direction dir;

    public Move(Location loc_, Direction dir_) {
        loc = loc_;
        dir = dir_;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (!loc.equals(move.loc)) return false;
        return dir == move.dir;

    }

    @Override
    public int hashCode() {
        int result = loc.hashCode();
        result = 31 * result + dir.hashCode();
        return result;
    }
}
