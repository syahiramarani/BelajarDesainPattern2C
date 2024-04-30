
    class LogisticsSystem implements OrderObserver {
        public void receiveOrderNotification(Order order) {
            System.out.println("New order #" + order.getId() + " received from customer " + order.getCustomerName() + ".");
            System.out.println("Product: " + order.getProduct());
            System.out.println("Initiating processing and shipping.");
        }
    }
