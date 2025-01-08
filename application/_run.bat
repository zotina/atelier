@echo off
for /F "tokens=*" %%A in (config.conf) do set "%%A"

:: set paths
set "sourceFolder=%root%\src\java"
set "destinationFolder=%root%\bin"
set "lib=%root%\lib"
set "src=%root%"
set "lib_jar=%root%\web\WEB-INF\lib"

:: copy all java file to a temporary folder
for /r "%sourceFolder%" %%f in (*.java) do (
    xcopy "%%f" "%root%\temp"
)

:: go to temp and compile all java files
cd "%root%\temp"
javac -d "%destinationFolder%" -cp "%lib%\*" *.java


xcopy "%root%\bin" "%root%\web\WEB-INF\classes" /E /I /H /Y /K /R
xcopy "%lib%" "%lib_jar%" /E /I /H /Y /K /R


:: create war file 
cd "%src%\web"
jar -cvf "../%app_name%.war" .
cd ..

:: copy war file to webapps
xcopy "%app_name%.war"  "%serveur%"
rmdir /s /q "%root%\temp"

pause