@echo off
title Android Emulator Auto Launcher

echo ==== Killing old ADB instances ====
taskkill /F /IM adb.exe >nul 2>&1

echo ==== Restarting ADB service ====
cd /d C:\Users\anura\AppData\Local\Android\Sdk\platform-tools
adb kill-server >nul 2>&1
adb start-server

echo ==== Starting Android Emulator: Medium_Phone_API_36.1 ====
cd /d C:\Users\anura\AppData\Local\Android\Sdk\emulator
start emulator.exe -avd Medium_Phone_API_36.1 -gpu auto -no-snapshot -no-boot-anim

echo Waiting for emulator to boot...
:wait_loop
timeout /t 3 >nul
adb devices | findstr "device" >nul
if %errorlevel% neq 0 goto wait_loop

echo ==== Emulator Connected! ====
adb devices

pause
