package example;

import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
public class Selenium2Example {
	public static void main(String[] args) throws IOException {
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// And now use this to visit Google
		driver.get("http://dict.youdao.com/search?le=eng&q=flint&keyfrom=dict.index");
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		WebElement login = driver.findElement(By.linkText("登录"));
		login.click();
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("yuelinyan@hotmail.com");
		WebElement pword = driver.findElement(By.id("password"));
		pword.sendKeys("hanxiaomei");
		pword.submit();

		// Find the text input element by its name
		// WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		// element.sendKeys("flint");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		// element.submit();

		// Check the title of the page
		// System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		/*
		 * (new WebDriverWait(driver, 10)).until(new
		 * ExpectedCondition<Boolean>() { public Boolean apply(WebDriver d) {
		 * return d.getTitle().toLowerCase().startsWith("cheese!"); } });
		 */

		// Should see: "cheese! - Google Search"
		// System.out.println("Page title is: " + driver.getTitle());

		// Close the browser

		// (new WebDriverWait(driver, 10)).until(new
		// ExpectedCondition<Boolean>() {
		// public Boolean apply(WebDriver d) {
		// return d.getTitle().toLowerCase().contains("flint");
		// }
		// });

		// Find the text input element by its name
		
		WebElement addButton = driver.findElement(By.id("wordbook"));
		try {
			// 1. Buffered input file
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					new FileInputStream("D:\\seleniumTest\\output.txt")));
			in.readLine();
			String s = new String();
			while ((s = in.readLine()) != null){				
				if (s.length()<=20 && !s.contains(" ")){
					System.out.println("http://dict.youdao.com/search?le=eng&q="+s+"&keyfrom=dict.index");
					driver.get("http://dict.youdao.com/search?le=eng&q="+s+"&keyfrom=dict.index");
					addButton = driver.findElement(By.id("wordbook"));
					if (addButton.getAttribute("class").contains("add"))
						addButton.click();
					System.out.println(addButton.getAttribute("class").toString());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
			in.close();

		} catch (EOFException e) {
			System.out.println("End of stream encountered");
		}
		
	}
}