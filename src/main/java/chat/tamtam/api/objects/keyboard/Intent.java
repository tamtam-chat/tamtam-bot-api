package chat.tamtam.api.objects.keyboard;

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
