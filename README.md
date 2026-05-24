# Chaldea

## Informasi Mahasiswa

- Nama : Naufal Rakan Ramadhan
- NIM : 2410501042
- Kelas : B

## Deskripsi Aplikasi

Chaldea adalah aplikasi Android yang berfungsi untuk menjelajahi daftar Servant dari game Fate/Grand Order (FGO). Aplikasi ini dirancang untuk menampilkan informasi dasar Servant.

## Fitur yang Diimplementasikan
### 1. RecyclerView: List & Grid
Aplikasi ini mendukung dua mode tampilan yang dapat diganti secara dinamis melalui menu di Toolbar:
- **List Mode**: Menampilkan daftar Servant secara vertikal dengan detail yang lebih lengkap.
- **Grid Mode**: Menampilkan daftar Servant dalam bentuk grid 2 kolom untuk efisiensi ruang layar.
- Menggunakan LinearLayoutManager dan GridLayoutManager untuk pengaturan tata letak.

### 2. Networking & Pull-to-Refresh
- **Volley Library**: Menggunakan library Volley untuk melakukan request HTTP secara asynchronous ke API Atlas Academy. Data diproses dalam format JSON untuk mengambil informasi nama, rarity, attribute, dan icon Servant.
- **SwipeRefreshLayout**: Implementasi fitur pull-to-refresh yang memungkinkan pengguna untuk memperbarui data Servant secara manual dengan menarik layar ke bawah.

### 3. API yang Digunakan
Data Servant diambil secara real-time dari:
- **URL**: `https://api.atlasacademy.io/export/NA/basic_servant.json`
- **Data**: Nama, Rarity (Bintang), Attribute, Class, dan Face Icon.

### 4. Fitur Tambahan
- **Filtering by Class**: Pengguna dapat memfilter daftar Servant berdasarkan Class (Saber, Archer, Lancer, Caster) menggunakan Material Chips.
- **Class Detail Section**: Menampilkan informasi deskripsi singkat mengenai class yang sedang dipilih dalam sebuah CardView yang interaktif.
- **Edge to Edge Display**: Mendukung tampilan layar penuh untuk pengalaman pengguna yang lebih imersif.

## Screenshot

### All List

<p align="center">
    <img width="250" alt="all_list" src="https://github.com/user-attachments/assets/e774c333-ebc2-4ab2-8f63-db5abac5a25f" />
</p>

### All Grid

<p align="center">
  <img width="250" alt="all_grid" src="https://github.com/user-attachments/assets/d68481ba-3dcf-4aa3-b449-55a7bf5646f1" />
</p>

### Class List

<p align="center">
  <img width="250" alt="class_list" src="https://github.com/user-attachments/assets/d2bb86be-cd86-4b22-abff-4457cdab3a75" />
</p>

### Class Grid

<p align="center">
  <img width="250" alt="class_grid" src="https://github.com/user-attachments/assets/7416e4a0-806a-4c81-9de0-6613bf8282dc" />
</p>

