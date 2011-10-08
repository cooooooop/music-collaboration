<%@ page import="java.util.List" %>
<%@ page import="com.solution.musiccollab.shared.value.AudioFileDAO" %>

<% 
String uploadUrl = (String) request.getAttribute("uploadUrl");
String message = (String) request.getParameter("message");
List<AudioFileDAO> audioFiles = (List<AudioFileDAO>) request.getAttribute("audioFiles");
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
			<center>
				Select an audio file from the selection box:
				<select name="fileList">
				<%
			         for(AudioFileDAO file : audioFiles) {
			         	out.print("<option value=" + file.getFilePath() + ">"+file.getFileName()+"</option>");
			         }
				%>
			
				</select>
				<br>
				<table width="100%">
					<tr>	
						<td>Mix Name</td>
						<td width="100%"><input id="txtMixName" type="text" name="mixName" style="width:100%"/></td>
					</tr>
				</table>
				<br>
				<input type="submit" value="Mix"/>
				<%= message %>
			</center>
		</form>
	</body>	
</html>