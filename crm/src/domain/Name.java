package edu.ccrm.domain;

/**
 * Immutable value class for a person's name.
 */
public final class Name {
    private final String first;
    private final String middle;
    private final String last;

    public Name(String first, String middle, String last) {
        this.first = first == null ? "" : first;
        this.middle = middle == null ? "" : middle;
        this.last = last == null ? "" : last;
    }

    public String fullName() {
        return (first + " " + (middle.isBlank() ? "" : middle + " ") + last).trim();
    }

    @Override
    public String toString() { return fullName(); }
}
