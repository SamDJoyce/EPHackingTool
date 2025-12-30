<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List"
    import="hackingtool.devices.Hackable"
    import="hackingtool.devices.User"
%>

<%
    List<Hackable> nodes = (List<Hackable>) request.getAttribute("nodes");
    List<User>     users = (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GM Screen</title>
    <link rel="stylesheet" href="css/ep-system.css">
</head>

<body>

<h1>GM Screen</h1>

<!-- ================= NODES ================= -->
<div id="nodes" class='container'>
    <!-- ===== Action Bar ===== -->
    <div class="gm-actions">
        <form action="CreateNode" method="get" style="display:inline;">
            <button type="submit">Create Node</button>
        </form>

        <form action="CreateNetwork" method="get" style="display:inline;">
            <button type="submit">Create Network</button>
        </form>

        <form action="GMScreen" method="post" style="display:inline;"
              onsubmit="return confirm('Delete ALL nodes? This cannot be undone.');">
            <input type="hidden" name="action" value="deleteAllNodes">
            <button type="submit" class="danger">Delete All Nodes</button>
        </form>
    </div><br><br>

    <!-- ===== Node Cards ===== -->
    <h2>Mesh Nodes</h2>
    <div class="node-grid">
    <%
        if (nodes != null && !nodes.isEmpty()) {
            for (Hackable node : nodes) {
    %>
        <a class="node-card" href='GMControlNode?nodeID=<%= node.getID() %>' ><div class="node-card">
            <div class="node-name"><%= node.getName() %></div>

            <div class="node-meta">
                Alert: <%= node.getAlert() %><br>
                Accounts: <%= node.getAccounts().size()%>
                Stability: <%= node.getStability() %>
            </div>

            <form action="GMScreen" method="post"
                  onsubmit="return confirm('Delete node <%= node.getName() %>?');">
                <input type="hidden" name="action" value="deleteNode">
                <input type="hidden" name="nodeID" value="<%= node.getID() %>">
                <button type="submit" class="danger">Delete</button>
            </form>
        </div></a>
        
    <%
            }
        } else {
    %>
        <p class="empty">No nodes present.</p>
    <%
        }
    %>
    </div>
</div>

<!-- ================= USERS ================= -->
<div id="users" class='container'>
    <h2>Users</h2>

    <table id='usersTable'>
    	<!-- Headers -->
    	<tr><th>Name</th><th>Firewall</th><th>Infosec</th><th>Durability</th><th>Actions</th></tr>
    <%
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
    %> <!-- Existing User rows -->
		        <tr>
		        	<td><%= user.getName() %></td>
		        	<td><%= user.getFirewall() %></td>
		        	<td><%= user.getInfosec() %></td>
		        	<td><%= user.getDurability() %></td>
		        	<td><form action='GMScreen' method='post'>
		        		<input type='hidden' name='action' value='deleteUser'>
		        		<input type='hidden' name='userID' value='<%= user.getID() %>'>
		        		<input type='submit' value='Delete User'>
		        	</form></td>
		        </tr>
    <%
            }
        }
    %> <!-- New User row -->
		<tr>
			<!-- Name -->
			<td><input type='text' id='userName' name='userName' form='newUser'></td>
			<!-- Firewall -->
			<td><input type='number' id='firewall' name='firewall' form='newUser' min='0' style='width: 40px'></td>
			<!-- Infosec -->
			<td><input type='number' id='infosec' name='infosec' form='newUser' min='0' style='width: 40px'></td>
			<!-- Durability -->
			<td><input type='number' id='durability' name='durability' form='newUser' min='0' style='width: 40px'></td>
			<!-- Action Buttons -->
			<td><form action='GMScreen' method='post' id='newUser'>
				<input type='hidden' name='action' value='createUser'>
				<input type='submit' value='Create User'>
			</form></td>
		</tr>
    </table>
</div>

</body>
</html>
