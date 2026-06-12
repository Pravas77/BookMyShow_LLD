public class RazorpayPaymentStrategy implements PaymentStrategy {
    private PaymentStatus paymentStatus;

    public RazorpayPaymentStrategy() {
        paymentStatus = PaymentStatus.PENDING;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentStatus pay(int cost) {

        try {
            Thread.sleep(2000);
            return paymentStatus = PaymentStatus.SUCCESS;
        } catch (Exception e) {
            return paymentStatus = PaymentStatus.FAILED;
        }

    }
}
