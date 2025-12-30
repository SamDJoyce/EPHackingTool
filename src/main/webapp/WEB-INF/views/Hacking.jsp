<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="hackingtool.devices.Hackable"
    import="hackingtool.devices.User"
    import="hackingtool.devices.Account"
    import="hackingtool.dice.Tests"
    import="hackingtool.logging.Event"
    import="java.util.Deque"
    import="java.util.ArrayDeque"
    import="java.util.List"
  %>
<!DOCTYPE html>
<html>
	<%
		final User hacker 	  = (User) request.getAttribute("hacker");
		final Hackable target = (Hackable) request.getAttribute("target");
		final Account account = target.getAccount(hacker);
		final Tests test 	  = (Tests) request.getAttribute("test");
		final Boolean sniffed = (Boolean) request.getAttribute("sniffed");
		final Deque<Event> eventLog = (ArrayDeque<Event>) request.getAttribute("eventLog");
		final List<Hackable> linkedNodes = (List<Hackable>) request.getAttribute("linkedNodes");
	%>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/ep-system.css">
	<title>Hacking</title>
</head>

<body>
	<!-- Target System Information -->
	<h1>Target Node: <%= target.getName() %> </h1>
	<p><a href='Nodes'>&larr; back to Nodes List</a></p>
	<div id="target" class='container'>
		<h2>Node</h2>
		<table id="targetTable">
			<!-- Headers -->
			<tr><th>Name</th><th>Firewall</th><th>Infosec</th><th>Alert</th><th>Damage</th><th>Wounds</th></tr>
			<!-- Data Rows -->
			<tr>
				<td><%= target.getName() %></td>
				<td><%= target.getFirewall() %></td>
				<td><%= target.getInfosec() %></td>
				<td><%= target.getAlertString() %></td>
				<td><%= target.getDamage() %>/<%= target.getDurability() %></td>
				<td><%= target.getWounds() %></td>
			</tr>			
		</table>
		<!-- Action buttons -->
		<!-- Intrusion -->
		<form method='post' action='Hacking'>
			<input type='hidden' name='action'value='intrusion'>
			<input type='hidden' name='targetID' value='<%= target.getID() %>'>
			<input type='hidden' name='hackerID' value='<%= hacker.getID() %>'>
			<input type='hidden' name='sniffed' value='<%= sniffed %>'>
			<input type='submit' value='Perform Intrusion'>
			<input type='checkbox' name='bruteForce' value='true' id='bfCheck'>
			<label for='bfCheck'>Brute force</label>
		</form>
		<% 
		if (!sniffed) {
		%>
		<!-- Sniff Traffic -->
		<form method='get' action='Hacking'>
			<input type='hidden' name='sniffed' value='true'>
			<input type='hidden' name='targetID' value='<%= target.getID() %>'>
			<input type='hidden' name='hackerID' value='<%= hacker.getID() %>'>
			<input type='submit' value='Sniff Connections'>
		</form>
		<%
		}	
		%>
		<!-- Mesh Attack -->
		<form method='post' action='Hacking'>
			<input type='hidden' name='action' value='meshAttack'>
			<input type='hidden' name='targetID' value='<%= target.getID() %>'>
			<input type='hidden' name='hackerID' value='<%= hacker.getID() %>'>
			<input type='hidden' name='sniffed' value='<%= sniffed %>'>
			<input type='submit' value='Mesh Attack'>
			<% if (account != null) { %>
				<input type='checkbox' id='local' name='local' value='true' >
				<label for='local'>Local Attack</label>
			<%} else { %>
				<input type='hidden' name='local' value='false'>
			<%} %>
		</form>
		<% 	
		if (account != null) {
		%>
			<!-- These tests are only possible with an account -->
			<!-- Improve status -->
			<form method='post' action='Hacking'>
				<input type='hidden' name='action'value='improveStatus'>
				<input type='hidden' name='targetID' value='<%= target.getID() %>'>
				<input type='hidden' name='hackerID' value='<%= hacker.getID() %>'>
				<input type='hidden' name='sniffed' value='<%= sniffed %>'>
				<input type='submit' value='Improve Status'>
			</form>
			<!-- Subversion -->
			<form method='post' action='Hacking'>
				<input type='hidden' name='action' value='subversion'>
				<input type='hidden' name='targetID' value='<%= target.getID() %>'>
				<input type='hidden' name='hackerID' value='<%= hacker.getID() %>'>
				<input type='hidden' name='sniffed' value='<%= sniffed %>'>	
				<input type='submit' value='Subvert System'>
			</form>
		<!-- Linked nodes list -->
		<%		
		}
		if (linkedNodes != null && !linkedNodes.isEmpty() && sniffed) {
			%>
				<h3>Linked Nodes</h3>
				<div class="linked-nodes">
			<%
			    for (Hackable node : linkedNodes) {
			%>
			    <a class="node-card" href="Hacking?targetID=<%= node.getID() %>&hackerID=<%= hacker.getID() %>">
			        <div class="node-name"><%= node.getName() %></div>
			        <div class="node-meta">Linked System</div>
			    </a>
			<%
				}
			}
			%>
			</div>
	</div>
	<hr>
	<div id="hacker" class='container'>
		<h2>Hacker</h2>
		<table>
			<!-- Headers -->
			<tr><th>Name</th><th>Infosec</th><th>Firewall</th><th>Account</th><th>Status</th><th>Damage</th><th>Wounds</th></tr>
			<!-- Data Rows -->
			<tr>
				<td><%= hacker.getName() %></td>
				<td><%= hacker.getInfosec() %></td>
				<td><%= hacker.getFirewall() %></td>
				<td><%= account != null ? account.getPriv() : "None" %></td>
				<td><%= account != null ? account.getStatus() : "N/A" %></td>
				<td><%= hacker.getDamage() %>/<%= hacker.getDurability() %></td>
				<td><%= hacker.getWounds() %></td>
			</tr>
		</table>
	</div>
	<hr>
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