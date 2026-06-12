import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

public class Show {

    private LocalDate localDate;
    private LocalTime localTime;
    private Movie movie;
    private Map<Seat, SeatStatus> seatStatusMap = new HashMap<>();
    private Map<Seat, StampedLock> seatStampedLockMap = new HashMap<>();
    private Map<Seat, Integer> seatPrice = new HashMap<>();

    public Show(LocalDate localDate, LocalTime localTime, Movie movie, Screen screen, Map<SeatCatogary, Integer> priceStructure) {
        this.localDate = localDate;
        this.localTime = localTime;
        this.movie = movie;


        for (Seat seat : screen.getSeats()) {
            seatStatusMap.put(seat, SeatStatus.AVAILABLE);
            seatStampedLockMap.put(seat, new StampedLock());
            seatPrice.put(seat, priceStructure.get(seat.getSeatCatogary()));
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<StampedLock> getSeatLocks(List<Seat> seats) {
        List<StampedLock> locks = new ArrayList<>();
        for (Seat seat : seats) locks.add(seatStampedLockMap.get(seat));
        return locks;
    }

    public boolean searchSeatsStatus(List<Seat> seats) {
        for (Seat seat : seats) if (seatStatusMap.get(seat) != SeatStatus.AVAILABLE) return false;
        return true;
    }

    public boolean searchSeatsStatusForReservation(List<Seat> seats) {
        List<StampedLock> locks = getSeatLocks(seats);
        List<Long> stamps = new ArrayList<>();

        for (StampedLock lock : locks) stamps.add(lock.tryOptimisticRead());

        if (!searchSeatsStatus(seats)) return false;

        for (int i = 0; i < stamps.size(); i++) {
            if (!locks.get(i).validate(stamps.get(i))) return false;
        }

        return true;
    }


    public int calculateCost(List<Seat> seats) {
        int cost = 0;
        for (Seat seat : seats) cost += seatPrice.get(seat);
        return cost;
    }

    public void bookseats(List<Seat> seats) {
        for (Seat seat : seats) seatStatusMap.put(seat, SeatStatus.BOOCKED);
    }

    public void unBookseats(List<Seat> seats) {
        List<StampedLock> locks = getSeatLocks(seats);
        List<Long> stamps = new ArrayList<>();

        try {
            for (StampedLock lock : locks) stamps.add(lock.writeLock());
            for (Seat seat : seats) seatStatusMap.put(seat, SeatStatus.AVAILABLE);
        } finally {
            for (int i = 0; i < stamps.size(); i++) locks.get(i).unlockWrite(stamps.get(i));
        }


    }

}

