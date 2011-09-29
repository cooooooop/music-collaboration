<% 
String uploadUrl = (String) request.getAttribute("uploadUrl");
String message = (String) request.getParameter("message");
%>		

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
  body {
    font-family: Georgia, "Times New Roman",
          Times, serif;
    color: #001C5E;
    background-color: #C5A159 }
  a:link {
    color: #2C53AE }
  a:visited {
    color: #2C53AE }
  </style>
  
    <script type="text/javascript">
        function getFilename()
        {
            document.getElementById('txtTitle').value = document.getElementById('fileInput').value;
        }
    </script>
	</head>
	<body>		
		<form method="POST" action="<%= uploadUrl %>" enctype="multipart/form-data">
			<table width="100%">
				<tr>	
					<td>File</td>
					<td width="100%">
						<input id="fileInput" type="file" name="file" style="width:100%" onchange="getFilename()"/>						
					</td>
				</tr>
				<tr>	
					<td>Title</td>
					<td width="100%"><input id="txtTitle" type="text" name="title" style="width:100%"/></td>
				</tr>
				<tr>
				</tr>
			</table>
			<center>
			<br>
			Allow commercial uses of your work?<br>
			<input type="radio" name="ccRadio" value="yes">Yes <a href="http://creativecommons.org/licenses/by/3.0/" target="_blank">Tell me more...</a><br>
			<input type="radio" name="ccRadio" value="no" checked>No <a href="http://creativecommons.org/licenses/by-nc/3.0/" target="_blank">Tell me more...</a><br>
			<br>
			<input type="submit" value="Upload"/>
			<br>
			<%= message %>
			</center>
		</form>
	</body>	
</html>