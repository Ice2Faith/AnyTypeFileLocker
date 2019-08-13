@echo off
color f1
title AnyFileLock by Ice2Faith
echo ------------------------------
echo   Welcome AnyFileLock Process
echo ------------------------------
echo ...
set check=%1
if "%check%"=="" (goto nullpoint) else (goto lockpoint)

:lockpoint
color fa
echo System : Load File : %1
set locmode=
set newname=
set password=
echo System : Please input mode(lock/unlock)
set /p locmode=
echo System : Please input new name
set /p newname=
echo System : Please input password
set /p password=
echo System : Source : %1 Mode : %locmode% New Name : %newname% Password : %password%
echo System : Please Wating...
java -jar AnyFileLocker.jar %locmode% %1 %newname% %password%
goto exitpoint

:nullpoint
echo System : Not load file
echo System : Please pull file to this bat icon to boot
goto exitpoint

:exitpoint
color f4
echo System : Press Any Key Exit
pause
exit