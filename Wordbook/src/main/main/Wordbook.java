package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.lightbody.bmp.proxy.ProxyServer;

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

		// Block images. This will make the page loading much faster, but I cannot use
		// it in this case because I need that 'Add' button which is an image.
		HashMap<String, Object> images = new HashMap<String, Object>();
		images.put("images", 2);

		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.managed_default_content_settings.images", 2);

		ChromeOptions options = new ChromeOptions();
		// options.setExperimentalOption("prefs", prefs);

		// Start the server and get the selenium proxy object
		ProxyServer server = new ProxyServer(8080); // package net.lightbody.bmp.proxy

		server.start();
		server.setCaptureHeaders(true);
		// Blacklist google analytics
		// server.blacklistRequests("\\S*ydstatic\\S* \\S*adingo\\S*", 410);
		// Or whitelist what you need
		server.whitelistRequests(
				"http?:\\/\\/\\S*.youdao.com\\S* https:\\/\\/\\S*.youdao.com\\S* \\S*.js \\S*.css \\S*.jpeg  \\S*.png \\S*.gif"
						.split(" "),
				200);

		Proxy proxy = server.seleniumProxy(); // Proxy is package org.openqa.selenium.Proxy

		// configure it as a desired capability
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.PROXY, proxy);

		// options.addArguments("test-type");
		capabilities.setCapability("chrome.binary", "driver/chromedriver");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		WebDriver driver = new ChromeDriver(capabilities);

		// WebDriver driver = new FirefoxDriver();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait = new WebDriverWait(driver, 10);

		int numTries = 10;
		while (true) {
			// driver.navigate().to("https://www.google.com");
			try {
				// And now use this to visit Youdo dictionary query page
				// driver.get("http://dict.youdao.com/search?le=eng&q=flint&keyfrom=dict.index");
				// driver.navigate().refresh();
				driver.navigate().to("http://dict.youdao.com/search?le=eng&q=flint");
				break;
			} catch (Exception e) {
				if (--numTries == 0)
					throw e;
				System.out.println("Login Timeout...");
			}
		}


		WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("登录")));
		login.click();
		WebElement agreePrRule = wait.until(ExpectedConditions.elementToBeClickable(By.id("agreePrRule")));
		agreePrRule.click();
		WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
		username.sendKeys("yuelinyan@hotmail.com");
		WebElement pword = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
		pword.sendKeys("handangdang");
		pword.submit();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		String[] Unfound = new String[100];

		try {
			BufferedReader reader = new BufferedReader(new FileReader("SortedWordbook.txt"));
			int wordNum = 0;
			while (reader.readLine() != null)
				wordNum++;
			reader.close();
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
				System.out
						.println("======================= NO. " + num + " of " + wordNum + " =======================");
				System.out.println(newWord);

				if (countWord(newWord, currentWords) > 0) {
					System.out.println("Already Added");
					continue;
				}

				if (newWord.length() > 1 && newWord.length() <= 50) {
					// System.out.println(newWord.length());
					System.out.println("http://dict.youdao.com/search?le=eng&q=" + newWord);
					try {
						driver.get("http://dict.youdao.com/search?le=eng&q=" + newWord);
					} catch (Exception e) {
						System.out.println("Taking too long to add " + newWord + ". Moving on...");
						continue;
					}

					try {
						WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("wordbook")));
						if (addButton.getAttribute("class").contains("add")) {
							addButton.click();
							System.out.println("Added...");
						}
						wordBook.write(newWord.getBytes());
						wordBook.write("\r\n".getBytes());
					} catch (Exception e) {
						wordBook.write(newWord.getBytes());
						wordBook.write("\r\n".getBytes());
						System.out.println("Fix Me...");
						Unfound[i] = newWord;
						i++;
						continue;
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
		server.stop();
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