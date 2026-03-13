# My Profile App

[![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-blue?logo=kotlin)](#)
[![Compose](https://img.shields.io/badge/Compose-Multiplatform-success?logo=jetpackcompose)](#)

[cite_start]**Tugas Praktikum Pertemuan 3 — Compose Multiplatform Basics** [cite: 3, 4]  
[cite_start]IF25-22017 Pengembangan Aplikasi Mobile [cite: 1, 2]  
[cite_start]Program Studi Teknik Informatika · Institut Teknologi Sumatera [cite: 6, 7]  

---

## Deskripsi

[cite_start]**My Profile App** adalah aplikasi multiplatform yang dibangun menggunakan Kotlin dan Compose Multiplatform[cite: 9, 10]. Aplikasi ini menampilkan halaman profil pengguna dengan Tema Merah khas ITERA. Aplikasi ini dilengkapi animasi, informasi kontak, dan daftar keahlian. [cite_start]Dirancang dengan paradigma UI Deklaratif [cite: 51, 52] agar performanya optimal dan responsif.

## Screenshot

<img width="418" height="804" alt="image" src="https://github.com/user-attachments/assets/6675a317-1591-4ef5-9584-8680e5c23cc3" />
<img width="419" height="881" alt="image" src="https://github.com/user-attachments/assets/7f16dab1-4025-4dd1-9d3f-9e4bc0e7412c" />

## Pemenuhan Rubrik Penilaian

[cite_start]Aplikasi ini telah memenuhi seluruh kriteria penilaian dan mencapai target Bonus[cite: 639]:

* [cite_start]**Layout Implementation (25%)** [cite: 639][cite_start]: Menggunakan kombinasi tata letak `Column`, `Row`, dan `Box`[cite: 168, 639].
* [cite_start]**Reusable Composables (25%)** [cite: 639][cite_start]: Memiliki 4 custom composable functions (`ProfileHeader`, `StatItem`, `InfoItem`, `ProfileCard`)[cite: 630, 639].
* [cite_start]**UI Components (20%)** [cite: 639][cite_start]: Menggunakan komponen dasar seperti `Text`, `Button`, `Image` (dengan foto asli), dan `Card`[cite: 29, 631, 639].
* [cite_start]**Modifiers (15%)** [cite: 639][cite_start]: Melakukan styling dengan modifier (gradient background, padding, clip, shape, offset, dan border)[cite: 28, 639].
* [cite_start]**Bonus Animasi (+10%)** [cite: 639][cite_start]: Mengimplementasikan `AnimatedVisibility` [cite: 639] untuk efek animasi saat tombol "Lihat Detail" ditekan.
* **Bebas Error**: Menggunakan karakter Emoji bawaan sebagai pengganti *library* ikon eksternal agar aplikasi dapat berjalan lancar tanpa masalah *dependency*.

## Struktur Composable Functions

| Composable | Kegunaan |
| :--- | :--- |
| `ProfileHeader` | Header merah yang menampilkan foto profil (`Image`), nama, gelar, dan bio. |
| `StatItem` | Komponen untuk menampilkan angka statistik (Proyek, IPK, Semester). |
| `InfoItem` | Baris informasi berisi Emoji, label, dan nilai (dipakai untuk Kontak & Keahlian). |
| `ProfileCard` | Komponen `Card` untuk membungkus grup informasi terkait. |
| `App` | Fungsi utama yang menyatukan seluruh komponen UI dan mengatur jalannya animasi. |

## Teknologi & Tools

* [cite_start]**Bahasa Utama**: Kotlin [cite: 9, 52]
* [cite_start]**Framework UI**: Compose Multiplatform [cite: 9, 52] (Material 3)
* [cite_start]**State Management**: `remember`, `mutableStateOf` [cite: 457]
* **Animation**: `androidx.compose.animation`

## Cara Menjalankan Aplikasi

### Android
1. Buka project di **Android Studio**.
2. Tunggu hingga proses *Sync Gradle* selesai.
3. Pilih target *run* pada modul `composeApp`.
4. Jalankan di emulator atau perangkat Android fisik (tekan **Shift + F10**).

### Desktop (JVM)
Buka terminal di dalam folder project dan jalankan perintah berikut:
```bash
./gradlew :composeApp:run
