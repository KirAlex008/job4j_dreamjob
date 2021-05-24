<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete</title>
</head>
<body>
<br>
<form action="<%=request.getContextPath()%>/delete?id=<%=request.getParameter("id")%>" method="post">
    <button type="submit" class="btn btn-primary">Delete Candidate</button>
</form>
</body>
</html>
