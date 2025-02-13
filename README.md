- Reflection 1\
Clean code principles dan secure coding practices yang sudah diimplementasikan adalah meaningful naming conventions, single responsibility, dan encapsulation.
Saya juga menggunakan UUID untuk ID produk dan input validasi yang basic. Prinsip clean code lain seperti Single Responsibility Principle (SRP) juga diimplementasikan. Single Responsibility Principle (SRP) merujuk pada pemisahan tugas antara controller, service, dan repository.
Selain itu, validasi input perlu diperbaiki, terutama untuk parameter `@PathVariable` pada metode `deleteProductPage`, agar ID yang diterima tidak menyebabkan error atau potensi eksploitasi.
Dari sisi keamanan, juga penting untuk menambahkan token CSRF pada form untuk mencegah serangan CSRF.
- Reflection 2\
Menulis unit test memberikan pemahaman lebih dalam tentang bagaimana aplikasi bekerja dan membantu menemukan bug lebih awal. Jumlah unit test dalam satu kelas tergantung pada kompleksitasnya, tetapi idealnya setiap metode publik yang memiliki logika signifikan harus diuji. 
Untuk memastikan bahwa unit test sudah cukup dalam memverifikasi program, kita bisa menggunakan code coverage sebagai ukuran. Namun, meskipun code coverage mencapai 100%, itu tidak berarti kode bebas dari bug, karena masih ada kemungkinan kesalahan logika atau kasus edge yang tidak terdeteksi.

  Mengenai pembuatan functional test suite baru, jika prosedur setup dan variabel instance diulang dari `CreateProductFunctionalTest`, maka ada potensi code duplication, yang bisa menurunkan kualitas kode dan menyulitkan pemeliharaan. 
Sebagai perbaikan, sebaiknya kita menggunakan kelas abstrak atau utilitas bersama untuk menangani setup yang berulang, sehingga functional test suite lebih terstruktur dan mudah diperluas. Dengan cara ini, kode menjadi lebih bersih, mudah dibaca, dan lebih mudah dirawat dalam jangka panjang.