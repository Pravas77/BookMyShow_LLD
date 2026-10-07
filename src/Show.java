import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

public class Show {

    private LocalDate localDate;
    private LocalTime localTime;
    private Movie movie;
    private Screen screen;
    private Map<SeatCatogary, Integer> priceStructure;
    private Map<Seat, SeatStatus> seatStatusMap;
    private Map<Seat, StampedLock> seatStampedLockMap;

    public Show(LocalDate localDate, LocalTime localTime, Movie movie, Screen screen, Map<SeatCatogary, Integer> priceStructure) {
        this.localDate = localDate;
        this.localTime = localTime;
        this.movie = movie;
        this.screen = screen;
        this.priceStructure = priceStructure;
        this.seatStatusMap = new ConcurrentHashMap<>();
        this.seatStampedLockMap = new ConcurrentHashMap<>();
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public Map<Seat, SeatStatus> getSeatStatusMap() {
        return seatStatusMap;
    }

    public Map<Seat, StampedLock> getSeatStampedLockMap() {
        return seatStampedLockMap;
    }

    public Map<SeatCatogary, Integer> getPriceStructure() {
        return priceStructure;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public List<StampedLock> getSeatLocks(List<Seat> seats) {

        List<StampedLock> locks = new ArrayList<>();
        for (Seat seat : seats) locks.add(seatStampedLockMap.computeIfAbsent(seat, k -> new StampedLock()));
        return locks;
    }

    public boolean searchSeatsStatus(List<Seat> seats) {

        for (Seat seat : seats) {
            if (seatStatusMap.computeIfAbsent(seat, k -> SeatStatus.AVAILABLE) != SeatStatus.AVAILABLE) return false;
        }
        return true;
    }

    public boolean searchSeatsStatusForReservation(List<Seat> seats) {

        List<StampedLock> locks = getSeatLocks(seats);
        List<Long> stamps = new ArrayList<>();
        for (StampedLock lock : locks) stamps.add(lock.tryOptimisticRead());

        if (!searchSeatsStatus(seats)) return false;

        for (int i = 0; i < locks.size(); i++) {
            if (!locks.get(i).validate(stamps.get(i))) return false;
        }
        return true;
    }


    public int calculateCost(List<Seat> seats) {
        int cost = 0;
        for (Seat seat : seats) cost += priceStructure.get(seat.getSeatCatogary());
        return cost;
    }

    public void bookseats(List<Seat> seats) {
        for (Seat seat : seats) seatStatusMap.put(seat, SeatStatus.BOOKED);
    }

    @Override
    public String toString() {
        return "Show{" + "localDate=" + localDate + ", localTime=" + localTime + ", movie=" + movie + '}';
    }
}

