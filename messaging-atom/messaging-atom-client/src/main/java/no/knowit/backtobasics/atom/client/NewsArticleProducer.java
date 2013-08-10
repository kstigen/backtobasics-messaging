package no.knowit.backtobasics.atom.client;

import java.util.Date;

import no.knowit.backtobasics.domain.NewsArticle;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.Response;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;

public class NewsArticleProducer {

    public static final String FEED_URL = "http://localhost:8080/newsArticle";

    public static void main(String args[]) {
        NewsArticle newsArticle = new NewsArticle(1, "En nyhetsartikkel" + new Date().toString(), "Med et veldig spennende innhold", new Date(), new Date());

        NewsArticleProducer producer = new NewsArticleProducer();
        producer.produce(newsArticle);
    }

    private void produce(NewsArticle newsArticle) {
        Abdera abdera = new Abdera();
        AbderaClient client = new AbderaClient(abdera);

        Entry entry = abdera.newEntry();
        entry.setTitle(newsArticle.getHeadline());
        entry.setText(newsArticle.getBody());
        entry.setPublished(newsArticle.getCreated());
        entry.setUpdated(newsArticle.getUpdated());
        entry.setContent(newsArticle.getBody());
        entry.setId("tag:example.org,2011:foo");
        System.out.println(entry.toString());


        ClientResponse response = client.post(FEED_URL, entry);
        if(response.getType() == Response.ResponseType.SUCCESS){
            System.out.println("Successful posting to server");
        } else {
            System.out.println(response.getStatusText());
            System.out.println("Could not post to server....");
        }
    }
}
