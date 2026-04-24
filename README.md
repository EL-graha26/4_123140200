# 📝 KMP Notes App - Offline First (Tugas Praktikum Minggu 7)

Aplikasi pencatatan (*Notes App*) lintas platform berbasis **Kotlin Multiplatform (KMP)** dan **Compose Multiplatform**. Proyek ini merupakan implementasi dari Tugas Praktikum Pertemuan 7 mata kuliah Pengembangan Aplikasi Mobile, dengan fokus pada arsitektur **Offline-First**, manajemen status (*UI States*), dan desain UI/UX modern ala *Pinterest Grid*.

## ✨ Fitur Utama (Sesuai Spesifikasi Tugas)
Aplikasi ini telah memenuhi seluruh kriteria penugasan:
1. **SQLDelight Database:** Penyimpanan data lokal yang *type-safe* dan tangguh.
2. **CRUD Operations:** Pengguna dapat membuat (*Create*), membaca (*Read*), mengubah (*Update*), dan menghapus (*Delete*) catatan secara *real-time*.
3. **Search Functionality:** Fitur pencarian pintar berdasarkan judul atau isi catatan. Tersedia juga *Filter Chips* (Penting, Ide, Tugas) untuk menyortir catatan secara instan.
4. **Settings Screen (DataStore):** Halaman profil terintegrasi sebagai *dashboard* pengaturan untuk menyimpan preferensi tema (Mode Gelap/Terang) secara persisten menggunakan `Multiplatform Settings`. Dilengkapi juga dengan fitur pengurutan (Terbaru/Terlama).
5. **Offline-First:** Seluruh data catatan dan pengaturan tersimpan secara lokal tanpa memerlukan koneksi internet.
6. **Proper UI States:** Penanganan status UI secara mulus yang menampilkan kondisi *Loading* (saat memuat), *Empty* (saat tidak ada catatan), dan *Content* (saat data berhasil dimuat).

## 🗄️ Skema Database (Database Schema)
Aplikasi ini menggunakan SQLDelight dengan skema tabel tunggal `noteEntity` sebagai berikut:

| Column Name  | Data Type | Constraints                 | Description                                      |
| :---         | :---      | :---                        | :---                                             |
| `id`         | INTEGER   | PRIMARY KEY AUTOINCREMENT   | ID unik untuk setiap catatan                     |
| `title`      | TEXT      | NOT NULL                    | Judul utama catatan                              |
| `content`    | TEXT      | NOT NULL                    | Isi / deskripsi dari catatan                     |
| `isFavorite` | INTEGER   | NOT NULL DEFAULT 0          | Penanda favorit (1 untuk True, 0 untuk False)    |
| `timestamp`  | INTEGER   | NOT NULL                    | Waktu pembuatan catatan (dalam *epoch millis*)   |

> *Catatan: Tabel ini di-generate otomatis oleh SQLDelight melalui file `Note.sq`.*

## 📸 Screenshots
Berikut adalah tangkapan layar dari antarmuka aplikasi dengan mode Terang dan Gelap (*Dark Mode*):

| Home Screen (Light) | Home Screen (Dark) | Profile & Settings | Add/Edit Note |
| :---: | :---: | :---: | :---: |
|<img width="738" height="1600" alt="WhatsApp Image 2026-04-24 at 22 11 24" src="https://github.com/user-attachments/assets/10955de7-2949-499b-ba98-be4f15c8dcfd" />|<img width="738" height="1600" alt="WhatsApp Image 2026-04-24 at 22 11 24 (1)" src="https://github.com/user-attachments/assets/e90dec88-1fab-429a-bbdd-1d56880c2ada" />|<img width="738" height="1600" alt="WhatsApp Image 2026-04-24 at 22 11 24 (2)" src="https://github.com/user-attachments/assets/bb2e1dc0-3414-4169-9185-525c24d31214" />|<img width="1080" height="2340" alt="WhatsApp Image 2026-04-24 at 22 11 24 (3)" src="https://github.com/user-attachments/assets/f9141636-4fa4-4f68-b44c-87b4bc5cd538" />|

## 🎥 Video Demo
Demonstrasi fungsionalitas CRUD, pencarian, perpindahan tema (Settings), dan pembuktian mode offline (tersimpan lokal) dalam durasi 45 detik dapat dilihat pada video berikut:



https://github.com/user-attachments/assets/aa742b61-a29c-46a9-9547-ebda25bf297f



## 🛠️ Teknologi yang Digunakan
* **Framework:** Compose Multiplatform (Jetpack Compose)
* **Arsitektur:** MVVM (Model-View-ViewModel) dengan Kotlin Flow
* **Database:** SQLDelight (`app.cash.sqldelight:android-driver:2.0.1`)
* **Preferences:** Multiplatform Settings (`com.russhwolf:multiplatform-settings-coroutines:1.1.1`)
* **Waktu & Tanggal:** Kotlinx DateTime
* **Navigasi:** Compose Navigation

---

**Dibuat oleh:**
* **Nama:** Muhammad Piela Nugraha
* **NIM:** 123140200
* **Instansi:** Institut Teknologi Sumatera (ITERA)
