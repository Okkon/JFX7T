package sample;

import java.io.Serializable;

public class Pair<F, S> implements Serializable {
    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Pair pair = (Pair) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        if (second != null ? !second.equals(pair.second) : pair.second != null) return false;

        return true;
    }

    protected int getHashCode(Object value) {
        return value.hashCode();
    }

    public int hashCode() {
        int result;
        result = (first != null ? getHashCode(first) : 0);
        result = 1000 * result + (second != null ? getHashCode(second) : 0);
        return result;
    }

    public String toString() {
        return String.format("Pair(%s : %s)", first, second);
    }
}
