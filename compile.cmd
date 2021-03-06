del /s /q /f classes
del /s /q /f build_beans
mkdir build_beans\places
mkdir build_beans\devices

javac -cp lib\servlet-api.jar;lib\mysql-connector-java-3.0.17-ga-bin;lib\jboss-j2ee.jar;lib\log4j-1.2.17.jar -d build_beans\places src\edu\sumdu\group5\lab4\ejb\places\*.java src\edu\sumdu\group5\lab4\dao\DaoException.java src\edu\sumdu\group5\lab4\model\ModelException.java src\edu\sumdu\group5\lab4\dao\ConnectionException.java src\edu\sumdu\group5\lab4\dao\ConnectionFactory.java src\edu\sumdu\group5\lab4\model\Place.java src\edu\sumdu\group5\lab4\ejb\devices\*.java src\edu\sumdu\group5\lab4\dao\DaoException.java src\edu\sumdu\group5\lab4\model\ModelException.java src\edu\sumdu\group5\lab4\dao\ConnectionException.java src\edu\sumdu\group5\lab4\dao\ConnectionFactory.java src\edu\sumdu\group5\lab4\model\Device.java src\edu\sumdu\group5\lab4\model\Place.java src\edu\sumdu\group5\lab4\model\DeviceUser.java
rem javac -cp lib\servlet-api.jar;lib\mysql-connector-java-3.0.17-ga-bin;lib\jboss-j2ee.jar -d build_beans\devices src\edu\sumdu\group5\lab4\ejb\devices\*.java src\edu\sumdu\group5\lab4\dao\DaoException.java src\edu\sumdu\group5\lab4\model\ModelException.java src\edu\sumdu\group5\lab4\dao\ConnectionException.java src\edu\sumdu\group5\lab4\dao\ConnectionFactory.java src\edu\sumdu\group5\lab4\model\Device.java src\edu\sumdu\group5\lab4\model\Place.java src\edu\sumdu\group5\lab4\model\DeviceUser.java
@pause
javac -cp build_beans\places;lib\mysql-connector-java-3.0.17-ga-bin;lib\servlet-api.jar;lib\jboss-j2ee.jar;lib\log4j-1.2.17.jar -d classes src\edu\sumdu\group5\lab4\model\*.java src\edu\sumdu\group5\lab4\dao\*.java src\edu\sumdu\group5\lab4\coreservlets\*.java src\edu\sumdu\group5\lab4\actions\*.java
@pause