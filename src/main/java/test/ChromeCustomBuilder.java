package test;

import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.net.URL;

public class ChromeCustomBuilder extends ChromeDriverService.Builder {

    public ChromeCustomBuilder() {
        super();
    }

    @Override
    protected File findDefaultExecutable() {
        URL classLoader = ClassLoader.getSystemResource("chromedriver.exe");
        File file = new File(classLoader.getFile());
        return file;
    }
}
