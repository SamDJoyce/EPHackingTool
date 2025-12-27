<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Create a Mesh Network</title>
	<link rel="stylesheet" href="css/ep-system.css">
</head>

<body>
	<h1>Create a Network</h1>
	<p><a href='GMScreen'>&larr; back to GM Screen</a></p>
	<div id='createNetwork'><form method='post' action='CreateNetwork'>
		<!-- Network owner -->
		<label for='owner'>Network Owner:</label><br>
		<input type='text' name='owner' id='owner'><br><br>
		<!-- Network Type -->
		<p>Network Type:</p>
		<label for='pan'>PAN </label>
		<input type='radio' id='pan' name='netType' value='pan'>
		<label for='home'>Home </label>
		<input type='radio' id='home' name='netType' value='home'>
		<label for='office'>Office </label>
		<input type='radio' id='office' name='netType' value='office'><br><br>
		<!-- Hub Device Type -->
		<p>Hub Device:</p>
		<label for='inserts'>Mesh Inserts </label>
		<input type='radio' id='inserts' name='hubDevice' value='inserts'>
		<label for='ecto'>Ecto </label>
		<input type='radio' id='ecto' name='hubDevice' value='ecto'> <br><br>
		<!-- Number of devices in network -->
		<label for='numDevices'>Number of devices on the network: </label><br>
		<input type='number' id='numDevices' name='numDevices' min='0' max='10'><br><br>
		<!-- Submit -->
		<input type='submit' value='Create'>
	</form></div>
</body>

</html>