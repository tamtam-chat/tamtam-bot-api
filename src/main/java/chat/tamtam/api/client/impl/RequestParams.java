package chat.tamtam.api.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

/**
 * @author alexandrchuprin
 */
public class RequestParams {
    private final List<RequestParam<?>> params;

    public RequestParams() {
        this.params = new ArrayList<>();
    }

    void addParam(@NotNull RequestParam param) {
        params.add(param);
    }

    public List<RequestParam<?>> getParams() {
        return params;
    }
}
