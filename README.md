# Notes App - Platform Features & Koin DI

[![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-blue?logo=kotlin)](#)
[![Compose](https://img.shields.io/badge/Compose-Multiplatform-success?logo=jetpackcompose)](#)
[![Koin](https://img.shields.io/badge/Koin-Dependency%20Injection-orange)](#)

**Tugas Praktikum Minggu 8 — Upgrade Notes App dengan Platform Features** **Mata Kuliah:** IF25-22017 Pengembangan Aplikasi Mobile  
**Instansi:** Program Studi Teknik Informatika · Institut Teknologi Sumatera  

---

## 📖 Deskripsi

**Notes App** telah di-upgrade untuk mendukung fitur-fitur platform spesifik menggunakan arsitektur **Kotlin Multiplatform (KMP)**. Seluruh *dependencies* aplikasi sekarang dikelola secara terpusat menggunakan framework **Koin Dependency Injection**. Fitur baru mencakup pembacaan status jaringan (online/offline otomatis), informasi perangkat keras (Tipe HP & OS), serta pemantauan baterai secara *real-time* (Nilai Bonus) dengan memisahkan logika menggunakan pola `expect/actual`.

## 📸 Screenshot

<div align="center">
  <img width="300" alt="Offline Banner" src="https://github.com/user-attachments/assets/6aeb1299-aff5-49c7-a1d7-46e8ea2f63d3" />
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img width="300" alt="Device Info" src="https://github.com/user-attachments/assets/186a7a39-968c-4ec6-a5e7-81ab2d26b9c6" />
</div>

## ✅ Pemenuhan Rubrik Penilaian

Aplikasi ini telah memenuhi seluruh kriteria penilaian dan mencapai target Bonus:

* **Koin DI Setup (25%)**: Seluruh dependensi (`DatabaseDriverFactory`, `ObservableSettings`, `NetworkMonitor`, `DeviceInfo`, `BatteryInfo`) di-inject dengan rapi melalui modul Koin pada `androidModule`.
* **expect/actual Pattern (25%)**: Mengimplementasikan pemisahan modul *common* dan *platform-specific* untuk mengambil data `DeviceInfo` dan status `NetworkMonitor` bawaan Android.
* **UI Integration (20%)**: Menampilkan indikator **Network Status** (*Offline Banner*) di layar utama dan menampilkan **Device Info** (Sistem Operasi & Tipe Perangkat) di layar *settings/profile* secara dinamis.
* **Architecture (20%)**: Menerapkan arsitektur *Clean Separation* dengan memisahkan kode platform-specific di `androidMain` dan `iosMain` dari komponen UI di `commonMain`.
* **Code Quality (10%)**: Menulis kode yang bersih, deklaratif, dan menghindari pemanggilan API platform langsung dari komponen Compose UI.
* 🌟 **Bonus (+10%)**: Mengimplementasikan **`BatteryInfo`** dengan `expect/actual` serta `callbackFlow` dan `BroadcastReceiver` untuk memantau Level Baterai dan Status Pengisian Daya (*Charging*) secara responsif tanpa perlu memuat ulang halaman.

## 🧩 Struktur `expect/actual` & DI

| Komponen | Kegunaan |
| :--- | :--- |
| `DeviceInfo` | Mengambil informasi *hardware* bawaan Android (`android.os.Build`) seperti Tipe Perangkat dan Versi OS. |
| `NetworkMonitor` | Memantau perubahan koneksi internet secara *real-time* menggunakan `ConnectivityManager` dan `callbackFlow`. |
| `BatteryInfo` | *(Bonus)* Memantau persentase dan status pengisian baterai menggunakan `BroadcastReceiver`. |
| `AndroidModule.kt` | Modul Koin yang mengatur dan menyediakan instance class *actual* ke dalam UI Compose. |

## 🛠️ Teknologi & Tools

* **Bahasa Utama**: Kotlin
* **Framework UI**: Compose Multiplatform (Material 3)
* **Dependency Injection**: Koin
* **State Management**: `StateFlow`, `collectAsState`
* **Asynchronous Logic**: Kotlinx Coroutines

## 🎥 Video Demo

https://github.com/user-attachments/assets/4a285950-1f78-4f65-85c3-aad1e12c2395

## 🚀 Cara Menjalankan Aplikasi

### Android
1. Buka project di **Android Studio**.
2. Tunggu hingga proses *Sync Gradle* selesai.
3. Pilih target *run* pada modul `composeApp`.
4. Sangat disarankan dijalankan di **perangkat Android fisik** (tekan **Shift + F10**) untuk menguji sensor baterai dan mode pesawat dengan akurat.
