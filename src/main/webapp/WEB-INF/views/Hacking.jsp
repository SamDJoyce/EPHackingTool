<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="hackingtool.devices.Hackable"
    import="hackingtool.devices.User"
    import="hackingtool.devices.Account"
    import="hackingtool.dice.Tests"
    import="hackingtool.logging.Event"
    import="java.util.Deque"
    import="java.util.ArrayDeque"
  %>
<!DOCTYPE html>
<html>
	<%
		final User hacker 	  = (User) request.getAttribute("hacker");
		final Hackable target = (Hackable) request.getAttribute("target");
		final Account account = target.getAccount(hacker);
		final Tests test 	  = (Tests) request.getAttribute("test");
		final Deque<Event> eventLog = (ArrayDeque<Event>) request.getAttribute("eventLog");
	%>
<head>
	<meta charset="UTF-8">
	<title>Hacking</title>
</head>

<body>
	<!-- Target System Information -->
	<h1>Target System: <%= target.getName() %> </h1>
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
				<td><%= target.getAlertString() %></td>
			</tr>			
		</table>
		<!-- Action buttons -->
		<form method='post' action='Hacking'>
			<input type='hidden' name='action'value='intrusion'>
			<input type='checkbox' name='bruteForce' value='true' id='bfCheck'>
			<label for='bfCheck'>Brute force</label>
			<br>
			<input type='submit' value='Perform Intrusion'>
		</form>
			<% 	
		if (account != null){
		%>
			<!-- These tests are only possible with an account -->
			<!-- Improve status -->
			<form method='post' action='Hacking'>
				<input type='hidden' name='action'value='improveStatus'>
				<input type='submit' value='Improve Status'>
			</form>
			<!-- Subversion -->
			<form method='post' action='Hacking'>
				<input type='hidden' name='action' value='subversion'>
				<input type='submit' value='SubvertSystem'>
			</form>
		<%		
		}
		%>
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
	<br><hr><br>
	<!-- Show activity logs -->
	<% 	
	if (eventLog != null){
		Deque<String> log;
		for (Event event : eventLog) {
			log = event.getLog();
			for (String message : log){
	%>
			<p><%= message %> </p>
	<%	
			}
	%>
			<br>
	<%
		}		
	}
	%>
</body>

</html>