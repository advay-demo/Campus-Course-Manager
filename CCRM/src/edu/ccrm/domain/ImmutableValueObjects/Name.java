package edu.ccrm.domain.ImmutableValueObjects;

import java.util.Objects;

public final class Name {
    private final String first;
    private final String last;

    public Name(String first, String last) {
        this.first = Objects.requireNonNull(first).trim();
        this.last = Objects.requireNonNull(last).trim();
    }

    public String getFirst() { return first; }
    public String getLast() { return last; }

    public String full() {
        return first + " " + last;
    }

    @Override
    public String toString() {
        return full();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name n = (Name) o;
        return first.equals(n.first) && last.equals(n.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last);
    }
}