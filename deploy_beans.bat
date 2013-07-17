del %JBOSS_HOME%\server\default\deploy\EJB.jar /Q
del %JBOSS_HOME%\server\default\deploy\EJB_Devices.jar /Q
copy jar\EJB.jar %JBOSS_HOME%\server\default\deploy /y
copy jar\EJB_Devices.jar %JBOSS_HOME%\server\default\deploy /y
@pause
