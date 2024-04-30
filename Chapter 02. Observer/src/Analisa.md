Program diatas menerapkan pemrosesan pesanan sederhana yang memanfaatkan pola Observer 
untuk memberi tahu pihak-pihak yang berkepentingan tentang perubahan
status pesanan. Pada class "Order" bertindak sebagai subjek, memelihara
daftar pengamat dan memberi tahu mereka tentang perubahan status.
 Class "Customer" dan "LogisticsSystem" bertindak sebagai pengamat,
mengimplementasikan interface "OrderObserver" dan menerima pemberitahuan.