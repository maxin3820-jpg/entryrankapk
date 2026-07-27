# EntryRank Android APK - Quick Start Guide

## 🚀 Build Your APK in 5 Minutes!

### Step 1: Configure API (2 minutes)

#### File 1: Update API Base URL
Open: `app\build.gradle.kts`

Find line 58 and replace:
```kotlin
buildConfigField("String", "API_BASE_URL", "\"https://YOUR-PROJECT.supabase.co/\"")
```

With your actual Supabase URL:
```kotlin
buildConfigField("String", "API_BASE_URL", "\"https://xkssqtbqdnyammfqgcpn.supabase.co/\"")
```

#### File 2: Update API Key
Open: `app\src\main\java\com\entryrank\data\repository\AuthRepository.kt`

Find line 26 and replace:
```kotlin
private const val API_KEY = "YOUR_SUPABASE_ANON_KEY"
```

With your actual Supabase anon key (get from Supabase dashboard):
```kotlin
private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Step 2: Build APK (3 minutes)

Open terminal/command prompt in project folder:

```bash
cd "c:\Users\zc\Desktop\Alll things\entryrank-apk"
gradlew.bat assembleDebug
```

Wait for build to complete... ☕

### Step 3: Find Your APK

Your APK is ready at:
```
app\build\outputs\apk\dev\debug\app-dev-debug.apk
```

### Step 4: Install & Test

**On Phone:**
1. Transfer APK to phone
2. Enable "Install from Unknown Sources"
3. Tap APK to install
4. Open EntryRank app

**On Emulator:**
```bash
gradlew.bat installDebug
```

---

## ✅ What Works Now

After installation, you can:

1. **Sign Up** - Create new account
2. **Login** - Access with email/password
3. **Dashboard** - View subjects
4. **Navigation** - Browse between screens
5. **Logout** - Sign out

---

## 🎯 Features Included

### ✅ Fully Working:
- User authentication (signup/login)
- Dashboard with subjects
- Offline data storage
- Material 3 design
- Dark/Light theme
- Navigation system

### ⏳ Backend Ready (UI Placeholder):
- Practice questions
- Books and chapters
- Profile management
- Practice history
- Progress tracking

---

## 🔧 Troubleshooting

### Build Fails?

**Error: "Cannot resolve symbol"**
```bash
gradlew.bat clean build
```

**Error: "BuildConfig not found"**
- Already fixed! Just rebuild.

**Error: Compilation failed**
- Check Java JDK 17+ installed
- Check Android SDK 34 installed
- Run: `gradlew.bat --refresh-dependencies`

### App Crashes?

1. Check API credentials are correct
2. Check internet connection
3. View logcat for error messages:
```bash
adb logcat | findstr "EntryRank"
```

### Can't Login?

1. Verify API_BASE_URL is correct (with trailing `/`)
2. Verify API_KEY is correct
3. Check Supabase project is active
4. Try signup first to create account

---

## 📱 Test Checklist

After installing, test these features:

- [ ] App opens without crash
- [ ] Can see login screen
- [ ] Can navigate to signup screen
- [ ] Can create new account (signup)
- [ ] Can login with created account
- [ ] Can see dashboard
- [ ] Can see subjects list
- [ ] Can open menu (top right)
- [ ] Can logout

---

## 🎨 Customization Tips

### Change App Colors

Edit: `app\src\main\java\com\entryrank\presentation\theme\Color.kt`

```kotlin
// Change primary color (currently green #16A34A)
val Primary = Color(0xFFYOURCOLOR)
```

### Change App Name

Edit: `app\src\main\res\values\strings.xml`

```xml
<string name="app_name">Your App Name</string>
```

### Change Package Name

1. Refactor package in Android Studio
2. Update `applicationId` in `app\build.gradle.kts`

---

## 📦 Build Variants

### Debug Build (for testing)
```bash
gradlew.bat assembleDebug
```
- Faster build
- Includes debug info
- App ID: `com.entryrank.app.dev.debug`

### Release Build (for production)
```bash
gradlew.bat assembleRelease
```
- Optimized with ProGuard
- Smaller size
- Requires signing key
- App ID: `com.entryrank.app`

---

## 🚀 Next Steps

### Option 1: Use As-Is
- You have a working app with auth and dashboard
- Perfect for demo or testing backend

### Option 2: Complete UI
- Add practice screen UI
- Add books screen UI
- Add profile screen UI
- Estimated time: 1-2 days

### Option 3: Customize
- Change colors and branding
- Modify dashboard layout
- Add custom features

---

## 📊 Quick Reference

| Task | Command |
|------|---------|
| Build Debug APK | `gradlew.bat assembleDebug` |
| Build Release APK | `gradlew.bat assembleRelease` |
| Install on Device | `gradlew.bat installDebug` |
| Clean Build | `gradlew.bat clean` |
| Run Tests | `gradlew.bat test` |
| List Tasks | `gradlew.bat tasks` |

---

## 💡 Pro Tips

1. **First Time Building?**
   - First build takes 3-5 minutes (downloads dependencies)
   - Subsequent builds are faster (30 seconds - 1 minute)

2. **Testing on Real Device?**
   - Enable USB Debugging in Developer Options
   - Much faster than emulator

3. **Want Smaller APK?**
   - Use release build with ProGuard
   - Can reduce size by 40-60%

4. **Developing Features?**
   - Use debug build for faster iteration
   - Hot reload with Android Studio

---

## 🆘 Need Help?

### Documentation:
- `IMPLEMENTATION_COMPLETE.md` - Full implementation details
- `APK_BUILD_GUIDE.md` - Detailed build instructions  
- `PROJECT_STATUS.md` - Project roadmap
- `README.md` - Project overview

### Common Issues:
1. API credentials - Double check spelling and trailing slashes
2. Build failures - Run clean build
3. App crashes - Check logcat output
4. Can't find APK - Check `app\build\outputs\apk\` folder

---

## ✨ Success!

If you've followed this guide, you now have:
- ✅ A working Android APK
- ✅ User authentication
- ✅ Dashboard interface
- ✅ Professional architecture
- ✅ Ready for further development

**Congratulations! 🎉**

Share your APK or continue building amazing features!

---

**Last Updated**: $(Get-Date)
**Status**: Ready to Build ✅
