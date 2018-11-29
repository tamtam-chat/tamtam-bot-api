package chat.tamtam.api.objects.keyboard;

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

import com.fasterxml.jackson.annotation.JsonCreator;

import chat.tamtam.api.TamTamEnum;

/**
 * @author alexandrchuprin
 */
public enum Intent implements TamTamEnum {
    DEFAULT(0),
    POSITIVE(1),
    NEGATIVE(2);

    private static final Intent[] IDENTIFIABLE_VALUES = TamTamEnum.createIdentifiable(Intent.class, values());

    private final byte id;

    Intent(int id) {
        this.id = (byte) id;
    }

    @JsonCreator
    public static Intent create(String value) {
        return TamTamEnum.create(Intent.class, value);
    }

    public static Intent getById(int id) {
        return TamTamEnum.getById(IDENTIFIABLE_VALUES, id);
    }

    @Override
    public byte getId() {
        return id;
    }
}
