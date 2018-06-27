package by.epam.task6.parser;

public class XMLParserFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    public XMLParserBuilder createPostcardBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {

            case DOM:
                return new DomBuilder();
            case STAX:
                return new StAXBuilder();
            case SAX:
                return new SaxBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
