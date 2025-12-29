<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import='hackingtool.devices.Hackable'
    import='hackingtool.devices.Alerts'
    import='hackingtool.devices.Account'
    import='hackingtool.devices.IntruderStatus'
    import='hackingtool.devices.Privileges'
    import='hackingtool.devices.User'
    import='java.util.List'
%>
<%
	Hackable node = (Hackable) request.getAttribute("node");
	List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Control Node</title>
	<link rel="stylesheet" href="css/ep-system.css">
</head>

<body>
	<h1>Control Node</h1>
	<h2><%= node.getName() %></h2>
	<p><a href='GMScreen'>&larr; back to GM Screen</a></p>
	<hr>
	<!-- Stats bar w. firewall, infosec, OS -->
	<div id='node'><h3>Node</h3>
	<table id="nodeTable">
			<!-- Headers -->
			<tr><th>Name</th><th>Firewall</th><th>Infosec</th><th>Alert</th><th>Damage</th><th>Wounds</th></tr>
			<!-- Data Rows -->
			<tr>
				<td><input type='text' id='nodeName' name='nodeName' form='nodeForm' value="<%= node.getName() %>" size='10'></td>
				<td><input type='number' id='firewall' name='firewall' form='nodeForm' value="<%= node.getFirewall() %>" style='width: 40px'> (<%= node.getFirewall() %>)</td>
				<td><input type='number' id='infosec' name='infosec' form='nodeForm' value="<%= node.getInfosec() %>" style='width: 40px'> (<%= node.getInfosec() %>)</td>
				<td><select id='alert' name='alert' form='nodeForm'>
					<option value='none' <%= Alerts.NONE.equals(node.getAlert()) ? "selected" : ""  %>>None</option>
					<option value='passive' <%= Alerts.PASSIVE.equals(node.getAlert()) ? "selected" : ""  %>>Passive</option>
					<option value='active' <%= Alerts.ACTIVE.equals(node.getAlert()) ? "selected" : ""  %>>Active</option>
				</select></td>
				<td><input type='number' id='nodeDamage' name='nodeDamage' form='nodeForm' value="<%= node.getDamage() %>" style='width: 40px'> (<%= node.getDamage() %>/<%= node.getDurability() %>)</td>
				<td><input type='number' id='nodeWounds' name='nodeWounds' form='nodeForm' value="<%= node.getWounds() %>" style='width: 40px'> (<%= node.getWounds() %>)</td>
			</tr>			
	</table></div>
		<div id='nodeControl'> <form action='GMControlNode' method='post' id='nodeForm'>	
		<!-- Mindware toggle -->
		<label for='mindware'>Running mindware? </label>
		<input type='checkbox' id='mindware' name='mindware' value='true' <%= node.isMindware() ? "checked" : "" %>><br>
		<!-- Defended toggle -->
		<label for='defended'>Node is Defended? </label>
		<input type='checkbox' id='defended' name='defended' value='true' <%= node.isDefended() ? "checked" : "" %>><br>
		<!-- Visible toggle -->
		<label for='visible'>Node is Visible? </label>
		<input type='checkbox' id='visible' name='visible' value='true' <%= node.isVisible() ? "checked" : "" %>><br>
		<input type='hidden' name='nodeID' value='<%= node.getID() %>'>
		<input type='hidden' name='action' value='updateNode'><br>
		<input type='submit' value='Update Node'>
	</form></div>
	<!-- List of Accounts -->
	<div id='accounts'><h3>Node Accounts</h3>
	
	<table id='accountsTable'>
		<tr><th>Name</th><th>Status</th><th>Privileges</th><th>Damage</th><th>Wounds</th><th>Actions</th></tr>
		<% 
			for (Account a : node.getAccounts()) {
		%>
			<tr>
				<!-- Name -->
				<td><%= a.getUser().getName() %></td>
				<!-- Status -->
				<td><select id='accStatus' name='accStatus' form='accountsForm'>
					<option value='hidden' <%= IntruderStatus.HIDDEN.equals(a.getStatus()) ? "selected" :"" %>>Hidden</option>
					<option value='covert' <%= IntruderStatus.COVERT.equals(a.getStatus()) ? "selected" :"" %>>Covert</option>
					<option value='spotted' <%= IntruderStatus.SPOTTED.equals(a.getStatus()) ? "selected" :"" %>>Spotted</option>
				</select></td>
				<!-- Privileges -->
				<td><select id='accPriv' name='accPriv' form='accountsForm'>
					<option value='public' <%= Privileges.PUBLIC.equals(a.getPriv()) ? "selected" : "" %>>Public</option>
					<option value='user' <%= Privileges.USER.equals(a.getPriv()) ? "selected" : "" %>>User</option>
					<option value='security' <%= Privileges.SECURITY.equals(a.getPriv()) ? "selected" : "" %>>Security</option>
					<option value='admin' <%= Privileges.ADMIN.equals(a.getPriv()) ? "selected" : "" %> >Admin</option>
				</select></td>
				<!-- Damage -->
				<td><input type='number' name='accDamage' id='accDamage' form='accountsForm' value='<%= a.getDamage() %>' style='width: 40px'> (<%= a.getDamage() %>/<%= a.getDurability() %>)</td>
				<!-- Wounds -->
				<td><input type='number' name='accWounds' id='accWounds' form='accountsForm' value='<%= a.getWounds() %>' style='width: 40px'> (<%= a.getWounds() %>)</td>
				<td>
					<form action='GMControlNode' method='post' id='accountsForm' style="display:inline">
						<input type='hidden' name='action' value='updateAccount'>
						<input type='hidden' name='accID' value='<%= a.getID() %>'>
						<input type='hidden' name='nodeID' value='<%= node.getID() %>'>
						<input type='submit' value='Update'>
					 </form>
					 <form action='GMControlNode' method='post' id='deleteAccount' style="display:inline">
						<input type='hidden' name='action' value='deleteAccount'>
						<input type='hidden' name='accID' value='<%= a.getID() %>'>
						<input type='hidden' name='nodeID' value='<%= node.getID() %>'>
						<input type='submit' value='Delete'>
					</form>
				 </td>
			</tr>		
		<% 	
			}
		%>
		<tr><!-- New Account -->
			<!-- Select User -->
			<td><select id='accUser' name='accUser' form='createAccount'>
				<option selected></option>
				<%
				for (User u : users){
				%>
					<option value='<%= u.getID() %>'><%= u.getName() %></option>
				<%
				}
				%>
			</select></td>
			<!-- Status -->
			<td><select id='accStatus' name='accStatus' form='createAccount'>
				<option selected></option>
				<option value='hidden'>Hidden</option>
				<option value='covert'>Covert</option>
				<option value='spotted'>Spotted</option>
			</select></td>
			<!-- Privileges -->
			<td><select id='accPriv' name='accPriv' form='createAccount'>
				<option selected></option>
				<option value='public'>Public</option>
				<option value='user'>User</option>
				<option value='security'>Security</option>
				<option value='admin'  >Admin</option>
			</select></td>
			<!--  -->
			<td></td>
			<td></td>
			<!-- Submit -->
			<td>
				<form action='GMControlNode' method='post' id='createAccount'>
					<input type='hidden' name='action' value='createAccount'>
					<input type='hidden' name='nodeID' value='<%= node.getID() %>'>
					<input type='submit' value='New Account'>
				</form>
			</td>
		</tr>
	</table>
	</div>
	<!-- List of linked nodes -->
	
</body>

</html>