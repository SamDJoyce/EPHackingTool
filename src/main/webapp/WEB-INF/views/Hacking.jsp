<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="hackingtool.hacking.Hacking"
    import="hackingtool.devices.Hackable"
    import="hackingtool.devices.User"
    import="hackingtool.devices.Account"
  %>
<!DOCTYPE html>
<html>
	<%
	// TODO assign this properly
		Hacking hack = (Hacking) request.getAttribute("hack");
		Hackable target = hack.getTarget();
		User hacker = hack.getHacker();
		Account account = target.getAccount(hacker);
	%>
<head>
	<meta charset="UTF-8">
	<title>Hacking</title>
</head>

<body>
	<!-- Target System Information -->
	<h1>Target System: <%= hack.getTargetName() %> </h1>
	<div id="target">
		<h2>Target</h2>
		<table id="targetTable">
			<!-- Headers -->
			<tr><th>System Name</th><th>Firewall</th><th>Infosec</th><th>Alert</th></tr>
			<!-- Data Rows -->
			<tr>
				<td><%= target.getName() %></td>
				<td><%= target.getFirewall() %></td>
				<td><%= target.getInfosec() %></td>
				<td><%= target.getAlert() %></td>
			</tr>			
		</table>
	</div>
	<br><hr><br>
	<div id="hacker">
		<h2>Hacker</h2>
		<table>
			<!-- Headers -->
			<tr><th>Name</th><th>Infosec</th><th>Firewall</th><th>Account</th><th>Status</th></tr>
			<!-- Data Rows -->
			<tr>
				<td><%= hacker.getName() %></td>
				<td><%= hacker.getInfosec() %></td>
				<td><%= hacker.getFirewall() %></td>
				<td><%= account != null ? account.getPriv() : "None" %></td>
				<td><%= account != null ? account.getStatus() : "N/A" %></td>
			</tr>
		</table>
	</div>
</body>

</html>