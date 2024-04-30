public interface Tugas {
    public void pay(int amount);
    }
    // Pembayaran dengan Kartu Kredit
    class CreditCardStrategy implements Tugas {
        private String name;
        private String cardNumber;

        public CreditCardStrategy(String nm, String ccNum) {
            this.name = nm;
            this.cardNumber = ccNum;
        }

        @Override
        public void pay(int amount) {
            System.out.println(amount + " dibayarkan dengan kartu kredit.");
        }
    }

    // Pembayaran melalui PayPal
    class PaypalStrategy implements Tugas {
        private String emailId;

        public PaypalStrategy(String email) {
            this.emailId = email;
        }

        @Override
        public void pay(int amount) {
            System.out.println(amount + " dibayarkan menggunakan PayPal.");
        }
    }

    // Pembayaran dengan Bitcoin
    class BitcoinStrategy implements Tugas{
        private String bitcoinWalletAddress;

        public BitcoinStrategy(String address) {
            this.bitcoinWalletAddress = address;
        }

        @Override
        public void pay(int amount) {
            System.out.println(amount + " dibayarkan menggunakan Bitcoin.");
        }
    }

