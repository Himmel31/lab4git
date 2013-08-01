<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="edu.sumdu.group5.lab4.model.*" %>
<%@ page import="edu.sumdu.group5.lab4.actions.UpdateDevicesAction" %>
<html>
<head>

    <title>Device List</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!--Author Sergey & Roman & Artem 
    This file show list of devices and give possibility to delete, add and change devices-->
    <link rel="stylesheet"
          href="./css/styles.css"
          type="text/css"/>

    <style type="text/css">
        body, table, hr {
            color: black;
            background: silver;
            font-family: Verdana, sans-serif;
            font-size: small;
        }
    </style>
</head>

<body>
<h2 align="center">Device List</h2>
<br/>
<br/>

<span id="bread_crumb">
<%
	LinkedList<Place> navigatePlaceList = (LinkedList<Place>) session.getAttribute("navigatePlace");
    if (navigatePlaceList == null) {
%>
<a href="index.perform">Ukraine</a>

<%
	} else {
%>
<a href="index.perform"> Ukraine</a>
<%-- creating path string--%>
<%
	int flag = 0; //add device to path string
	for (Place currPlace : navigatePlaceList){
		Place navigatePlace = currPlace;
		flag++;
%>
&gt;
<a href="index.perform?id=<%= navigatePlace.getId()%>"><%= navigatePlace.getName()%>
</a>

<%
    if (navigatePlaceList.size() <= flag) {
%>
              &gt;
			  <a><%= session.getAttribute("currentRootDeviceName").toString() %>
              </a>
<%
            }
        }
    }
%>
</span>
</br>
</br>
<fieldset>
    <legend><b>Devices</b></legend>

    <%
        List<Device> listDeviceSlot = (List<Device>) session.getAttribute("devicesSlot");
        if (!listDeviceSlot.isEmpty()) {
    %>

    <table style="border-button: 4px solid yellow;">
        <th align="left">&nbsp;</th>
        <th align="left">&nbsp;</th>
        <th align="left">Slots</th>
        <th align="left">&nbsp;</th>
		<%-- creating list of slots and cards if necessary--%>
        <%
            for (Device currDev : listDeviceSlot) {
				Device slot = currDev;
                boolean haveCard = false; %>

        <tr>
            <td width="20" align='center'><a href="removeDeviceAction.perform?id=<%= slot.getId()%>">x</a></td>
            <td width="20"><img src="slot.gif" alt="Location icon"></td>

            <td width="95">
                <% if (UpdateDevicesAction.isUpdatable() && UpdateDevicesAction.getUpdateID() == slot.getId()) {%>
                <form method="post" action="updateDeviceAction.perform?id=<%= slot.getId()%>" style="margin:0 auto;">
                    <input type="text" size="30" name="devicename" value="<%=slot.getDevName() %>">&nbsp;<input
                        type="submit" name="update" value="  Update  ">
                </form>
                <%} else { %>
                <%=slot.getDevName() %>
                <a href="updateDeviceAction.perform?id=<%= slot.getId()%>">...</a></td>

            <td valign='middle'>
                <form method="post" action="addDeviceAction.perform" style="margin:0 auto;">
                    <input style="display: none;" type="radio" size="30" name="type" value="card" checked>&nbsp;
                    <input style="display: none;" type="radio" size="30" name="parentid" value="<%= slot.getId()%>"
                           checked>&nbsp;
                    <input type="text" size="15" name="devicename" value="New card">&nbsp;
                    <input type="submit" name="addContact" value="Add">
                </form>
            </td>
            <% }%>

            </td>
        </tr>
        <%
            List<Device> listDeviceCard = (List<Device>) session.getAttribute("devicesChildCards");
            for (Device currCard : listDeviceCard) {
				Device card = currCard;			
                if (card.getParentID() == slot.getId()) {
        %>
        <tr>
            <td width="20" align='center'></td>
            <td width="20"></td>
            <td width="95"></td>
            <td width="200" align='center'>
                <a href="removeDeviceAction.perform?id=<%= card.getId()%>"><font color="#777">x</font></a>&nbsp;
                <% if (UpdateDevicesAction.isUpdatable() && UpdateDevicesAction.getUpdateID() == card.getId()) {%>
                <form method="post" action="updateDeviceAction.perform?id=<%= card.getId()%>" style="margin:0 auto;">
                    <input type="text" size="30" name="devicename" value="<%=card.getDevName() %>">&nbsp;<input
                        type="submit" name="update" value="  Update  ">
                </form>
                <%} else { %>
                <%=card.getDevName() %>
                <a href="updateDeviceAction.perform?id=<%= card.getId()%>">...</a></td>
            <% }%>

            </td>


        </tr>

        <%
                        }
                    }
                }
            }


        %>


    </table>

	<%-- list of ports --%>
    <%
        List<Device> listDevicePort = (List<Device>) session.getAttribute("devicesPort");
        if (!listDevicePort.isEmpty()) {
    %>
    <table style="border-button: 4px solid yellow;">
        <th align="left">&nbsp;</th>
        <th align="left">&nbsp;</th>
        <th align="left">Ports</th>
        <th align="left">&nbsp;</th>
        <%
            for (Device currPort : listDevicePort) {
				Device port = currPort;
        %>


        <tr>
            <td width="20"><a href="removeDeviceAction.perform?id=<%= port.getId()%>">x</a></td>
            <td width="10"><img src="port.ico" alt="Location icon"></td>
            <td width="150"><% if (UpdateDevicesAction.isUpdatable() && UpdateDevicesAction.getUpdateID() == port.getId()) {%>
                <form method="post" action="updateDeviceAction.perform?id=<%= port.getId()%>" style="margin:0 auto;">
                    <input type="text" size="30" name="devicename" value="<%=port.getDevName() %>">&nbsp;<input
                        type="submit" name="update" value="  Update  ">
                </form>
                <%} else { %>
                <%=port.getDevName() %>
                <a href="updateDeviceAction.perform?id=<%= port.getId()%>">...</a></td>
            <% }%>


        </tr>
        <%
            }
        %>
    </table>
    <%
        }
        if (listDevicePort.isEmpty() && listDeviceSlot.isEmpty()) {
    %>
    <tr>There is no devices in this location!</tr>
    <%
        }
    %>
</fieldset>
<br>
<!-- Add Slot/Port-->
<form method="post" action="addDeviceAction.perform" style="margin:0 auto;">
    <table>
        <tr>
            <td>Type:
            <td>
            <td><input type="radio" size="30" name="type" value="slot"> Slot <input type="radio" size="30" name="type"
                                                                                    value="port" checked> Port
            </td>
        </tr>
        <tr>
            <td>Device name:
            <td>
            <td><input type="text" size="30" name="devicename">&nbsp;<input type="submit" name="addContact"
                                                                            value="  Add  "></td>
        </tr>
    </table>
</form>
</body>

</html>