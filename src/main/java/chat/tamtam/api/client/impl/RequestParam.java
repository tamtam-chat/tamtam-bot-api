package chat.tamtam.api.client.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author alexandrchuprin
 */
public class RequestParam<T> {
    private final String name;
    private T value;
    private boolean isRequired;

    public RequestParam(@NotNull String name, @NotNull RequestParams holder) {
        this(name, null, holder);
    }

    public RequestParam(@NotNull String name, @Nullable T defaultValue, @NotNull RequestParams holder) {
        this.name = name;
        this.value = defaultValue;
        holder.addParam(this);
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    public void setValue(@NotNull T value) {
        this.value = value;
    }

    @NotNull
    public RequestParam<T> required() {
        this.isRequired = true;
        return this;
    }

    public boolean isRequired() {
        return isRequired;
    }
}
