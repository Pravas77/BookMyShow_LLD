import java.util.List;

public class Booking {
    private User user;
    private Show show;
    private List<Seat> seats;

    public Booking(User user, Show show, List<Seat> seats) {
        this.user = user;
        this.show = show;
        this.seats = seats;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
