del build_beans\places\EJB.jar /Q
del build_beans\devices\EJB_Devices.jar /Q

jar cf jar/EJB.jar -C build_beans\places . -C res\places .
jar cf jar/EJB_Devices.jar -C build_beans\devices . -C res\devices .

@pause
