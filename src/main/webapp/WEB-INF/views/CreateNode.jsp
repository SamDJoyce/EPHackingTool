<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Create a Node</title>
	<link rel="stylesheet" href="css/ep-system.css">
</head>

<body>
	<!-- Quick Create -->
	<!-- pick system type, generate new random system of that type -->
	<div id='quickCreate' class='create'>
		<form action='CreateNode' method='post'>
			<label for='mote'>Mote </label>
			<input type='radio' name='nodeType' id='mote' value='mote'>
			<label for='host'>Host </label>
			<input type='radio' name='nodeType' id='host' value='host'>
			<label for='server'>Server </label>
			<input type='radio' name='nodeType' id='server' value='server'><br>
			<button type='submit'>Quick Create</button>
			<input type='hidden' name='action' value='quickCreate'>
		</form>
	</div><hr>
	
	<!-- Full Create -->
	<!-- Form for all details of a node -->
	<div id='fullCreate' class='create'>
		<form action='CreateNode' method='post' >
			<!-- Name -->
			<label for='nodeName'>Node Name: </label><br>
			<input type='text' name='nodeName' id='nodeName'><br>
			<!-- OS Specs -->
				<!-- Type -->
			<label for='nodeType'>OS Name: </label><br>
			<input type='text' name='nodeType' id ='nodeType'><br>
				<!-- Firewall -->
			<label for='firewall'>Firewall Rating: </label><br>
			<input type='number' name='firewall' id ='firewall'><br>
				<!-- Infosec -->
			<label for='infosec'>Infosec Rating: </label><br>
			<input type='number' name='infosec' id ='infosec'><br>
				<!-- Armor -->
			<label for='armor'>Armor Rating: </label><br>
			<input type='number' name='armor' id ='armor'><br>
				<!-- Durability -->
			<label for='durability'>Durability Rating: </label><br>
			<input type='number' name='durability' id ='durability'><br>
				<!-- isDefended -->
			<label for='defended'> Node is Defended? </label>
			<input type='checkbox' name='defended' id='defended' value='true'><br>
			<!-- isMindware -->
			<label for='mindware'> Node is Running Mindware? </label>
			<input type='checkbox' name='mindware' id='mindware' value='true'><br>
			<!-- isVisible -->
			<label for='visible'> Node is Visible?: </label>
			<input type='checkbox' name='visible' id='visible' value='true'><br>
			<!-- Alerts -->
			<p>Set Starting Alert:</p>
			<label for='noneAlert'>None </label>
			<input type='radio' name='alert' id='noneAlert' value='none'>
			<label for='passiveAlert'>Passive </label>
			<input type='radio' name='alert' id='passiveAlert' value='passive'>
			<label for='activeAlert'>Active </label>
			<input type='radio' name='alert' id='activeAlert' value='active'>
			<!-- accounts -->
			<!-- TODO -->
			<!--  Linked nodes -->
			<!-- TODO -->
			<input type='hidden' name='action' value='fullCreate'><br>
			<input type='submit' value='Create Node'>
		</form>
	</div>
	
</body>

</html>