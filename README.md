# Tugas Praktikum PAM Minggu 5: Navigasi Antar Layar

Repositori ini berisi implementasi **Note App** dengan fitur navigasi *multi-screen* menggunakan Jetpack Navigation Compose, yang dikembangkan untuk memenuhi Tugas Praktikum Pengembangan Aplikasi Mobile.

## 👨‍💻 Identitas
- **Nama:** Muhammad Piela Nugraha
- **NIM:** 123140200
- **Program Studi:** Teknik Informatika
- **Institut:** Institut Teknologi Sumatera (ITERA)

## 📱 Fitur Aplikasi & Pemenuhan Kriteria
Aplikasi ini telah memenuhi seluruh spesifikasi tugas praktikum:
1. **Bottom Navigation:** Terdapat 3 tab utama fungsional yaitu Notes, Favorites, dan Profile.
2. **Passing Arguments (`noteId`):** Pengiriman data `noteId` yang berjalan dengan baik dari Note List ke Note Detail dan Edit Note.
3. **Floating Action Button (FAB):** FAB tersedia di layar utama (Notes) untuk bernavigasi ke AddNoteScreen.
4. **Proper Back Navigation:** Navigasi kembali (*back*) berjalan dengan semestinya dari setiap *screen* (menggunakan *TopAppBar* dan tombol *back* sistem).
5. **Bonus - Navigation Drawer:** Terdapat menu samping (Drawer) yang berisi navigasi tambahan dan fitur interaktif (Switch Dark/Light Mode).

## 📂 Struktur Folder
Struktur proyek telah disesuaikan dengan standar arsitektur yang diminta:
- `components/` : Berisi komponen UI yang dapat digunakan kembali (TopBar, NoteCard, TextFields).
- `data/` : Berisi model data (Note.kt).
- `navigation/` : Berisi definisi rute navigasi menggunakan *sealed class* (Routes.kt).
- `screens/` : Berisi seluruh composable layar utuh (NoteScreens.kt, ProfileScreen.kt).
- `viewmodel/` : Berisi *state management* untuk Profil dan Catatan.

## 🗺️ Navigation Flow Diagram
<img width="1150" height="555" alt="image" src="https://github.com/user-attachments/assets/0539142e-dd7a-4a2b-981a-f31660d8707e" />

Berikut adalah antarmuka aplikasi dari berbagai layar:

| Semua Catatan (Home) | Catatan Favorit | Profil Saya |
| :---: | :---: | :---: |
|  <img width="415" height="883" alt="image" src="https://github.com/user-attachments/assets/0396ea10-412d-489f-bbbb-ae4db00c77ab" /> | <img width="410" height="881" alt="image" src="https://github.com/user-attachments/assets/abc1f8b6-4885-4eb8-954b-eb21103c2877" /> | <img width="440" height="877" alt="image" src="https://github.com/user-attachments/assets/f64e5a7a-0244-45f3-ab5f-ab5b9f37d157" /> |

| Tambah Catatan | Detail Catatan | Edit Catatan | Navigation Drawer |
| :---: | :---: | :---: | :---: |
| <img width="428" height="883" alt="image" src="https://github.com/user-attachments/assets/9cb8af27-3c69-4c82-a19d-3dc726e9b26c" /> | <img width="437" height="900" alt="image" src="https://github.com/user-attachments/assets/c4df9190-f8a0-49f2-a783-68ee63e23beb" /> | <img width="414" height="888" alt="image" src="https://github.com/user-attachments/assets/78a92796-dd3c-494a-830d-b2aae8dd042e" /> | <img width="435" height="904" alt="image" src="https://github.com/user-attachments/assets/14358b22-e233-4e18-a3df-127baa6f1495" />
| |

## 🎥 Video Demo
https://github.com/user-attachments/assets/4893288b-9a35-483d-a09f-0f6842fc2570



---
*Dibuat dengan menggunakan Kotlin Multiplatform & Compose.*
