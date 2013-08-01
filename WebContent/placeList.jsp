<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="edu.sumdu.group5.lab4.model.*" %>
<html>
<head>
    <title>Location List</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!--Author Sergey 
    This file show list of places-->
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
<h2 align="center">Location List</h2>
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
	for (Place currPlace : navigatePlaceList) {
        Place navigatePlace = currPlace;
%>
&gt;
<a href="index.perform?id=<%=navigatePlace.getId()%>"><%=navigatePlace.getName()%>
</a>

<%
	}
    }
%>
</span>
</br>
</br>
<fieldset>
    <%
    	byte isRouter = 0;
            if (session.getAttribute("check") == null) {
    %>
    <legend><b>Location</b></legend>
    <table style="border-button: 4px solid yellow;">
	<%-- list of locations --%>
        <%
        	TreeSet<Place> listPlace = (TreeSet<Place>) session.getAttribute("places");
                    for (Place currPlace : listPlace) {
        		Place place = currPlace;
        %>
        <tr>
            <td width="10"><img src="location.ico" alt="Location icon"></td>
            <td width="300"><a href="index.perform?id=<%= place.getId()%>"><%= place.getName()%>
            </a></td>
        </tr>
        <%
            }
        } else {
        %>
        <legend><b>Devices</b></legend>
        <table>
		<%-- list of routers --%>
            <%
                List<Device> listDev = (List<Device>) session.getAttribute("devices");

                if (listDev.size() != 0) {
                    isRouter = 1;
                    for (Device currDev : listDev) {
						Device dev = currDev;
            %>
            <tr>
                <td width="10"><img src="router.png" alt="Location icon"></td>

                <td width="300"><a href="device.perform?id=<%= dev.getId()%>"><%= dev.getDevName()%>
                </a></td>
            </tr>
            <%
                }
            } else {
                isRouter = 1;
            %>
            <tr>There is no devices in this location!</tr>
            <%
                    }
                }
            %>

        </table>

    </table>
</fieldset>
<br/>
<%
    if (isRouter == 1) {
%>

<form method="post" action="addDeviceAction.perform">
    <table>
        <tr style="display: none;">
            <td><input type="text" name="type" value="router"></td>
        </tr>
        <tr>

            <td>Router name:
            <td>
            <td><input type="text" size="30" name="devicename" value="router">&nbsp;<input type="submit"
                                                                                           name="addContact"
                                                                                           value="  Add  "></td>
        </tr>

    </table>
    <br/>

</form>

<%
    }
%>


</body>
</html>