<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>JavaProxy</title>
		
		<style type="text/css">
		body { font: 14px Arial, sans-serif; }
		table { font: inherit; }
		.fontsize150 { font-size: 150%; }
		</style>
	</head>
	
	<body>
		<center>
			<table>
				<tr>
					<td><span class="fontsize150">JavaProxy</span></td>
				</tr>				
			</table>
			
			<table>
				<tr>
					<td>
						Enter the url you want to browse and hit Go
					</td>
				</tr>	
			</table>
			
			<form method="POST" action="jproxy">
			<table>
				<tr>
					<td><input type="text" name="inurl" size="40"/></td>
					<td><input type="submit" value="Go" onclick="return (this.form.inurl.value != '')"/></td>
				</tr>
			</table>
			</form>
			
			<table>
				<tr>
					<td align="center"><b>Things todo</b></td>
				</tr>				
				<tr>				
					<td>
						<ol style="margin:0;">
							<li>Links within iframe needs to be changed.</li>
							<li>Check to me made to disable or handle https:// protocol.</li>
							
						</ol>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>