package CommonPck;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.page.Page;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static CommonPck.seleniumCommon.exeJs;

public class automate {
    public static Robot returnRobot() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return robot;
    }

    public static void sendKey(Robot robot, int key) {
        robot.keyPress(key);
        robot.keyRelease(key);

    }

    public static void sendKey(int key) {
        Robot robot = returnRobot();
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    public static void sendKey(int... keys) {
        Robot robot = returnRobot();
        for (int key : keys) {
            robot.keyPress(key);
        }
        for (int key : keys) {
            robot.keyRelease(key);
        }

    }

    public static int[] getKey(char c) throws Exception {
        int[] keycode = new int[2];
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            keycode[0] = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(Character.toUpperCase(c));
        } else {
            switch (c) {
                case ':':
                    keycode[0] = KeyEvent.VK_SHIFT;
                    keycode[1] = KeyEvent.VK_SEMICOLON;
                    System.out.println(keycode);
                    break;
                case '\\':
                    keycode[0] = KeyEvent.VK_BACK_SLASH;
                    break;
                case '.':
                    keycode[0] = KeyEvent.VK_PERIOD;
                    break;
            }
        }


        return keycode;
    }

    public static void sendKeys(String s) throws Exception {
        Robot robot = returnRobot();
        for (char c : s.toCharArray()) {
            System.out.println(c);
            sendKey(getKey(c));
        }
    }

    public static void mouseClick(int x, int y) {
        Robot robot = returnRobot();
        robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private static short count = -1;

    public static void autoprint(WebDriver driver, String path) throws Exception {
        //呼叫print diloag
        try {
            exeJs(driver, "setTimeout(function() { window.print(); }, 0);");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(2000);
        if (count < 0) {
            //選擇目的地
            mouseClick(1075, 158);
            Thread.sleep(1000);
            //選擇另存為pdf
            mouseClick(1075, 198);
            ++count;
        }

        // 按確定列印
        Thread.sleep(1000);
        mouseClick(1058, 675);
        Thread.sleep(2000);
        StringSelection stringSelection = new StringSelection(path);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        Thread.sleep(3000);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        //按儲存
        Thread.sleep(1000);
        mouseClick(730, 446);
        Thread.sleep(1000);
    }

}
