<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import='java.util.List'
    import='java.util.ArrayList'
    import='hackingtool.devices.Hackable'
    import='hackingtool.devices.Alerts'
    import='hackingtool.devices.User'
%>
<%
	List<Hackable> nodes = (ArrayList<Hackable>) request.getAttribute("nodes");
	User user = (User) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/ep-system.css">
	<title>Detected Nodes</title>
</head>

<body>
	<h1>Detected Nodes</h1>
	<div id='target'>
		<table id='nodesTable'>
			<!-- Headers -->
			<tr> 
				<th>Name</th><th>Account</th><th>Alert</th><th>Stability</th>
			</tr>
			<!-- Data Rows -->
			<%
			Alerts alert;
			String alertString;
			Boolean hasAccount;
			for (Hackable node : nodes) {
				alert = node.getAlert();
				alertString = alert.toString();
				hasAccount = node.accountPresent(user);
			%>
			<tr>
				<td><a href='Hacking?targetNode=<%= node.getID() %>' ><%= node.getName() %></a></td>
				<td><%= hasAccount ? "Present" : "No account" %></td>
				<td><%= alertString %></td>
				<!-- Status based on wounds, system damage percentage -->
				<td> TBD </td>
			</tr>
			<%
			}
			%>

		</table>
	</div>
	
	<div id='hacker'>
		<h2>Home Device</h2>
		<!-- Home device stats -->
	</div>
</body>

</html>