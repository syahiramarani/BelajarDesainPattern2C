import java.util.ArrayList;
import java.util.List;

class Order {
        private int id;
        private String customerName;
        private String product;
        private OrderStatus status;

        public Order(int id, String customerName, String product) {
            this.id = id;
            this.customerName = customerName;
            this.product = product;
            this.status = OrderStatus.PENDING;
        }

        public int getId() {
            return id;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getProduct() {
            return product;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void updateStatus(OrderStatus newStatus) {
            this.status = newStatus;
            notifyObservers();
        }

        private List<OrderObserver> observers = new ArrayList<>();

        public void registerObserver(OrderObserver observer) {
            observers.add(observer);
        }

        public void unregisterObserver(OrderObserver observer) {
            observers.remove(observer);
        }

        private void notifyObservers() {
            for (OrderObserver observer : observers) {
                observer.receiveOrderNotification(this);
            }
        }
    }
enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}