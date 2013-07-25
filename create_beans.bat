del build_beans\places\EJB.jar /Q
rem del build_beans\devices\EJB_Devices.jar /Q

jar cf jar/EJB.jar -C build_beans\places . -C res\places .
rem jar cf jar/EJB_Devices.jar -C build_beans\devices . -C res\devices .

@pause
