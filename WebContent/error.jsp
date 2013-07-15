<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="edu.sumdu.group5.lab3.model.*" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <!--Author Sergey
    This file show an error page    -->
    <style type="text/css">
        body, table, hr {
            color: black;
            background: silver;
            font-family: Verdana, sans-serif;
            font-size: x-small;
        }
    </style>
</head>

<body>
<h2>Error :( </h2>
<hr size="2"/>
<fieldset>
    <legend><b>Error</b></legend>
    There was an error: <%= session.getAttribute("errorMessage") %>
</fieldset>
</body>

</html>