package by.epam.task6.web;

public enum CommandEnum {
    UPLOAD_PAGE("UploadPage"), UPLOAD_RESULT_PAGE("UploadResultPage"),
    WELCOME_PAGE("WelcomePage"), LOGIN_PAGE("LoginPage"), CHECK_LOGIN("CheckLogin"),
    REGISTER_USER("RegisterUser");
    String value;

    CommandEnum(String value) {
        this.value = value;
    }
}
