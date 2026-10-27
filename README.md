# Null Launcher (Nothing Bit Minimalist OS for Android & Galaxy S24)

Custom launcher Android minimalis dengan estetika **Nothing OS / Dot-Matrix (Null Style)**, dioptimalkan secara spesifik untuk performa layar **Samsung Galaxy S24 (Dynamic AMOLED 2X 120Hz)**.

---

## 📱 Fitur & Tampilan Sesuai Referensi 1:1

1. **Header Atas (Top Bar)**:
   - Ikon matriks 6-titik `::` dan label monospaced `N U L L` di sisi kiri.
   - Ikon pengatur/slider (*Quick Settings*) di sisi kanan untuk akses cepat pengaturan default launcher Android One UI.
2. **Widget Jam Dot-Matrix & Kalender**:
   - Jam digital LED dot-matrix dinamis (tanpa background card kaku, mengambang elegan di atas wallpaper).
   - Tampilan tanggal monospaced `TUE 27 OCT` di bawah jam.
   - Klik jam untuk membuka Alarm / Jam S24, klik tanggal untuk membuka Google / Samsung Kalender.
3. **Pill Search Bar Minimalis**:
   - Kolom pencarian dengan teks placeholder `Cari aplikasi` font monospaced.
   - Ikon kaca pembesar di kiri dan aksen **dot merah khas Nothing OS** di kanan.
   - Pencarian instan asinkron (real-time filtering) saat mengetik.
4. **Home Screen App Grid (4x2)**:
   - 8 shortcut aplikasi favorit di layar utama (Phone, Messages, Chrome, Photos, Clock, Settings, Calendar, Camera).
   - Label teks monospace bersih dan rapi di bawah setiap ikon.
5. **Dividing Line & Pinned Dock**:
   - Garis pemisah horizontal tipis minimalis di atas dock.
   - 4 aplikasi utama di dock bawah (tanpa label teks, hanya ikon bulat bersih).
6. **AMOLED Dot-Matrix Texture**:
   - Latar belakang True Black (`#07090C` / `#000000`) dengan grid titik matriks mikro halus.

---

## ⚡ Optimalisasi Khusus Samsung Galaxy S24

- **Dynamic AMOLED 2X 120Hz Fluidity**: Dibangun dengan Jetpack Compose modern dan akselerasi GPU perangkat keras, menghasilkan transisi dan scrolling 120 FPS tanpa stutter.
- **Efisiensi Baterai AMOLED**: Warna hitam pekat mematikan piksel OLED Galaxy S24 sepenuhnya, menghemat daya baterai hingga 30%+.
- **Edge-to-Edge & Punch Hole Handling**: Menangani Window Insets (`WindowCompat`) secara otomatis sehingga tata letak tidak terpotong kamera punch-hole maupun gesture bar Samsung.
- **Haptic Feedback One UI**: Sentuhan responsif dengan getaran haptik taktil saat memilih aplikasi.
- **Kompatibilitas Penuh Android 14 / 15 (One UI 6.1 / 7)**: Dilengkapi `android.intent.category.HOME` dan `DEFAULT` agar bisa diatur langsung sebagai Launcher Bawaan (Default Home App).

---

## 🚀 Cara Build Menjadi File `.apk`

### Opsi 1: Otomatis via GitHub Actions (Sangat Direkomendasikan - Gratis & Cepat)
Anda **tidak perlu** menginstall Android Studio atau SDK berat di laptop Anda:
1. Buat repository baru di [GitHub](https://github.com/new).
2. Upload semua file dalam folder `NothingBitLauncher` ini ke repository tersebut (bisa drag & drop via web browser GitHub).
3. Buka tab **Actions** di GitHub repository Anda.
4. GitHub Actions akan otomatis meng-compile APK dalam waktu ~2 menit.
5. Masuk ke hasil workflow dan download artifact `NothingBitLauncher-v1.0.0-Debug.zip` yang berisi file `app-debug.apk`.
6. Kirim file `.apk` ke Galaxy S24 Anda (via Quick Share, WhatsApp, Telegram, atau kabel USB) dan klik **Install**.

### Opsi 2: Compile Menggunakan Android Studio di PC
1. Buka aplikasi **Android Studio**.
2. Pilih **Open** lalu arahkan ke folder `NothingBitLauncher`.
3. Tunggu Gradle selesai sinkronisasi dependensi.
4. Klik menu **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. File `.apk` akan siap di folder `app/build/outputs/apk/debug/app-debug.apk`.

---

## ⚙️ Mengatur Sebagai Default Launcher di Galaxy S24
1. Install file `.apk` di Samsung Galaxy S24 Anda.
2. Buka **Pengaturan (Settings)** di Galaxy S24.
3. Masuk ke **Aplikasi (Apps)** > **Pilih aplikasi default (Choose default apps)**.
4. Pilih **Aplikasi beranda (Home app)** lalu centang **Null Launcher**.
