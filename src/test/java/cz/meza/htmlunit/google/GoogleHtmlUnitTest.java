package cz.meza.htmlunit.google;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Test get results from google.
 */
public class GoogleHtmlUnitTest {

    private WebClient webClient;
    private HtmlPage page;

    private static final String GOOGLE_URL = "http://www.google.com";
    private static final String QUERY = "vladimir mezera";
    private static final String LINKED_IN = "linkedin";
    

    @Before
    public void init() throws IOException {
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(false);


        page = webClient.getPage(GOOGLE_URL);

        HtmlForm form = page.getFormByName("f");
        HtmlInput input = form.getInputByName("q");
        input.setValueAttribute(QUERY);
        HtmlButton button = page.getElementByName("btnG");
        page = button.click();
    }

    /**
     * Test that google return results.
     * @throws IOException
     */
    @Test
    public void runGoogleSearch() throws IOException {

         int sizeOfResults = page.getByXPath("//h3[@class='r']").size();
         Assert.assertNotEquals(sizeOfResults, 0);
    }

    /**
     * Test that my linkedin account is in first 10 requests.
     * @throws IOException
     */
    @Test
    public void findGoogleSearchLinkedInFirstPage() throws IOException {
        List<HtmlHeading3> listHeading = (List<HtmlHeading3>) page.getByXPath("//h3[@class='r']");
        boolean testLinked = false;
        for (HtmlHeading3 head: listHeading) {
            HtmlElement el = head.getElementsByTagName("a").get(0);
            if (el != null) {
                String href = el.getAttribute("href");
                if (href != null && href.contains(LINKED_IN)) {
                    testLinked = true;
                    break;
                }
            }
        }
        Assert.assertTrue(testLinked);

    }
}
