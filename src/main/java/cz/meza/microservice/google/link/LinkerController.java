package cz.meza.microservice.google.link;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/linker/search")
public class LinkerController {

    private WebClient webClient;
    private HtmlPage page;
    private String GOOGLE_URL = "http://www.google.com";

    private static final Logger LOG = LoggerFactory.getLogger(LinkerController.class);


    @PostConstruct
    public void init()  {
        webClient = new WebClient();
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setAppletEnabled(true);
    }

    @RequestMapping(method = RequestMethod.GET)
    public void testId(@RequestParam String search) throws IOException {

        LOG.info("Searching with parameter []", search);
        page = webClient.getPage(GOOGLE_URL);

        HtmlForm form = page.getFormByName("f");
        HtmlInput input = form.getInputByName("q");
        input.setValueAttribute(search);
        HtmlButton button = page.getElementByName("btnG");
        page = button.click();

        List<HtmlHeading3> listHeading = (List<HtmlHeading3>) page.getByXPath("//h3[@class='r']");
        boolean testLinked = false;
        for (HtmlHeading3 head: listHeading) {
            HtmlElement el = head.getElementsByTagName("a").get(0);
            if (el != null) {
                String href = el.getAttribute("href");
                LOG.info("Search url is: []", href);
            }
        }
        LOG.info("End of search");
    }

}
