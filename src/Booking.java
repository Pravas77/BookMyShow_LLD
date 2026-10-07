import java.util.List;

public class Booking {
    private User user;
    private Show show;
    private List<Seat> seats;
    private int cost;

    public Booking(User user, Show show, List<Seat> seats, int cost) {
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.cost = cost;
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

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "user=" + user +
                ", show=" + show +
                ", seats=" + seats +
                ", cost=" + cost +
                '}';
    }
}
