package core.library;

public class PropertyLoader {


    private static volatile PropertyLoader instance;

    public static PropertyLoader getInstance() {
        if (instance == null) {

            synchronized (PropertyLoader.class) {
                if (instance == null) {
                    instance = new PropertyLoader();
                }
            }
        }
        return instance;
    }

    private String browser;

    private String baseUrl;


    public String getBrowser() {
        return browser;
    }


    public void setBrowser(String browser) {
        this.browser = browser;
    }


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}

