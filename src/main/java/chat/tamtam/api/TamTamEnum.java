package chat.tamtam.api;

import java.lang.reflect.Array;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author alexandrchuprin
 */
public interface TamTamEnum {
    String name();

    byte getId();

    static <T extends Enum<T>> T create(Class<T> enumClass, String value) {
        return value == null ? null : Enum.valueOf(enumClass, value.toUpperCase());
    }

    static <T extends Enum<T> & TamTamEnum> T getById(T[] values, int id) {
        if (id > values.length) {
            throw new IllegalArgumentException("No value found with id " + id);
        }

        return values[id];
    }

    static <T extends Enum<T> & TamTamEnum> T[] createIdentifiable(Class<T> enumClass, T[] values) {
        @SuppressWarnings("unchecked")
        T[] identifiableValues = (T[]) Array.newInstance(enumClass, values.length);
        for (T intent : values) {
            if (intent.getId() < 0 || intent.getId() >= values.length) {
                throw new IllegalArgumentException("Id should be in range from 0 to " + (values.length - 1));
            }

            if (identifiableValues[intent.getId()] != null) {
                throw new IllegalArgumentException("Duplicate id for " + intent);
            }

            identifiableValues[intent.getId()] = intent;
        }

        return identifiableValues;
    }

    @JsonValue
    default String getKey() {
        return name().toLowerCase();
    }
}
