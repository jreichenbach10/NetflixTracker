import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;
import org.jsoup.*; 
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class ScrapeNetflix {
	/*
	 * Jack T. Reichenbach December 22, 2019
	 * 
	 * Methods: public static ArrayList<Title> scrape() Scrapes html of this
	 * particular url. Extracts info from its release calendar and returns a list of
	 * Titles
	 *
	 */
	public static ArrayList<Title> scrape() {
		final String[] myShows;
		myShows = new String[] {"Ozark", "Haunting of Hill House", "You", "Stranger Things", "Peaky Blinders",
				"Master of None", "Dark", "Mindhunter", "Big Mouth", "The End of the F***ing World", "Black Mirror", 
				"Sex Education", "AJ and the Queen"};
		final String url = "https://www.whats-on-netflix.com/coming-soon/";
		ArrayList<Title> titles = new ArrayList<Title>();

		Element calendar; // full calendar html
		Document document; // full webpage html
		Elements elements; // list of title elements, date headers and titles4
		String name;
		String releaseDate;
		String type;
		String description;

		try {
			document = Jsoup.connect(url).get();
			calendar = document.selectFirst("ul.release-calendar");
			elements = calendar.getElementsByTag("li");
			releaseDate = elements.get(0).text();

			for (int i = 1; i < elements.size(); i++) {

				if (elements.get(i).className().equals("date-header")) { // if current element is a date
					releaseDate = elements.get(i).text();
				}

				else { // current element is a tv show or movie
					name = elements.get(i).child(2).text();
					type = elements.get(i).child(3).text().substring(6);
					description = elements.get(i).child(4).text();

					titles.add(new Title(name, releaseDate, type, description));
				}
			}

			for (int i = titles.size() - 1; i > 0; i--) {
				for (int j = 0; j < myShows.length; j++) {
					if (titles.get(i).getName().startsWith(myShows[j])) {
						sendNotification(titles.get(i));
					}
				}
			}
		}

		catch (IOException ioe) {
			System.err.println(ioe);
		}
		return titles;

	}

	public static void sendNotification(Title title) {
		String header = title.getName();
		String text = "Returning on " + title.getReleaseDate();
		
		if (SystemTray.isSupported()) {
			try {
				SystemTray tray = SystemTray.getSystemTray();
				Image image = Toolkit.getDefaultToolkit()
						.createImage("C:\\Users\\wildc\\eclipse-workspace\\NetflixTracker\\Resources\\iconfinder_64-netflix_4202092.png");
				TrayIcon icon = new TrayIcon(image, "NetflixTracker");
				tray.add(icon);
				icon.displayMessage(header, text, MessageType.INFO);
				tray.remove(icon);
				
			} catch (Exception ex) {
				System.err.print(ex);
			}
		}

	}

	public static void main(String[] args) {
		scrape();
	}

}
