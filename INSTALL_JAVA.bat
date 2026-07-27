@echo off
REM ========================================
REM Java 17 Installer for EntryRank APK
REM ========================================

echo.
echo ========================================
echo   Java 17 Installation Helper
echo ========================================
echo.

REM Check if Java is already installed
java -version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Java is already installed!
    echo.
    java -version
    echo.
    echo You can now build your APK by running: BUILD_APK.bat
    echo.
    pause
    exit /b 0
)

echo Java is not installed yet.
echo.
echo ========================================
echo   Installation Options:
echo ========================================
echo.
echo Option 1: Using winget (Windows 10/11)
echo Command: winget install Microsoft.OpenJDK.17
echo.
echo Option 2: Manual Download
echo URL: https://adoptium.net/temurin/releases/?version=17
echo.
echo ========================================
echo.

REM Try winget installation
set /p choice="Do you want to install using winget? (Y/N): "
if /i "%choice%"=="Y" (
    echo.
    echo Installing Java 17 using winget...
    echo.
    winget install Microsoft.OpenJDK.17
    
    if %errorlevel% equ 0 (
        echo.
        echo ========================================
        echo   [SUCCESS] Java 17 Installed!
        echo ========================================
        echo.
        echo Please RESTART your command prompt, then run:
        echo BUILD_APK.bat
        echo.
    ) else (
        echo.
        echo [ERROR] Winget installation failed.
        echo.
        echo Please install manually:
        echo 1. Go to: https://adoptium.net/temurin/releases/?version=17
        echo 2. Download Windows x64 JDK .msi installer
        echo 3. Run the installer
        echo 4. Restart command prompt
        echo 5. Run BUILD_APK.bat
        echo.
    )
) else (
    echo.
    echo Please install Java 17 manually:
    echo.
    echo 1. Go to: https://adoptium.net/temurin/releases/?version=17
    echo 2. Select:
    echo    - Operating System: Windows
    echo    - Architecture: x64
    echo    - Package Type: JDK
    echo    - Version: 17 (LTS)
    echo 3. Download .msi installer
    echo 4. Run installer and follow prompts
    echo 5. Restart your command prompt
    echo 6. Run BUILD_APK.bat
    echo.
    echo Opening download page in browser...
    start https://adoptium.net/temurin/releases/?version=17
    echo.
)

pause
