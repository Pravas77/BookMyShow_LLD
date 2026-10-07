import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.StampedLock;

public class BookingManager {

    public Booking bookSeats(User user, Show show, List<Seat> seats, PaymentStrategy paymentStrategy) {

        Collections.sort(seats, (seat1, seat2) -> seat1.getId() - seat2.getId());

        if (!show.searchSeatsStatusForReservation(seats))
            throw new RuntimeException("Selected seates are not available");

        List<StampedLock> locks = show.getSeatLocks(seats);
        List<Long> stamps = new ArrayList<>();

        try {

            for (StampedLock lock : locks) stamps.add(lock.writeLock());

            if (!show.searchSeatsStatus(seats)) throw new RuntimeException("Selected seates are not available");

            int cost = show.calculateCost(seats);
            boolean paymentStatus = paymentStrategy.pay(cost);

            if (paymentStatus) {
                show.bookseats(seats);
                Booking booking = new Booking(user, show, seats, cost);
                return booking;
            } else {
                throw new RuntimeException("Payment failed please try again");
            }

        } finally {
            for (int i = 0; i < stamps.size(); i++) locks.get(i).unlockWrite(stamps.get(i));
        }
    }

}
