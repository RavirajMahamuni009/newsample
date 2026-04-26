package sampleProject;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.Test;

public class Ssampleclass {

	@Test
	public void openNaukri() {
		// Uses Playwright (already declared in pom.xml) to open Naukri.com,
		// print the page title and current URL, then close the browser.
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			Page page = browser.newPage();
			page.navigate("https://www.naukri.com");

			// Wait for network to be idle to allow any redirects/popups to load
			page.waitForLoadState();

			System.out.println("Page title: " + page.title());
			System.out.println("Current URL: " + page.url());

			// If you want to try to capture a jobs count on the page, you can
			// try locating a likely element. The site structure may change, so
			// this is optional and best-effort.
			try {
				String bodyText = page.content();
				// Simple heuristic: print if the word "jobs" appears in the page
				if (bodyText.toLowerCase().contains("job") || bodyText.toLowerCase().contains("jobs")) {
					System.out.println("The page contains the word 'job(s)'. You can add specific selectors to extract counts.");
				}
			} catch (Exception e) {
				System.out.println("Could not inspect page content: " + e.getMessage());
			}

			browser.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to open Naukri.com: " + e.getMessage(), e);
		}
	}

}
