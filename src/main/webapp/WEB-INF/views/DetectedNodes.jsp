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
	<div class='container'>
		<table id='nodesTable'>
			<!-- Headers -->
			<tr> 
				<th>Name</th><th>Account</th><th>Alert</th><th>Stability</th>
			</tr>
			<!-- Data Rows -->
			<%
			Alerts alert;
			String alertString;
			String nodeStability;
			Boolean hasAccount;
			for (Hackable node : nodes) {
				// Only display the node if it is visible
				if (node.isVisible()){
					alert = node.getAlert();
					alertString = alert.toString();
					hasAccount = node.accountPresent(user);
					nodeStability = node.getStability();
			%>
				<tr>
					<td><a href='Hacking?targetID=<%= node.getID() %>&hackerID=<%= user.getID() %>' ><%= node.getName() %></a></td>
					<td><%= hasAccount ? node.getAccount(user).getPriv().toString() : "No account"  %></td>
					<td><%= alertString %></td>
					<td><%= nodeStability %> </td>
				</tr>
			<%
				}
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