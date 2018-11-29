package chat.tamtam.api;

/*-
 * ------------------------------------------------------------------------
 * TamTam chat Bot API
 * ------------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ------------------------------------------------------------------------
 */

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
