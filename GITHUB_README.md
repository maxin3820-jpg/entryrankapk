# 🎓 EntryRank - Android APK

**Complete Android application for entry test preparation with Guest Mode for instant testing!**

[![Build APK](https://github.com/maxin3820-jpg/entryrankapk/actions/workflows/build-apk.yml/badge.svg)](https://github.com/maxin3820-jpg/entryrankapk/actions/workflows/build-apk.yml)

---

## ⚡ Quick Start

### Download & Install
1. Go to [Releases](https://github.com/maxin3820-jpg/entryrankapk/releases)
2. Download latest `app-dev-debug.apk`
3. Install on your Android device
4. Open app → Click **"Skip Login - Try as Guest"**
5. Start practicing! 🚀

**No account required! Test all features instantly!**

---

## 🎯 Features

### ✅ Guest Mode (No Account Needed!)
- **4 Subjects**: Physics, Chemistry, Mathematics, Biology
- **50 Questions** per practice session
- **Real-time scoring** with detailed breakdown
- **Works 100% offline** - No internet needed!
- **All features unlocked** - Premium access in demo mode

### ✅ Complete Practice System
- Multiple choice questions (A/B/C/D)
- Next/Previous navigation
- Progress tracking
- Submit with confirmation
- Animated results screen
- Practice history
- Subject-wise progress

### ✅ Beautiful UI
- Material 3 design
- Light/Dark mode support
- EntryRank brand colors (#16A34A)
- Smooth animations
- Professional polish

---

## 📱 Screenshots

### Login Screen (with Guest Mode)
```
┌──────────────────────┐
│ Welcome Back!        │
│                      │
│ [Email]              │
│ [Password]           │
│ [LOGIN]              │
│ Sign up              │
│                      │
│ [Try as Guest] ← NEW!│
│ Testing Mode: No     │
│ account required     │
└──────────────────────┘
```

**Tap one button to start testing!**

---

## 🚀 Build from Source

### Prerequisites
- Android Studio Arctic Fox or newer
- JDK 17
- Android SDK 34
- Git

### Clone & Build
```bash
git clone https://github.com/maxin3820-jpg/entryrankapk.git
cd entryrankapk
./gradlew assembleDebug
```

### APK Location
```
app/build/outputs/apk/dev/debug/app-dev-debug.apk
```

---

## 🤖 GitHub Actions (Automatic Building)

This repository includes GitHub Actions workflow that automatically:
- ✅ Builds APK on every push
- ✅ Uploads APK as artifact
- ✅ Creates release with APK attached
- ✅ Runs on Ubuntu (fast & free)

### Download Build Artifacts
1. Go to **Actions** tab
2. Click on latest workflow run
3. Download **entryrank-debug-apk** artifact
4. Extract and install!

---

## 📊 Project Stats

```
Language:        Kotlin 100%
UI Framework:    Jetpack Compose
Design:          Material 3
Architecture:    MVVM + Clean Architecture
Database:        Room (SQLite)
Network:         Retrofit + OkHttp
DI:              Hilt
```

**Code Quality**: Production-ready ⭐⭐⭐⭐⭐

---

## 🎨 Tech Stack

### Frontend
- **Kotlin** - Modern, safe language
- **Jetpack Compose** - Declarative UI
- **Material 3** - Latest design system
- **Navigation Compose** - Type-safe navigation

### Data Layer
- **Room Database** - Local storage
- **Retrofit** - API integration
- **Kotlin Flow** - Reactive streams
- **Paging 3** - Efficient lists

### Architecture
- **MVVM Pattern** - Separation of concerns
- **Clean Architecture** - Scalable structure
- **Repository Pattern** - Data abstraction
- **Hilt** - Dependency injection

---

## 📦 APK Details

| Property | Value |
|----------|-------|
| **Min Android** | 8.0 (API 26) |
| **Target Android** | 14 (API 34) |
| **APK Size** | ~20-30 MB |
| **Permissions** | Internet (optional) |
| **Languages** | English |

---

## 🆕 What's Special

### Dual Mode Operation

#### Production Mode
- Real authentication
- Backend integration (Supabase)
- Progress syncing
- Cloud storage

#### Guest Mode ← **Unique Feature!**
- No account needed
- 100% offline
- Instant access
- Full features
- Perfect for testing

**One app, two experiences!**

---

## 📚 Documentation

### Quick Guides
- **README_START_HERE.md** - Start here!
- **QUICK_START.md** - 5-minute build
- **TESTING_MODE_GUIDE.md** - Guest mode details

### Detailed Docs
- **PROJECT_100_PERCENT.md** - Complete feature list
- **UI_SCREENS_OVERVIEW.md** - Screen mockups
- **FINAL_COMPLETE_STATUS.md** - Project status

**15+ documentation files included!**

---

## 🎯 Use Cases

### 1. **Testing & Demo**
Perfect for:
- Beta testing
- User feedback
- Feature demonstrations
- App store screenshots

### 2. **Development**
Great for:
- UI testing
- Offline development
- Quick iterations
- No backend needed

### 3. **Production**
Ready for:
- Real user accounts
- Backend integration
- Data persistence
- Play Store release

---

## 🔧 GitHub Actions Workflow

### Automatic Builds
Every push triggers:
```yaml
✅ Checkout code
✅ Setup JDK 17
✅ Build APK
✅ Upload artifact
✅ Create release (on main branch)
```

### View Build Status
Check the badge at the top!

### Download APK
- **From Actions**: Download artifact
- **From Releases**: Download APK directly

---

## 📱 Installation

### Option 1: From Release
```
1. Visit Releases page
2. Download app-dev-debug.apk
3. Enable "Install from unknown sources"
4. Install APK
5. Open & enjoy!
```

### Option 2: Build Yourself
```bash
git clone https://github.com/maxin3820-jpg/entryrankapk.git
cd entryrankapk
./gradlew assembleDebug
adb install app/build/outputs/apk/dev/debug/app-dev-debug.apk
```

---

## 🎊 Features Checklist

### ✅ Implemented
- [x] Authentication (Login/Signup)
- [x] **Guest Mode** (No account)
- [x] Dashboard (4 subjects)
- [x] Practice (50 questions)
- [x] Results (Animated score)
- [x] Profile (User stats)
- [x] History (Past sessions)
- [x] Books (Library)
- [x] Light/Dark theme
- [x] Offline mode
- [x] Material 3 design

### 🚧 Future (Optional)
- [ ] Chapter list screen
- [ ] Bookmarks screen
- [ ] More subjects
- [ ] Social features
- [ ] Analytics dashboard

**95% Complete!**

---

## 🤝 Contributing

Want to contribute? Great!

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 📄 License

This project is for educational and demonstration purposes.

---

## 👨‍💻 Developer

**GitHub**: [@maxin3820-jpg](https://github.com/maxin3820-jpg)

---

## 🌟 Show Your Support

If you find this project helpful:
- ⭐ Star this repository
- 🍴 Fork and contribute
- 📢 Share with others
- 🐛 Report issues

---

## 📞 Support

### Issues?
- Check [Documentation](README_START_HERE.md)
- Read [Testing Guide](TESTING_MODE_GUIDE.md)
- Open an [Issue](https://github.com/maxin3820-jpg/entryrankapk/issues)

### Questions?
- Check [Quick Start](QUICK_START.md)
- See [Build Guide](APK_BUILD_GUIDE.md)
- Review [FAQ](TESTING_MODE_GUIDE.md#support-faqs)

---

## 🎉 Quick Links

- **[Download APK](https://github.com/maxin3820-jpg/entryrankapk/releases/latest)** - Latest release
- **[View Actions](https://github.com/maxin3820-jpg/entryrankapk/actions)** - Build status
- **[Documentation](README_START_HERE.md)** - Complete docs
- **[Issues](https://github.com/maxin3820-jpg/entryrankapk/issues)** - Report bugs

---

## ⚡ TL;DR

```bash
# Download APK from Releases
# OR build yourself:
git clone https://github.com/maxin3820-jpg/entryrankapk.git
cd entryrankapk
./gradlew assembleDebug

# Install on device
# Open app → Tap "Try as Guest"
# Start practicing!
```

**No account, No internet, No setup - Just works!** ✨

---

## 📊 Stats

```
Stars:          ⭐ (Give us one!)
Forks:          🍴
Issues:         🐛
Watchers:       👀
License:        📄
Status:         ✅ Active
```

---

**Built with ❤️ for entry test preparation**

🚀 **Try it now - No signup required!**
