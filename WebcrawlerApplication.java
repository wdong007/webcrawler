package com.webcrawler;

import java.io.IOException;
import java.util.*


public class WebcrawlerApplication {

	public static Queue<String> queue = new LinkedList<>();
	public static Set<String> marked = new HashSet<>();
	public static String regex = "http[s]*://(\\w+\\.)*(\\w+)";

	public static void bfs(String root) throws IOException {
		queue.add(root);
		BufferedReader br = null;
		while (!queue.isEmpty()) {
			String crawledUrl = queue.poll();
			System.out.println("\n=== Site crawled : " + crawledUrl + "===");
			if (marked.size() > 100) {
				return;
			}
			boolean ok = false;
			URL url  = null;


			while (!ok) {
				try {
					url = new URL(crawledUrl);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					ok = true;
				} catch (MalformedURLException e) {
					System.out.println("*** Maformed URL : " + crawledUrl);
					crawledUrl = queue.poll();
					ok = false;
				} catch (IOException ioe) {
					System.out.println("*** IOException for URL : " + crawledUrl);
					crawledUrl = queue.poll();
					ok = false;
				}
			}

			StringBuilder sb = new StringBuilder();
			String tmp = null;
			while ((tmp= br.readLine()) != null) {
				sb.append(tmp);
			}
			tmp = sb.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(tmp);
			while (matcher.find()) {
				String w = matcher.group();
				if(!marked.contains(w)) {
					marked.add(w);
					System.out.println("Sited added for crawling : " + w);
					queue.add(w);
				}
			}
		}
		if (br != null) {
			br.close();
		}
	}

	public static void show Result() {
		for (String s: marked) {
			Sysout.out.println("* " + s);
		}
	}

	public static void main(String[] args) {
		try {
			bfs("http://www.ssaurel.com/blog");
			showResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
