del /s /q /f classes
del /s /q /f build_beans
mkdir build_beans\places
mkdir build_beans\devices

javac -cp lib\servlet-api.jar;lib\mysql-connector-java-3.0.17-ga-bin;lib\jboss-j2ee.jar -d build_beans\places src\edu\sumdu\group5\lab3\ejb\*.java src\edu\sumdu\group5\lab3\dao\DaoException.java src\edu\sumdu\group5\lab3\model\ModelException.java src\edu\sumdu\group5\lab3\dao\ConnectionException.java src\edu\sumdu\group5\lab3\dao\ConnectionFactory.java src\edu\sumdu\group5\lab3\model\PlaceCl.java
javac -cp lib\servlet-api.jar;lib\mysql-connector-java-3.0.17-ga-bin;lib\jboss-j2ee.jar -d build_beans\devices src\edu\sumdu\group5\lab3\ejb\*.java src\edu\sumdu\group5\lab3\dao\DaoException.java src\edu\sumdu\group5\lab3\model\ModelException.java src\edu\sumdu\group5\lab3\dao\ConnectionException.java src\edu\sumdu\group5\lab3\dao\ConnectionFactory.java src\edu\sumdu\group5\lab3\model\Device.java src\edu\sumdu\group5\lab3\model\PlaceCl.java src\edu\sumdu\group5\lab3\model\DeviceUser.java 
@pause
javac -cp build_beans\places;build_beans\devices;lib\mysql-connector-java-3.0.17-ga-bin;lib\servlet-api.jar;lib\jboss-j2ee.jar -d classes src\edu\sumdu\group5\lab3\model\*.java src\edu\sumdu\group5\lab3\dao\*.java src\edu\sumdu\group5\lab3\coreservlets\*.java src\edu\sumdu\group5\lab3\actions\*.java  
@pause