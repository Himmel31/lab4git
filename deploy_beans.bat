del %JBOSS_HOME%\server\default\deploy\EJB.jar /Q
rem del %JBOSS_HOME%\server\default\deploy\EJB_Devices.jar /Q
copy jar\EJB.jar %JBOSS_HOME%\server\default\deploy /y
copy WebContent\mssql-ds.xml %JBOSS_HOME%\server\default\deploy /y
rem copy jar\EJB_Devices.jar %JBOSS_HOME%\server\default\deploy /y
@pause
