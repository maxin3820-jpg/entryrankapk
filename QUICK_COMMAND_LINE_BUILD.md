# ⚡ Quick Command Line APK Build - 3-5 Minutes

Build your APK locally using just Java and command line - no Android Studio needed!

---

## 🚀 STEP 1: Install Java 17 (One Time - 10 Minutes)

### Option A: Using Winget (Windows 10/11)
```cmd
winget install Microsoft.OpenJDK.17
```

### Option B: Manual Download
1. **Go to**: https://adoptium.net/temurin/releases/?version=17
2. **Select**:
   - Operating System: Windows
   - Architecture: x64
   - Package Type: JDK
   - Version: 17 (LTS)
3. **Download**: `.msi` installer (~180 MB)
4. **Run installer** and follow prompts
5. **Restart** your command prompt/terminal

### Verify Installation:
```cmd
java -version
```

**Expected output:**
```
openjdk version "17.0.x" 2024-xx-xx
OpenJDK Runtime Environment Temurin-17.0.x (build 17.0.x+x)
OpenJDK 64-Bit Server VM Temurin-17.0.x (build 17.0.x+x, mixed mode, sharing)
```

✅ If you see this, Java is installed correctly!

---

## 🚀 STEP 2: Build Your APK (3-5 Minutes)

### Open Command Prompt:
1. Press `Win + R`
2. Type `cmd`
3. Press Enter

### Navigate to Project:
```cmd
cd "C:\Users\zc\Desktop\Alll things\entryrank-apk"
```

### Build Debug APK:
```cmd
gradlew.bat assembleDevDebug
```

### What Happens:
```
> Task :app:preBuild
> Task :app:preDevDebugBuild
> Task :app:compileDevDebugKotlin
> Task :app:kspDevDebugKotlin
> Task :app:processDevDebugResources
> Task :app:compileDevDebugJavaWithJavac
> Task :app:dexBuilderDevDebug
> Task :app:mergeDevDebugJavaResource
> Task :app:packageDevDebug
> Task :app:createDevDebugApkListingFileRedirect
> Task :app:assembleDevDebug

BUILD SUCCESSFUL in 3m 45s
142 actionable tasks: 142 executed
```

**Time**: 3-5 minutes (first build), 1-2 minutes after

---

## 🚀 STEP 3: Find Your APK

### Location:
```
C:\Users\zc\Desktop\Alll things\entryrank-apk\app\build\outputs\apk\dev\debug\app-dev-debug.apk
```

### Quick Command to Open Folder:
```cmd
explorer app\build\outputs\apk\dev\debug
```

### Check File:
```cmd
dir app\build\outputs\apk\dev\debug\app-dev-debug.apk
```

**Expected size**: ~20-30 MB

---

## 🚀 STEP 4: Install APK on Android Phone

### Method 1: USB Cable
1. Connect phone to computer via USB
2. Copy `app-dev-debug.apk` to phone's Download folder
3. On phone: Open file manager → Downloads
4. Tap `app-dev-debug.apk`
5. Enable "Install from unknown sources" if prompted
6. Tap "Install"

### Method 2: Cloud Storage
1. Upload APK to Google Drive/Dropbox
2. Download on phone
3. Install

---

## 🔄 REBUILDING AFTER CHANGES

### Make Your Changes:
- Edit any file in `src/main/java/com/entryrank/...`
- Modify colors, text, logic, etc.

### Rebuild APK:
```cmd
cd "C:\Users\zc\Desktop\Alll things\entryrank-apk"
gradlew.bat assembleDevDebug
```

**Time**: 1-2 minutes (much faster after first build!)

### Clean Build (if needed):
```cmd
gradlew.bat clean
gradlew.bat assembleDevDebug
```

---

## 📋 ALL AVAILABLE COMMANDS

### Build Commands:
```cmd
# Build debug APK (dev flavor)
gradlew.bat assembleDevDebug

# Build release APK (dev flavor) - minified
gradlew.bat assembleDevRelease

# Build production debug APK
gradlew.bat assembleProdDebug

# Build all variants
gradlew.bat assembleDebug

# Clean build folder
gradlew.bat clean

# Clean and build
gradlew.bat clean assembleDevDebug
```

### Info Commands:
```cmd
# List all tasks
gradlew.bat tasks

# Check dependencies
gradlew.bat dependencies

# Project info
gradlew.bat projects

# Build with more details
gradlew.bat assembleDevDebug --info

# Build with full logging
gradlew.bat assembleDevDebug --stacktrace --info
```

---

## ⚡ SPEED OPTIMIZATION TIPS

### 1. Enable Gradle Daemon
Edit `gradle.properties` (already configured):
```properties
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
```

### 2. Increase Memory
Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g
```

### 3. Use Build Cache
```cmd
# First build creates cache
gradlew.bat assembleDevDebug

# Subsequent builds use cache (faster!)
gradlew.bat assembleDevDebug
```

---

## 🐛 TROUBLESHOOTING

### Error: "java is not recognized"
**Solution**: Java not installed or not in PATH
```cmd
# Check Java installation
java -version

# If not working, reinstall Java 17
# OR manually add to PATH: C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot\bin
```

### Error: "JAVA_HOME is not set"
**Solution**: Set JAVA_HOME environment variable
```cmd
# Check current JAVA_HOME
echo %JAVA_HOME%

# Set temporarily
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot

# Or add permanently via System Properties → Environment Variables
```

### Error: "Gradle build failed"
**Solution**: Clean and rebuild
```cmd
gradlew.bat clean
gradlew.bat assembleDevDebug --stacktrace
```

### Error: "Out of memory"
**Solution**: Increase heap size
Edit `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx6g -XX:MaxMetaspaceSize=1g
```

### Build is Slow (10+ minutes)
**Solutions**:
1. Close other applications
2. Increase Gradle memory (see above)
3. Disable antivirus temporarily
4. Use SSD if available
5. Check internet connection (first build downloads dependencies)

---

## 📊 BUILD TIME BREAKDOWN

### First Build (~3-5 minutes):
```
Download dependencies:     1-2 minutes
Compile Kotlin code:       1-2 minutes
Process resources:         30 seconds
KSP (Room/Hilt):          30 seconds
Package APK:              30 seconds
Total:                    3-5 minutes
```

### Subsequent Builds (~1-2 minutes):
```
Compile changed files:     30 seconds
Incremental KSP:          20 seconds
Package APK:              30 seconds
Total:                    1-2 minutes
```

---

## 🎯 QUICK REFERENCE CARD

### Daily Workflow:
```cmd
# 1. Make changes to your code
# Edit files in: src/main/java/com/entryrank/...

# 2. Navigate to project
cd "C:\Users\zc\Desktop\Alll things\entryrank-apk"

# 3. Build APK
gradlew.bat assembleDevDebug

# 4. Get APK
explorer app\build\outputs\apk\dev\debug

# 5. Transfer to phone and install
```

### Time per cycle:
- Edit code: 1-10 minutes (depends on change)
- Build APK: 1-2 minutes
- Transfer to phone: 30 seconds
- Install and test: 1-2 minutes
- **Total**: ~5 minutes per iteration

---

## 🆚 COMPARISON

### GitHub Actions:
```
✅ No local setup
❌ 30+ minutes per build
❌ Can't iterate quickly
❌ Need internet
```

### Command Line Local:
```
✅ 1-2 minute builds
✅ Work offline
✅ Fast iteration
✅ Full control
✅ Minimal setup (just Java)
⚠️ Need to transfer to phone
```

### Android Studio:
```
✅ 1-2 minute builds
✅ Built-in emulator (30 sec testing)
✅ Code editing features
✅ Debugging tools
⚠️ Large download (~4 GB)
⚠️ More resource intensive
```

---

## 💡 PRO TIPS

### Tip 1: Create Build Script
Create `build.bat` in project root:
```batch
@echo off
echo Building EntryRank APK...
gradlew.bat clean assembleDevDebug
echo.
echo Build complete!
echo APK location: app\build\outputs\apk\dev\debug\app-dev-debug.apk
explorer app\build\outputs\apk\dev\debug
pause
```

Now just run: `build.bat`

### Tip 2: Watch for Changes
```cmd
# Install gradle-watch plugin (optional)
# Then run continuous build:
gradlew.bat assembleDevDebug --continuous
```

### Tip 3: Build Multiple Variants
```cmd
# Build both debug and release
gradlew.bat assembleDevDebug assembleDevRelease
```

### Tip 4: Parallel Builds
Already enabled in `gradle.properties`:
```properties
org.gradle.parallel=true
```

---

## 📝 CHECKLIST

### One-Time Setup:
- [ ] Download Java 17
- [ ] Install Java 17
- [ ] Verify: `java -version` works
- [ ] Test build: `gradlew.bat assembleDevDebug`
- [ ] APK created successfully

### Daily Development:
- [ ] Make code changes
- [ ] Run: `gradlew.bat assembleDevDebug`
- [ ] Wait 1-2 minutes
- [ ] Find APK in `app\build\outputs\apk\dev\debug\`
- [ ] Transfer to phone
- [ ] Install and test

---

## 🎊 SUCCESS!

Once you have Java 17 installed, you can:

✅ Build APK in **1-2 minutes** (after first build)  
✅ Work completely **offline**  
✅ Iterate **quickly** on changes  
✅ Have **full control** over build process  
✅ Build **anytime** without GitHub limits  

---

## 🔗 QUICK LINKS

- **Download Java 17**: https://adoptium.net/temurin/releases/?version=17
- **Gradle Docs**: https://docs.gradle.org
- **Android Build Guide**: https://developer.android.com/build
- **Your APK Location**: `app\build\outputs\apk\dev\debug\`

---

## 📞 HELP

If you encounter issues:
1. Check TROUBLESHOOTING.md in project
2. Run: `gradlew.bat assembleDevDebug --stacktrace --info`
3. Copy error message and search online
4. Or report issue on GitHub

---

**Ready to build? Install Java 17 and run `gradlew.bat assembleDevDebug`!** 🚀
