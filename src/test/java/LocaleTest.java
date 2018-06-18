
import by.epam.task6.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleTest {
    private StringBuffer stringBuffer;
    private static Logger logger = LogManager.getLogger();
    private static final String CORRECT_LINE = "Вітаем!\nСпіс лекаў\n" +
            "Welcome!\nMedicine List\n" +
            "Добро пожаловать!\nСписок препаратов\n";

    @BeforeMethod
    public void setUp() {
        stringBuffer = new StringBuffer();
    }

    @AfterMethod
    public void tearDown(){
        stringBuffer = null;

    }

    @Test
    public void LocaleTest() {
        String expected = CORRECT_LINE;
        for (int i = 0; i < 3; i++) {
            String country;
            String language;
            switch (i) {
                case 1:
                    country = "US";
                    language = "EN";
                    break;
                case 2:
                    country = "RU";
                    language = "ru";
                    break;
                default:
                    country = "BY";
                    language = "be";
            }
            Locale current = new Locale(language, country);
            ResourceBundle resourceBundle = ResourceBundle.getBundle("message", current);
            stringBuffer.append(resourceBundle.getString("message.welcomePage") + '\n');
            stringBuffer.append(resourceBundle.getString("label.header.medicineList") + '\n');
        }
        logger.info(stringBuffer.toString());
        String actual = stringBuffer.toString();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void ResourceManagerTest() {
        String expected = CORRECT_LINE;
        ResourceManager manager = ResourceManager.INSTANCE;
        manager.changeResource(new Locale("be", "BY"));
        stringBuffer.append(manager.getString("message.welcomePage") + '\n');
        stringBuffer.append(manager.getString("label.header.medicineList") + '\n');
        manager.changeResource(new Locale("en", "US"));
        stringBuffer.append(manager.getString("message.welcomePage") + '\n');
        stringBuffer.append(manager.getString("label.header.medicineList") + '\n');
        manager.changeResource(new Locale("ru", "RU"));
        stringBuffer.append(manager.getString("message.welcomePage") + '\n');
        stringBuffer.append(manager.getString("label.header.medicineList") + '\n');
        logger.info(stringBuffer.toString());
        String actual = stringBuffer.toString();
        Assert.assertEquals(actual, expected);
    }
}
