import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Matrix<T> {
    public final int width;
    public final int height;
    private final List<T> matrix;

    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;

        int count = width * height;
        matrix = new ArrayList<T>(count);
        for (int i = 0; i < count; ++i)
            matrix.add(null);
    }

    public T get(Site site) {
        return get(site.getLocation());
    }

    public T get(Location location) {
        return get(location.x, location.y);
    }

    public T get(int x, int y) {
        return matrix.get(toIndex(x, y));
    }

    public T set(Site site, T element) {
        return set(site.getLocation(), element);
    }

    public T set(Location location, T element) {
        return set(location.x, location.y, element);
    }

    public T set(int x, int y, T element) {
        return matrix.set(toIndex(x, y), element);
    }

    public Stream<T> stream() {
        return matrix.stream();
    }

    private int toIndex(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Coordinates [" + x + ", " + y + "] are out of range for matrix of width " + width + " and height " + height);

        return y * width + x;
    }
}
