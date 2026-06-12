public class StripePaymentStrategy implements PaymentStrategy {
    private PaymentStatus paymentStatus;

    public StripePaymentStrategy() {
        paymentStatus = PaymentStatus.PENDING;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentStatus pay(int cost) {

        try {

            return paymentStatus = PaymentStatus.SUCCESS;
        } catch (Exception e) {
            return paymentStatus = PaymentStatus.FAILED;
        }

    }
}
