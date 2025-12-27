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
<div id="nodes">
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
    </div><hr>

    <!-- ===== Node Cards ===== -->
    <h2>Mesh Nodes</h2>
    <div class="node-grid">
    <%
        if (nodes != null && !nodes.isEmpty()) {
            for (Hackable node : nodes) {
    %>
        <a class="node-card" href='Hacking?hackerID=1&targetID=<%= node.getID() %>' ><div class="node-card">
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
<div id="users">
    <h2>Users</h2>

    <ul class="user-list">
    <%
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
    %>
        <li>
            <strong><%= user.getName() %></strong>
        </li>
    <%
            }
        } else {
    %>
        <li class="empty">No users found.</li>
    <%
        }
    %>
    </ul>
</div>

</body>
</html>
