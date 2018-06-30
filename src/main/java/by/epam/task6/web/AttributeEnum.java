package by.epam.task6.web;

public enum AttributeEnum {
    LANG("lang"), DOM("dom"), SAX("sax"), STAX("stax"),
    NEED_LOGIN("needLogin"), SUCCESS("success"),
    PARSER("parser"), RESULT("result"), POSTCARDS("postcards"),
    LOGIN("login"), PASSWORD("password"),
    LOGGED("logged"),
    USER_EXIST("userExist"), USER_REGISTERED("userRegistered"),
    USER_NOT_REGISTERED("userNotRegistered"),
    GREETING("greeting"), NEED_REGISTER("needRegister");

    private String value;

    AttributeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
