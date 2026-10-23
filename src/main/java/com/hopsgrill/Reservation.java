package com.hopsgrill;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Reservation {
    public static final int MAX_PARTY_SIZE = 12;
    public static final Duration SLOT = Duration.ofMinutes(90);

    private final String guestName;
    private final int partySize;
    private final LocalDateTime start;

    public Reservation(String guestName, int partySize, LocalDateTime start) {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new IllegalArgumentException("guest name is required");
        }
        if (partySize < 1 || partySize > MAX_PARTY_SIZE) {
            throw new IllegalArgumentException("party size must be 1-" + MAX_PARTY_SIZE);
        }
        this.guestName = guestName;
        this.partySize = partySize;
        this.start = Objects.requireNonNull(start, "start");
    }

    public String getGuestName() {
        return guestName;
    }

    public int getPartySize() {
        return partySize;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return start.plus(SLOT);
    }

    /** True if the two reservations' time slots intersect. */
    public boolean overlaps(Reservation other) {
        return start.isBefore(other.getEnd()) && other.start.isBefore(getEnd());
    }

    @Override
    public String toString() {
        return guestName + " x" + partySize + " @ " + start;
    }
}
