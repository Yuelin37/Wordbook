package main;

import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wordbook {
	public static void main(String[] args) throws IOException {
		// Create a new instance of the Chrome driver
		// For Mac and Windows, use different binary
		String currentOS = System.getProperty("os.name");
		if (currentOS.contains("Mac")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
		}
		if (currentOS.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver",
					"driver/chromedriver.exe");
		}
		WebDriver driver = new ChromeDriver();

		// And now use this to visit Youdo dictionary query page   
		driver.get("http://dict.youdao.com/search?le=eng&q=flint&keyfrom=dict.index");
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");
		
		// Login
		WebElement login = driver.findElement(By.linkText("登录"));
		login.click();
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("yuelinyan@hotmail.com");
		WebElement pword = driver.findElement(By.id("password"));
		pword.sendKeys("handangdang");
		pword.submit();

		

		// Find the 'Add To Wordbook' button
		WebElement addButton = driver.findElement(By.id("wordbook"));
		//WebElement errorTypo = driver.findElement(By.className("error-typo"));
		
		try {
			// Open the file which contains the words you want to add
			FileInputStream fstream = new FileInputStream("b.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String newWord;
			// TODO
			// I have some problem with the first chars in the first line
			// Read the first line, so the word in the first line will not be prcossed
	
			
			int i=0;
			// Read File Line By Line, start from the second line
			while ((newWord = br.readLine()) != null) {
				// Print the content on the console
				System.out.println(i);
				i++;
				if (newWord.length() > 1 && newWord.length() <= 50) {
					System.out.println(newWord.length());
					System.out
							.println("http://dict.youdao.com/search?le=eng&q="
									+ newWord + "&keyfrom=dict.index");
					driver.get("http://dict.youdao.com/search?le=eng&q="
							+ newWord + "&keyfrom=dict.index");
					try {
						addButton = driver.findElement(By.id("wordbook"));
						assert addButton.isDisplayed()==true;
					
					} catch(AssertionError ae) {
					   System.out.println("The table was located, but not displayed.");
					}
					
					if (addButton.getAttribute("class").contains("add"))
						addButton.click();
					System.out.println(addButton.getAttribute("class")
							.toString());
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}
}