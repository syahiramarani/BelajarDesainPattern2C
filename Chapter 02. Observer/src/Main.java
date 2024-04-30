public class Main {
    public static void main(String[] args) {
        Order order = new Order(123, "syahira", "Nasi Padang");
        order.registerObserver(new Customer("syahira", "syahiramarani@gmail.com"));
        order.registerObserver(new LogisticsSystem());

        order.updateStatus(OrderStatus.PROCESSING);
        order.updateStatus(OrderStatus.SHIPPED);
        order.updateStatus(OrderStatus.DELIVERED);
    }
}