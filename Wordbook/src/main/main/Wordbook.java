package main;

import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Wordbook {
	public static void main(String[] args) throws IOException {

		// Create a new instance of the Chrome driver
		// For Mac and Windows, use different binary
		String currentOS = System.getProperty("os.name");
		if (currentOS.contains("Mac")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
		}
		if (currentOS.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		}

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		capabilities.setCapability("chrome.binary", "driver/chromedriver");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		WebDriver driver = new ChromeDriver(capabilities);

		// WebDriver driver = new FirefoxDriver();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		int numTries = 10;
		while (true) {
			// driver.navigate().to("https://www.google.com");
			try {
				// And now use this to visit Youdo dictionary query page
				// driver.get("http://dict.youdao.com/search?le=eng&q=flint&keyfrom=dict.index");
				driver.navigate().refresh();
				driver.navigate().to("http://dict.youdao.com/search?le=eng&q=flint");
				// Alternatively the same thing can be done like this
				// driver.navigate().to("http://www.google.com");
				break;
			} catch (Exception e) {
				if (--numTries == 0)
					throw e;
				System.out.println("Login Timeout...");
			}
		}

		WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("登录")));
		login.click();
		WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		username.sendKeys("yuelinyan@hotmail.com");
		WebElement pword = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
		pword.sendKeys("handangdang");
		pword.submit();

		// Find the 'Add To Wordbook' button
		// WebElement addButton = wait.until(ExpectedConditions
		// .elementToBeClickable(By.id("wordbook")));
		// WebElement errorTypo =
		// driver.findElement(By.className("error-typo"));

		String[] Unfound = new String[100];

		try {
			// Open the file which contains the words you want to add
			FileInputStream fstream = new FileInputStream("SortedWordbook.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String newWord;

			//
			FileOutputStream wordBook = new FileOutputStream("Wordbook.txt", true);

			File currentWords = new File("Wordbook.txt");

			int i = 0;
			int num = 0;

			// Read File Line By Line, start from the second line

			while ((newWord = br.readLine()) != null) {
				newWord = newWord.trim();
				num++;
				System.out.println("======================= NO. " + num + " =======================");
				System.out.println(newWord);

				if (countWord(newWord, currentWords) > 0) {
					System.out.println("Already Added");
					continue;
				}
				// Print the content on the console
				if (newWord.length() > 1 && newWord.length() <= 50) {
					// System.out.println(newWord.length());
					System.out.println("http://dict.youdao.com/search?le=eng&q=" + newWord);
					try {
						driver.navigate().refresh();
						driver.get("http://dict.youdao.com/search?le=eng&q=" + newWord);
					} catch (Exception e) {
						System.out.println("Taking too long to add " + newWord + ". Moving on...");
					}

					// String url = "http://dict.youdao.com/search?le=eng&q=" + newWord +
					// "&keyfrom=dict.index";
					// ((JavascriptExecutor) driver).executeScript("window.location.href='" + url +
					// "'");

					try {
						WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("wordbook")));
						if (addButton.getAttribute("class").contains("add")) {
							addButton.click();
						}
						wordBook.write(newWord.getBytes());
						wordBook.write("\r\n".getBytes());
					} catch (Exception e) {
						System.out.println("Cannot find this word in Youdao.");
						Unfound[i] = newWord;
						i++;
					}
				}
				System.out.println("================================================");
			}
			wordBook.close();
			FileOutputStream out = new FileOutputStream("unfound.txt");
			for (int j = 0; j < i; j++) {
				System.out.println(Unfound[j]);
				out.write(Unfound[j].getBytes());
				out.write("\r\n".getBytes());
			}

			// Close the input stream
			in.close();
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error123: " + e.getMessage());
		}
		driver.close();
		driver.quit();
		System.err.println("DONE... Version 1.1");
	}

	static int countWord(String word, File file) {
		int count = 0;
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String nextToken = scanner.nextLine();
				if (nextToken.equalsIgnoreCase(word))
					count++;
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}
}