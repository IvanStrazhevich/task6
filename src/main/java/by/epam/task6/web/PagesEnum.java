package by.epam.task6.web;

public enum PagesEnum {
    ERROR_PAGE("/jsp/ErrorPage.jsp"),
    LOGIN_PAGE("/jsp/LoginPage.jsp"),
    REGISTER_PAGE("/jsp/RegisterPage.jsp"),
    UPLOAD_PAGE("/jsp/UploadPage.jsp"),
    UPLOAD_RESULT_PAGE("/jsp/UploadResultPage.jsp"),
    WELCOME_PAGE("/jsp/WelcomePage.jsp"),
    MISSED_FILE_PAGE("/jsp/MissedFilePage.jsp");
    String value;

    PagesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
