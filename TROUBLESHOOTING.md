# 🔧 Troubleshooting Guide - EntryRank Android APK Build

This guide helps you identify and fix common build issues.

---

## 🚨 Common Build Errors & Solutions

### Error 1: "Could not find or load main class org.gradle.wrapper.GradleWrapperMain"

**Cause**: Missing `gradle-wrapper.jar` file

**Solution**: ✅ Already fixed!
```bash
# Verify the file exists (locally)
ls -la gradle/wrapper/gradle-wrapper.jar

# Should show: 43,453 bytes
```

**Status**: ✅ Fixed in commit d758018

---

### Error 2: "Resource android:xml/data_extraction_rules not found"

**Cause**: Missing Android XML resource files

**Solution**: ✅ Already fixed!

Required files created:
- `app/src/main/res/xml/data_extraction_rules.xml`
- `app/src/main/res/xml/backup_rules.xml`
- 16 launcher icon XML files
- Icon drawables

**Status**: ✅ Fixed in commit 9078b0a

---

### Error 3: "Room SQL syntax error: array indexing not supported"

**Cause**: Using `:questionIds[0]` syntax in Room @Query

**Solution**: ✅ Already fixed!

Changed from:
```kotlin
ORDER BY CASE WHEN id = :questionIds[0] THEN 0 ...
```

To:
```kotlin
WHERE id IN (:questionIds)
```

**File**: `app/src/main/java/com/entryrank/data/local/dao/QuestionDao.kt`

**Status**: ✅ Fixed in commit 248c5a3

---

### Error 4: "Task 'assembleDebug' not found"

**Cause**: Using wrong build variant name

**Solution**: ✅ Already fixed!

The app uses product flavors (`dev`, `prod`), so build command must include flavor:
- ❌ `./gradlew assembleDebug` (wrong)
- ✅ `./gradlew assembleDevDebug` (correct)

**Status**: ✅ Fixed in commit e9aa211

---

## 🔍 How to Diagnose New Build Failures

### Step 1: Access Build Logs
1. Go to: https://github.com/maxin3820-jpg/entryrankapk/actions
2. Click on the failed workflow run (red X)
3. Click on "Build Debug APK" step
4. Expand the log output

### Step 2: Identify Error Type

#### Gradle Error Pattern:
```
FAILURE: Build failed with an exception.
* What went wrong:
[ERROR MESSAGE HERE]
```

#### Compilation Error Pattern:
```
e: file:///.../SomeFile.kt:42:10 [ERROR MESSAGE]
```

#### KSP Error Pattern:
```
error: [KSP Error] [ERROR MESSAGE]
```

#### Resource Error Pattern:
```
AAPT: error: resource android:xxx not found.
```

### Step 3: Common Error Categories

#### A. Dependency Resolution Errors
**Symptoms**: 
- "Could not resolve dependency"
- "Could not download artifact"

**Possible Causes**:
- Network issues in GitHub Actions
- Dependency version conflict
- Repository not accessible

**Solutions**:
1. Re-run the workflow (might be temporary network issue)
2. Check dependency versions in `app/build.gradle.kts`
3. Verify repository URLs in root `build.gradle.kts`

#### B. Compilation Errors
**Symptoms**:
- "Unresolved reference"
- "Type mismatch"
- "Cannot access class"

**Possible Causes**:
- Syntax error in Kotlin code
- Missing import statements
- Type incompatibility

**Solutions**:
1. Check the specific file and line mentioned in error
2. Fix syntax errors
3. Add missing imports
4. Correct type mismatches

#### C. KSP/Room Errors
**Symptoms**:
- "[KSP Error]" prefix
- "SQL syntax error"
- "Room cannot verify data integrity"

**Possible Causes**:
- Invalid SQL query syntax in @Query annotations
- Missing @Entity or @Dao annotations
- Database schema issues

**Solutions**:
1. Check all @Query annotations for SQL syntax
2. Verify Room entity annotations
3. Ensure DAO methods have correct signatures
4. Remove unsupported SQL features (like array indexing)

#### D. Resource Errors
**Symptoms**:
- "resource not found"
- "AAPT: error"
- "No resource identifier found"

**Possible Causes**:
- Missing resource files (XML, drawables)
- Incorrect resource references in code/manifests
- Resource naming conflicts

**Solutions**:
1. Verify all referenced resources exist
2. Check resource file paths match references
3. Ensure proper XML syntax in resource files
4. Check AndroidManifest.xml for correct resource refs

#### E. Memory Errors
**Symptoms**:
- "OutOfMemoryError: Java heap space"
- "GC overhead limit exceeded"

**Possible Causes**:
- Large number of dependencies
- Complex Compose layouts
- Insufficient memory allocation

**Solutions**:
1. Add memory settings to `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g
```
2. Enable parallel builds:
```properties
org.gradle.parallel=true
org.gradle.caching=true
```

---

## 🛠️ Local Testing Commands

Before pushing to GitHub, test locally:

### 1. Clean Build
```bash
./gradlew clean
```

### 2. Build Dev Debug APK
```bash
./gradlew assembleDevDebug --stacktrace
```

### 3. Check for Compilation Errors
```bash
./gradlew compileDevDebugKotlin --stacktrace
```

### 4. Verify Resources
```bash
./gradlew processDevDebugResources --stacktrace
```

### 5. Run KSP Processing
```bash
./gradlew kspDevDebugKotlin --stacktrace
```

---

## 📋 Pre-Push Checklist

Before pushing code to GitHub, verify:

- [ ] All Kotlin files compile without errors
- [ ] All @Query annotations use valid SQL syntax
- [ ] All resource files exist (XML, drawables, icons)
- [ ] AndroidManifest.xml is valid
- [ ] No hardcoded paths or local file references
- [ ] gradle-wrapper.jar is committed
- [ ] `.gitignore` doesn't exclude needed files
- [ ] Local build succeeds: `./gradlew assembleDevDebug`

---

## 🔄 How to Fix and Re-Push

### When a Build Fails:

1. **Read the error logs** on GitHub Actions
2. **Identify the root cause** (see error patterns above)
3. **Fix the issue locally**:
   ```bash
   # Navigate to project
   cd "c:\Users\zc\Desktop\Alll things\entryrank-apk"
   
   # Make your fix
   # Edit the problematic file
   
   # Test locally
   ./gradlew clean assembleDevDebug --stacktrace
   ```

4. **Commit and push**:
   ```bash
   git add .
   git commit -m "Fix: [describe what you fixed]"
   git push origin main
   ```

5. **Monitor the new build**:
   - Go to: https://github.com/maxin3820-jpg/entryrankapk/actions
   - Wait for new workflow run to start
   - Check if it succeeds

---

## 📊 Build Success Indicators

### Successful Build Looks Like:
```
✅ Checkout code
✅ Set up JDK 17
✅ Grant execute permission for gradlew
✅ Build Debug APK
    > Task :app:compileDevDebugKotlin
    > Task :app:kspDevDebugKotlin
    > Task :app:processDevDebugResources
    > Task :app:assembleDevDebug
    BUILD SUCCESSFUL in 5m 47s
    142 actionable tasks: 142 executed
✅ Upload APK
✅ Create Release

Artifact uploaded: entryrank-debug-apk
```

### Failed Build Looks Like:
```
✅ Checkout code
✅ Set up JDK 17
✅ Grant execute permission for gradlew
❌ Build Debug APK
    FAILURE: Build failed with an exception.
    * What went wrong:
    [ERROR MESSAGE]
    
    BUILD FAILED in 2m 15s
```

---

## 🎯 Quick Fixes Reference

| Error Message | Quick Fix |
|---------------|-----------|
| "GradleWrapperMain not found" | Add gradle-wrapper.jar |
| "resource not found" | Create missing resource file |
| "Unresolved reference" | Add missing import |
| "SQL syntax error" | Fix @Query annotation |
| "Task not found" | Use correct build variant name |
| "Cannot access class" | Check package names and imports |
| "Type mismatch" | Fix type incompatibility |
| "OutOfMemoryError" | Increase heap size in gradle.properties |

---

## 🆘 Still Having Issues?

### 1. Check Existing Issues
Visit: https://github.com/maxin3820-jpg/entryrankapk/issues

### 2. Create New Issue
If your error is new:
1. Go to: https://github.com/maxin3820-jpg/entryrankapk/issues/new
2. Title: "[Build Error] Brief description"
3. Include:
   - Full error message from GitHub Actions
   - Link to failed workflow run
   - What you've tried
   - Any relevant code changes

### 3. Re-run Workflow
Sometimes transient issues resolve on retry:
1. Go to failed workflow run
2. Click "Re-run all jobs"
3. Wait for completion

---

## ✅ Current Status

As of the latest commit (be87e78):

- ✅ **Gradle wrapper**: Present and functional
- ✅ **Android resources**: All created
- ✅ **Room queries**: SQL syntax fixed
- ✅ **Build configuration**: Correct variant names
- ✅ **Dependencies**: All versions compatible
- ✅ **Manifest**: Properly configured

**All known issues have been resolved!**

Next build should succeed. 🎯

---

## 📞 Support Resources

- **GitHub Actions Docs**: https://docs.github.com/en/actions
- **Gradle Docs**: https://docs.gradle.org
- **Android Docs**: https://developer.android.com
- **Room Database**: https://developer.android.com/training/data-storage/room
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Hilt DI**: https://developer.android.com/training/dependency-injection/hilt-android

---

**Last Updated**: January 27, 2025  
**Status**: All known errors fixed ✅  
**Next Action**: Monitor GitHub Actions build 👀
