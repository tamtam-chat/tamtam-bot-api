package chat.tamtam.api.requests.queries;

import org.jetbrains.annotations.Nullable;

import chat.tamtam.api.client.impl.RequestParams;

/**
 * @author alexandrchuprin
 */
public class TamTamQuery<T> extends RequestParams {
    private final String url;
    private final Class<T> responseType;
    private final Object body;
    private final boolean isPost;

    public TamTamQuery(String url, Class<T> responseType) {
        this(url, null, responseType, true);
    }

    public TamTamQuery(String url, Class<T> responseType, boolean isPost) {
        this(url, null, responseType, isPost);
    }

    public TamTamQuery(String url, Object body, Class<T> responseType, boolean isPost) {
        this.url = url;
        this.responseType = responseType;
        this.body = body;
        this.isPost = isPost;
    }

    public Class<T> getResponseType() {
        return responseType;
    }

    public String getURL() {
        return url;
    }

    @Nullable
    public Object getBody() {
        return body;
    }

    public boolean isPost() {
        return isPost;
    }
}
