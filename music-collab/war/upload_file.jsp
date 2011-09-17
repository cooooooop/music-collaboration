<% 
String uploadUrl = (String) request.getAttribute("uploadUrl");
%>		

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
	<body>		
		<form method="POST" action="<%= uploadUrl %>" enctype="multipart/form-data">
			<table>
				<tr>	
					<td>Title</td>
					<td><input type="text" name="title" /></td>
				</tr>
				<tr>	
					<td>Upload</td>
					<td>
						<input type="file" name="file" />						
					</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" /></td>
				</tr>				
			</table>
		</form>
	</body>	
</html>