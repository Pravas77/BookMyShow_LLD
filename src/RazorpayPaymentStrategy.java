public class RazorpayPaymentStrategy implements PaymentStrategy {

    public boolean pay(int cost) {

        try {
            Thread.sleep(500);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
