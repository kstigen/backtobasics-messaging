package no.knowit.backtobasics.atom.client;

import no.knowit.backtobasics.domain.NewsArticle;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.Response;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsArticleConsumer {

    public static final String FEED_URL = "http://localhost:8080/newsArticle";

    private Map<Integer, NewsArticle> receivedNewsArticles = new HashMap<Integer, NewsArticle>();
    boolean running = false;

    public static void main(String args[]) {
        NewsArticleConsumer newsArticleConsumer = new NewsArticleConsumer();
        newsArticleConsumer.listenForArticles();
    }

    private void listenForArticles() {
        running = true;
        while(running) {
            List<Integer> newEntries = fetchArticles();

            for (Integer entryId : newEntries) {
                System.out.println(receivedNewsArticles.get(entryId));
            }


            sleep(1);

        }
    }

    private List<Integer> fetchArticles() {
        List<Integer> newEntriesIds = new ArrayList<Integer>();

        Abdera abdera = new Abdera();
        AbderaClient client = new AbderaClient(abdera);

        ClientResponse response = client.get(FEED_URL);
        if(response.getType() == Response.ResponseType.SUCCESS){
            Document<Feed> document = response.getDocument();
            Feed feed = document.getRoot();

            for (Entry entry : feed.getEntries()) {
                Integer entryId = new Integer(entry.getId().toString());
                if(!receivedNewsArticles.containsKey(entryId))  {
                    receivedNewsArticles.put(entryId, createNewsArticle(entry));
                    newEntriesIds.add(entryId);
                }
            }
        }
        return newEntriesIds;
    }

    private NewsArticle createNewsArticle(Entry entry) {
        Date updated = entry.getUpdated();
        Date created = entry.getPublished();
        String body = entry.getText();
        String headline = entry.getTitle();
        int id = Integer.parseInt(entry.getId().toString());
        return new NewsArticle(id, headline, body, created, updated);
    }

    private void sleep(int secondsToSleep) {
        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void testAbdera() {

    }
}
