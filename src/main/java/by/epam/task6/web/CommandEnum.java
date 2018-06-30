package by.epam.task6.web;

public enum CommandEnum {
    UPLOAD_PAGE("UploadPage"), UPLOAD_RESULT_PAGE("UploadResultPage"),
    WELCOME_PAGE("WelcomePage"), LOGIN_PAGE("LoginPage"), CHECK_LOGIN("CheckLogin"),
    REGISTER_USER("RegisterUser"),REGISTER_PAGE("RegisterPage");
    private String value;



    CommandEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
