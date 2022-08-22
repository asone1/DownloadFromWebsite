import CommonPck.automate;
import CommonPck.seleniumCommon;
import org.openqa.selenium.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static CommonPck.StringProcessor.getFileNameForSave;
import static CommonPck.StringProcessor.getLastPartOfUrl;
import static CommonPck.automate.*;
import static CommonPck.seleniumCommon.*;

public class app {

    private static WebDriver driver;

    public static void faj(String[] args) throws Exception {
//    public static void main(String[] args) throws Exception{

        WebDriver driver = seleniumCommon.startDriver();
        driver.get("https://www.chabad.org/library/article_cdo/aid/682956/jewish/Mishneh-Torah.htm");
        driver.manage().window().maximize();
        autoprint(driver, "C:\\Users\\tina.yu\\Desktop\\chabad\\Yesodei-haTorah\\a.pdf");
    }

    //        public static void faj(String[] args) throws Exception {
    public static void main(String[] args) throws Exception {

        WebDriver driver = seleniumCommon.startDriver();
        driver.get("https://www.chabad.org/library/article_cdo/aid/682956/jewish/Mishneh-Torah.htm");
        driver.manage().window().maximize();
        List<WebElement> books = driver.findElements(By.cssSelector("div[class='nested_item child_title small_vertical_margin']"));
        List<String> urlBooks = new ArrayList<>();
        List<String> urlChapters = new ArrayList<>();
        for (WebElement book : books) {
            for (WebElement a : book.findElements(By.tagName("a"))) {
                urlBooks.add(a.getAttribute("href"));
                System.out.println(defaultPath + "/" + getLastPartOfUrl(a.getAttribute("href")));
//                new File(defaultPath+"/"+getLastPartOfUrl(a.getAttribute("href"))).mkdirs();
            }

        }
        for (String urlBook : urlBooks) {
            driver.get(urlBook);
            clickIfExist(driver, By.xpath("//*[@id=\"dialog_0\"]/div/div[1]/div/div[1]/i"));
            List<WebElement> chapters = driver.findElements(By.cssSelector("div[class='media-item__body']"));
            String bookName = driver.findElement(By.xpath("//*[@id=\"co_content_container\"]/div/div[2]/header/div[1]/a[5]")).getText().replaceAll("\\s+","");
            if(!new File(defaultPath+"/"+bookName).exists())
                new File(defaultPath+"/"+bookName).mkdirs();
            if (chapters.size() > 1) {
                for (WebElement chapter : chapters) {
                    WebElement a_chapter = chapter.findElement(By.tagName("a"));
                    urlChapters.add(a_chapter.getAttribute("href"));
                }
                for (String urlChapter : urlChapters) {
                    driver.get(urlChapter);
                    autoprint(driver, defaultPath + bookName + "\\" + bookName + "_" + getFileNameForSave(urlChapter));
                }
                urlChapters = new ArrayList<>();
            } else {
                autoprint(driver, defaultPath + bookName);
            }

            if (bookName.contains("Avel")) break;
        }

        driver.close();
    }
}
