<html>
<head>
    <title>Upload Page</title>
</head>
<body>
<form action="ResultPage"
      enctype="multipart/form-data"
      method="post">
    Upload file:
    <input type="file" style="color: darkslateblue" name="content" value="Select file">
    <input type="submit" style="color: darkslateblue" value="Upload file">
    <input type="hidden" name="action" value="ResultPage">
</form>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: darkslateblue" value="Welcome Page">
</form>
</body>
</html>
