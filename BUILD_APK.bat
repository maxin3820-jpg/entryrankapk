@echo off
REM ========================================
REM EntryRank APK Builder
REM Quick script to build APK locally
REM ========================================

echo.
echo ========================================
echo   EntryRank APK Builder
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed!
    echo.
    echo Please install Java 17 first:
    echo https://adoptium.net/temurin/releases/?version=17
    echo.
    echo Or run: winget install Microsoft.OpenJDK.17
    echo.
    pause
    exit /b 1
)

echo [OK] Java is installed
echo.
echo ========================================
echo   Starting APK Build...
echo ========================================
echo.
echo This will take 3-5 minutes (first time)
echo or 1-2 minutes (subsequent builds)
echo.
echo Please wait...
echo.

REM Build the APK
call gradlew.bat assembleDevDebug

if %errorlevel% neq 0 (
    echo.
    echo ========================================
    echo   [ERROR] Build Failed!
    echo ========================================
    echo.
    echo Try running: gradlew.bat clean assembleDevDebug
    echo Or check the error messages above
    echo.
    pause
    exit /b 1
)

echo.
echo ========================================
echo   [SUCCESS] APK Built Successfully!
echo ========================================
echo.
echo Your APK is ready at:
echo app\build\outputs\apk\dev\debug\app-dev-debug.apk
echo.
echo Size: ~20-30 MB
echo.
echo Opening APK folder...
echo.

REM Open the APK folder
explorer app\build\outputs\apk\dev\debug

echo.
echo ========================================
echo   Next Steps:
echo ========================================
echo.
echo 1. Copy app-dev-debug.apk to your phone
echo 2. Install on Android device
echo 3. Launch and tap "Skip Login - Try as Guest"
echo 4. Test your app!
echo.
echo To rebuild after changes, just run this script again!
echo.
pause
