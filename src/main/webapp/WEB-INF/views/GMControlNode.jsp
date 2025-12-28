<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import='hackingtool.devices.Hackable'
    import='hackingtool.devices.Alerts'
%>
<%
	Hackable node = (Hackable) request.getAttribute("node");
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
	<hr>
	
	<!-- Stats bar w. firewall, infosec, OS -->
	<div id='node'><table id="nodeTable">
			<!-- Headers -->
			<tr><th>Name</th><th>Firewall</th><th>Infosec</th><th>Alert</th><th>Damage</th><th>Wounds</th></tr>
			<!-- Data Rows -->
			<tr>
				<td><%= node.getName() %></td>
				<td><%= node.getFirewall() %></td>
				<td><%= node.getInfosec() %></td>
				<td><%= node.getAlertString() %></td>
				<td><%= node.getDamage() %>/<%= node.getDurability() %></td>
				<td><%= node.getWounds() %></td>
			</tr>			
	</table></div>
	<!-- Alert selector -->
	<label for='alert'>Alert Level</label>
	<select id='alert' name='alert'>
		<option value='none' <%= Alerts.NONE.equals(node.getAlert()) ? "selected" : ""  %>>None</option>
		<option value='passive' <%= Alerts.PASSIVE.equals(node.getAlert()) ? "selected" : ""  %>>Passive</option>
		<option value='active' <%= Alerts.ACTIVE.equals(node.getAlert()) ? "selected" : ""  %>>Active</option>
	</select>
	<!-- Mindware toggle -->
	<label for='mindware'>Running mindware? </label>
	<input type='checkbox' id='mindware' name='mindware' value='true' <%= node.isMindware() ? "checked" : "" %>><br>
	<!-- Defended toggle -->
	<label for='defended'>Node is Defended? </label>
	<input type='checkbox' id='defended' name='defended' value='true' <%= node.isDefended() ? "checked" : "" %>><br>
	<!-- Visible toggle -->
	<label for='visible'>Node is Visible? </label>
	<input type='checkbox' id='visible' name='visible' value='true' <%= node.isVisible() ? "checked" : "" %>><br>

	<!-- List of Accounts -->
	
	
	<!-- List of linked nodes -->
	
</body>

</html>