package oogle.util;

public interface Type {
    boolean checkBytes(int index);

    boolean checkBoolean(int index);

    boolean checkByte(int index);

    boolean checkShort(int index);

    boolean checkInt(int index);

    boolean checkLong(int index);

    boolean checkFloat(int index);

    boolean checkDouble(int index);

    boolean checkString(int index);

    String getName(int index);

    int size();

    int
        BYTES = 0,
        BOOLEAN = 1,
        BYTE = 2,
        SHORT = 3,
        INT = 4,
        LONG = 5,
        FLOAT = 6,
        DOUBLE = 7,
        STRING = 8;

    static Type create(int... types) {
        for (int i : types)
            if (i < 0 || i > 8) throw new IllegalArgumentException("Types not correct");
        return new ImplType(types);
    }
}

class ImplType implements Type{
    private final int types[];

    ImplType(int types[]){
        this.types = types;
    }

    @Override
    public boolean checkBytes(int index) {
        return types[index] == BYTES;
    }

    @Override
    public boolean checkBoolean(int index) {
        return types[index] == BOOLEAN;
    }

    @Override
    public boolean checkByte(int index) {
        return types[index] == BYTE;
    }

    @Override
    public boolean checkShort(int index) {
        return types[index] == SHORT;
    }

    @Override
    public boolean checkInt(int index) {
        return types[index] == INT;
    }

    @Override
    public boolean checkLong(int index) {
        return types[index] == LONG;
    }

    @Override
    public boolean checkFloat(int index) {
        return types[index] == FLOAT;
    }

    @Override
    public boolean checkDouble(int index) {
        return types[index] == DOUBLE;
    }

    @Override
    public boolean checkString(int index) {
        return types[index] == STRING;
    }

    @Override
    public String getName(int index) {
        switch (types[index]){
            case BYTES:
                return "BYTES";
            case BOOLEAN:
                return "BOOLEAN";
            case BYTE:
                return "BYTE";
            case SHORT:
                return "SHORT";
            case INT:
                return "INT";
            case LONG:
                return "LONG";
            case FLOAT:
                return "FLOAT";
            case DOUBLE:
                return "DOUBLE";
            case STRING:
                return "STRING";
            default:
                throw new IllegalStateException("wtf");
        }
    }

    @Override
    public int size() {
        return types.length;
    }
}


