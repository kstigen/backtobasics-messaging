package no.knowit.backtobasics.atom.server;

import org.apache.abdera.protocol.server.Provider;
import org.apache.abdera.protocol.server.impl.DefaultProvider;
import org.apache.abdera.protocol.server.impl.SimpleWorkspaceInfo;
import org.apache.abdera.protocol.server.servlet.AbderaServlet;

public class NewsArticleProviderServlet extends AbderaServlet {

    protected Provider createProvider() {
        NewsArticleCollectionAdapter ca = new NewsArticleCollectionAdapter();
        ca.setHref("newsArticle");

        SimpleWorkspaceInfo wi = new SimpleWorkspaceInfo();
        wi.setTitle("NewsArticle Directory workspace");
        wi.addCollection(ca);

        DefaultProvider provider = new DefaultProvider("/");
        provider.addWorkspace(wi);
        provider.init(getAbdera(), null);
        return provider;
    }

}
