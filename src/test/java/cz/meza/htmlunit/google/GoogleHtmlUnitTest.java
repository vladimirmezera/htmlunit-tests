package cz.meza.htmlunit.google;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GoogleHtmlUnitTest {

    private WebClient webClient;
    private HtmlPage page;

    private static final String GOOGLE_URL = "http://www.google.com";

    @BeforeEach
    public void init() throws IOException {
        webClient = new WebClient();
        long time = System.currentTimeMillis();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);
        page = webClient.getPage(GOOGLE_URL);

    }

    @Test
    public void runGoogleSearch() throws IOException {

    }
}
