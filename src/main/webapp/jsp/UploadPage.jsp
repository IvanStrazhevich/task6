<html>
<head>
    <title>Upload Page</title>
</head>
<body>
<form action="UploadResultPage"
      enctype="multipart/form-data"
      method="post">

    Upload file:
    <input type="file" style="color: darkslateblue" name="content" value="Select file">
    <input type="hidden" name="action" value="UploadResultPage">
    <select name="parser">
        <option value="${dom}">${dom}</option>
        <option value="${sax}">${sax}</option>
        <option value="${stax}">${stax}</option>
    </select>
    <input type="submit" style="color: darkslateblue" value="Upload file and parse">
</form>
<form action="WelcomePage"
      method="post">
    <input type="hidden" name="action" value="WelcomePage">
    <input type="submit" style="color: darkslateblue" value="Welcome Page">
</form>
</body>
</html>
