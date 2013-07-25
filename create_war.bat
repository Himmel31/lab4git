del /s /q /f build
del /s /q /f war
xcopy /s /y WebContent build
mkdir build\WEB-INF\classes
mkdir build\WEB-INF\lib
xcopy lib\*.* build\WEB-INF\lib\ /y
xcopy /s /y classes build\WEB-INF\classes 
del build\WEB-INF\lib\servlet-api.jar /Q
del build\WEB-INF\lib\jboss-j2ee.jar /Q

jar cvfM build/lab4.war -C build\ .
rem jar -cmvf build\META-INF\MANIFEST.MF war\laba4.war -C build\ .
@pause
