<a href="https://eshop-ariq.koyeb.app/">ESHOP🔗</a>
<details>
  <summary>Modul 1</summary>

### Reflection 1
Clean code principles dan secure coding practices yang sudah diimplementasikan adalah meaningful naming conventions, single responsibility, dan encapsulation.  
Saya juga menggunakan UUID untuk ID produk dan input validasi yang basic. Prinsip clean code lain seperti Single Responsibility Principle (SRP) juga diimplementasikan. Single Responsibility Principle (SRP) merujuk pada pemisahan tugas antara controller, service, dan repository.

Selain itu, validasi input perlu diperbaiki, terutama untuk parameter `@PathVariable` pada metode `deleteProductPage`, agar ID yang diterima tidak menyebabkan error atau potensi eksploitasi.  
Dari sisi keamanan, juga penting untuk menambahkan token CSRF pada form untuk mencegah serangan CSRF.

### Reflection 2
Menulis unit test memberikan pemahaman lebih dalam tentang bagaimana aplikasi bekerja dan membantu menemukan bug lebih awal. Jumlah unit test dalam satu kelas tergantung pada kompleksitasnya, tetapi idealnya setiap metode publik yang memiliki logika signifikan harus diuji.  
Untuk memastikan bahwa unit test sudah cukup dalam memverifikasi program, kita bisa menggunakan code coverage sebagai ukuran. Namun, meskipun code coverage mencapai 100%, itu tidak berarti kode bebas dari bug, karena masih ada kemungkinan kesalahan logika atau kasus edge yang tidak terdeteksi.

Mengenai pembuatan functional test suite baru, jika prosedur setup dan variabel instance diulang dari `CreateProductFunctionalTest`, maka ada potensi code duplication, yang bisa menurunkan kualitas kode dan menyulitkan pemeliharaan.  
Sebagai perbaikan, sebaiknya kita menggunakan kelas abstrak atau utilitas bersama untuk menangani setup yang berulang, sehingga functional test suite lebih terstruktur dan mudah diperluas. Dengan cara ini, kode menjadi lebih bersih, mudah dibaca, dan lebih mudah dirawat dalam jangka panjang.

</details>
<details>
  <summary>Modul 2</summary>

### Reflection 1

#### Code quality issue:
- Document empty method body: ada method setup yang empty dan tidak ada dokumentasinya. Fix: tambah komen dokumentasi pada method.
- JUnit test method name: ada nama method yang tidak menggunakan camel case pada JUnit test. Fix: ubah nama method mengikuti camel case.
- Utility class has non-private constructor: `EshopApplication` hanya memiliki static method dan dihimbau menjadikannya utility class. Ini adalah false positive karena class `EshopApplication` mengandung method `main` yang menjadi entry point untuk aplikasi spring boot. Fix: ignore.
- Unnecessary modifier: modifier `public` yang tidak perlu pada interface. Fix: delete modifier `public` tersebut.
- Unused import: ada import yang tidak digunakan. Fix: delete import tersebut.
### Reflection 2
Menurut saya, implementasi workflow sekarang bisa dikatakan cukup untuk memenuhi definisi Continuous Integration and Continuous Deployment atau CI/CD.
#### Continuous Integration:
- Automated Testing: Workflow menjalankan unit test untuk setiap push dan pull request.
- Static Code Analysis: Workflow menggunakan PMD untuk menganalisis code quality dan security vulnerabilities.
- Security Checks: Workflow menggunakan Scorecard analysis yang memastikan supply-chain security dengan mengevaluasi dependencies dan konfigurasi.
##### Continuous Deployment:
- Automated Deployment: Menggunakan Koyeb deployment dilakukan setiap kali ada update pada `main` branch.
</details>

<details>
  <summary>Modul 3</summary>

### Reflection 1
- Single Responsibility Principle (SRP)

    Sekarang setiap class memiliki satu tanggung jawab:
  - ProductController mengontrol request yang berkaitan dengan product.
  - CarController mengontrol request yang berkaitan dengan car. 
- Open/Closed Principle (OCP)

    `CarSrviceImpl implements CarSrvice`, ini menjadikannya jika ada pembuatan implementasi service baru kita tidak perlu mengubah kode yang sudah ada.
- Liskov Substitution Principle (LSP)

  CarController tidak extend ProductControler. CarController tidak seharusnya berperilaku seperti ProductController. Keduanya memiliki tanggung jawab yang berbeda.
Setiap controller bekerja secara independen, sesuai dengan fungsi masing-masing.
- Dependency Inversion Principle (DIP)

    CarController menggunakan CarService yang merupakan interface dan bukan CarServiceImpl. Jadi kita bisa mengganti implementasi tanpa mengubah CarController.
### Reflection 2
- Lebih bisa dikelola
Contoh seperti jika CarServiceImpl perlu diubah, CarController dan sistem lainnya tidak akan terpengaruhi.
- Lebih mudah untuk testing
Pemakaian CarServie interface akan memudahkan unit testing menggunakan mock.
- Kode yang dapat digunakan kembali
CarService sebagai interface bisa mempunyai banyak implementasi.
### Reflection 3
- Lebih sulit untuk dikelola
Contoh jika CarController masih extend ProductController, dan ada method baru pada ProductController, yang seharusnya CarController dan ProductController berdiri sendiri, CarController bisa jadi rusak jika mengguanakn method yang sama dari ProductController.
- Testing yang sulit
CarServiceImpl tidak dapat di mock untuk testing.
- Kode menjadi tidak fleksibel
Dengan tidak menggunakan interface untuk CarService, jika ada perubahan pada CarServiceImpl maka CarController bisa jadi harus ada yang diubah juga.
</details>
<details>
  <summary>Modul 4</summary>

### Reflection 1
TDD sangat membantu memastikan bahwa setiap fitur yang dikembangkan sudah memiliki pengujian sejak awal. 
Saya merasa alur ini cukup efektif dalam meningkatkan kepercayaan terhadap kode, tetapi masih ada beberapa 
kekurangan dalam implementasi. Salah satu masalah yang saya hadapi adalah adanya unnecessary stubbing, yang 
membuat test lebih kompleks dari yang seharusnya. Selain itu, saya juga menyadari bahwa beberapa test yang 
saya buat belum benar-benar mencerminkan kebutuhan aplikasi secara menyeluruh. Ke depannya, saya perlu lebih 
teliti dalam merancang skenario pengujian sebelum menulis kode, agar test benar-benar mencerminkan behavior yang
diharapkan. Saya juga harus lebih disiplin dalam melakukan refactoring pada test untuk menghindari redundansi dan 
memastikan pengujian tetap efisien.
### Reflection 2
Dalam kaitannya dengan prinsip F.I.R.S.T., sebagian besar test sudah memenuhi aspek fast, 
isolated, dan repeatable, tetapi masih ada beberapa kekurangan yang perlu diperbaiki. Salah satu 
masalah utama adalah adanya test yang kurang independen karena terlalu bergantung pada kondisi tertentu, 
sehingga jika satu test gagal, test lain juga berisiko ikut gagal. Selain itu, beberapa test masih menggunakan
stubbing yang tidak perlu, sehingga menambah kompleksitas tanpa manfaat yang jelas. Saya juga menyadari bahwa
readability pada beberapa test masih bisa ditingkatkan agar lebih mudah dipahami oleh pengembang lain. Ke depannya, 
saya harus lebih berhati-hati dalam menentukan kapan menggunakan mock dan kapan cukup mengandalkan data nyata agar 
test lebih representatif. Dengan melakukan perbaikan ini, saya berharap test yang saya buat bisa lebih efektif dalam 
menjaga kualitas kode dan lebih mudah untuk dirawat dalam jangka panjang.









</details>