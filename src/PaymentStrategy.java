public interface PaymentStrategy {
    PaymentStatus pay(int cost);
}
