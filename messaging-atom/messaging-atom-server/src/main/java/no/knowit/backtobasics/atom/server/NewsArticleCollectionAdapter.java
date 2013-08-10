package no.knowit.backtobasics.atom.server;

import no.knowit.backtobasics.domain.IdCounter;
import no.knowit.backtobasics.domain.NewsArticle;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.i18n.text.Sanitizer;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Person;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;
import org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsArticleCollectionAdapter extends AbstractEntityCollectionAdapter<NewsArticle>{

    private static final String FEED_ID = "no.knowit.backtobasics.messaging.newsarticlefeed";
    private static final String FEED_TITLE = "Back To Basics News Articles";
    private static final String FEED_AUTHOR = "Knowit Objectnet";

    private IdCounter idCounter = new IdCounter();
    private Map<Integer, NewsArticle> newsArticles = new HashMap<Integer, NewsArticle>();

    public NewsArticleCollectionAdapter() {
        NewsArticle n = new NewsArticle(idCounter.getNextId(), "The first newsstory", "Interesting stuff", new Date(), new Date());
        newsArticles.put(n.getId(), n);
    }


    @Override
    public NewsArticle postEntry(String title, IRI iri, String summary, Date updated, List<Person> authors, Content content, RequestContext requestContext) throws ResponseContextException {
        NewsArticle newsArticle = new NewsArticle(idCounter.getNextId(), title, content.getText().trim(), updated, updated);
        newsArticles.put(newsArticle.getId(), newsArticle);

        return newsArticle;
    }

    @Override
    public void putEntry(NewsArticle newsArticle, String title, Date updated, List<Person> authors, String summary, Content content, RequestContext requestContext) throws ResponseContextException {
        newsArticle.setHeadline(title);
        newsArticle.setBody(content.getText().trim());
        newsArticle.setUpdated(updated);
    }

    @Override
    public void deleteEntry(String resourceName, RequestContext requestContext) throws ResponseContextException {
        Integer newsArticleId = new Integer(resourceName);
        newsArticles.remove(newsArticleId);
    }

    @Override
    public Object getContent(NewsArticle newsArticle, RequestContext requestContext) throws ResponseContextException {
        Content content = requestContext.getAbdera().getFactory().newContent(Content.Type.TEXT);
        content.setText(newsArticle.getBody());
        return content;
    }

    @Override
    public Iterable<NewsArticle> getEntries(RequestContext requestContext) throws ResponseContextException {
        return newsArticles.values();
    }

    @Override
    public NewsArticle getEntry(String resourceName, RequestContext requestContext) throws ResponseContextException {
        Integer newsArticleId = new Integer(resourceName);
        return newsArticles.get(newsArticleId);
    }

    @Override
    public String getId(NewsArticle newsArticle) throws ResponseContextException {
        return Integer.toString(newsArticle.getId());
    }

    @Override
    public String getName(NewsArticle newsArticle) throws ResponseContextException {
        return newsArticle.getId() + "-" + Sanitizer.sanitize(newsArticle.getHeadline());
    }

    @Override
    public String getTitle(NewsArticle newsArticle) throws ResponseContextException {
        return newsArticle.getHeadline();
    }

    @Override
    public Date getUpdated(NewsArticle newsArticle) throws ResponseContextException {
        return newsArticle.getUpdated();
    }

    @Override
    public String getAuthor(RequestContext requestContext) throws ResponseContextException {
        return FEED_AUTHOR;
    }

    @Override
    public String getId(RequestContext requestContext) {
        return FEED_ID;
    }

    @Override
    public String getTitle(RequestContext requestContext) {
        return FEED_TITLE;
    }
}
