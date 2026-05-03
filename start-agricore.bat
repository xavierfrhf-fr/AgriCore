@echo off
setlocal

set "PROJECT_ROOT=%~dp0"

REM === Nettoyage : nettoyer les ports avant de demarrer ===
echo Nettoyage des ports 8080 et 4200...
call :killport 8080
call :killport 4200

REM === Detection WAMP ===
set "WAMP_PATH="
if exist "C:\wamp64\wampmanager.exe" set "WAMP_PATH=C:\wamp64\wampmanager.exe"
if exist "C:\wamp\wampmanager.exe"   set "WAMP_PATH=C:\wamp\wampmanager.exe"

if "%WAMP_PATH%"=="" (
    echo [ERREUR] WAMP introuvable
    pause
    exit /b 1
)

echo === Demarrage AgriCore ===
echo Lancement de WAMP...
start "" "%WAMP_PATH%"
timeout /t 20 /nobreak >nul

echo Lancement back et front...
wt new-tab --title "AgriCore Back" -d "%PROJECT_ROOT%agricore_spring_boot" cmd /k mvnw.cmd spring-boot:run ; new-tab --title "AgriCore Front" -d "%PROJECT_ROOT%agricore-angular" cmd /k npm start

exit /b 0

:killport
for /f "tokens=5" %%a in ('netstat -ano ^| findstr ":%~1 " ^| findstr LISTENING') do (
    taskkill /F /PID %%a >nul 2>&1
)
exit /b 0