# 📱 My Profile App — MVVM & State Management

[![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-blue?logo=kotlin)](#)
[![Compose](https://img.shields.io/badge/Compose-Multiplatform-success?logo=jetpackcompose)](#)

**Tugas Praktikum Pertemuan 4 — State Management dan MVVM**
IF25-22017 Pengembangan Aplikasi Mobile
Program Studi Teknik Informatika · Institut Teknologi Sumatera

---

## 📖 Deskripsi

Pengembangan lanjutan dari *Profile App*. Aplikasi ini telah diarsiteki ulang menggunakan pola **MVVM (Model-View-ViewModel)** dengan `StateFlow`.

Pembaruan ini menghadirkan:

* ✏️ Fitur **Edit Profil**
* 🌙 **Dark Mode Toggle**
* ⚡ State management reaktif dengan Compose Multiplatform
* 🎨 Tema Merah

---

## 📸 Screenshot

*(Tambahkan screenshot aplikasi kamu di sini)*

<img width="367" height="874" alt="image" src="https://github.com/user-attachments/assets/5405b417-69fb-4d85-9b56-8c429efd24e7" />

<img width="378" height="870" alt="image" src="https://github.com/user-attachments/assets/650017eb-59fc-49ce-adb6-bed8d243df3b" />

<img width="393" height="871" alt="image" src="https://github.com/user-attachments/assets/f81e1989-263d-4ed9-b738-9ecde6a4afcd" />

<img width="420" height="889" alt="image" src="https://github.com/user-attachments/assets/89e3b3d7-7ead-49ad-9540-80e5bd8c9222" />



---

## ✨ Fitur & Pencapaian

* ✅ **ViewModel Implementation (25%)**
  Menggunakan `ProfileViewModel` dengan `MutableStateFlow`

* ✅ **UI State Pattern (20%)**
  State dibungkus dalam `ProfileUiState` (*Single Source of Truth*)

* ✅ **State Hoisting (20%)**
  Komponen `LabeledTextField` bersifat stateless

* ✅ **Edit Feature (20%)**
  Dialog untuk update nama & bio secara real-time

* ✅ **Code Structure (15%)**
  Struktur dipisah: `data`, `ui`, `viewmodel`

* ⭐ **Bonus (+10%)**
  Dark Mode mempengaruhi seluruh UI

* 🟢 **Zero Error Build**
  Menggunakan emoji sistem (tanpa dependency ikon)

---

## 📋 Data Profil Default

| Field   | Value                                                             |
| ------- | ----------------------------------------------------------------- |
| Nama    | Muhammad Piela Nugraha                                            |
| Title   | Mahasiswa Teknik Informatika                                      |
| Email   | [emailmu@student.itera.ac.id](mailto:emailmu@student.itera.ac.id) |
| Telepon | +62 812-3456-7890                                                 |
| Lokasi  | Bandar Lampung, Indonesia                                         |

---

## 🗂️ Struktur Folder

```
composeApp/src/commonMain/kotlin/com/example/myprofileapp/
├── App.kt
├── data/
│   └── ProfileUiState.kt
├── viewmodel/
│   └── ProfileViewModel.kt
└── ui/
    └── ProfileScreen.kt
```

---

## 🏗️ Arsitektur MVVM & Alur Data

```
[ ProfileUiState ]       ← Model
        ↓
[ ProfileViewModel ]     ← ViewModel
        ↓ ↑
[ ProfileScreen ]        ← View
```

**Alur:**

1. ViewModel menyimpan state (`MutableStateFlow`)
2. UI mengobservasi dengan `collectAsState()`
3. User input → event → ViewModel → update state

---

## 🔗 State Hoisting

```
EditProfileDialog
    └── LabeledTextField (Stateless)
            ├── value (↓ dari parent)
            └── onValueChange (↑ ke parent)
```

---

## 🧩 Daftar Composable

| Composable           | Deskripsi                 |
| -------------------- | ------------------------- |
| ProfileHeader        | Header + Dark Mode switch |
| EditProfileDialog    | Form edit profil          |
| LabeledTextField     | Input stateless           |
| StatItem             | Statistik                 |
| InfoItem             | Info profil               |
| ProfileCardContainer | Wrapper card              |
| ProfileScreen        | Main UI                   |

---

## ⚙️ Event Handler (ViewModel)

| Fungsi         | Aksi              |
| -------------- | ----------------- |
| updateProfile  | Update nama & bio |
| toggleDarkMode | Ganti tema        |

---

## 🎨 Tema Warna

| Elemen     | Light Mode        | Dark Mode         |
| ---------- | ----------------- | ----------------- |
| Background | #FFF0F0           | #121212           |
| Surface    | #FFFFFF           | #1E1E1E           |
| Header     | #B71C1C → #E53935 | #6B1111 → #8E1D1D |
| Text       | #212121           | #FFFFFF           |

---

## 🚀 Cara Menjalankan

### Android

1. Buka di Android Studio
2. Tunggu Gradle Sync
3. Jalankan (Shift + F10)

### Desktop (JVM)

```bash
./gradlew :composeApp:run
```

---

## 👨‍💻 Penulis

| Keterangan | Detail                       |
| ---------- | ---------------------------- |
| Nama       | Muhammad Piela Nugraha       |
| NIM        | 123140200                    |
| Kelas      | Pengembangan Aplikasi Mobile |
| Institusi  | Institut Teknologi Sumatera  |

---

**Tugas Praktikum 4 · Tahun Akademik Genap 2025/2026**
