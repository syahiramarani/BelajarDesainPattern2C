
    class Customer implements OrderObserver {
        private String name;
        private String email;

        public Customer(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public void receiveOrderNotification(Order order) {
            System.out.println("Kepada " + name + ",");
            System.out.println("Orderan anda #" + order.getId() + " telah diperbarui ke status: " + order.getStatus());
            System.out.println("Produk: " + order.getProduct());
            System.out.println("Tolong cek email anda.");
        }
    }

