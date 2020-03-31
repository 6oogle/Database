package oogle.util.sql;

import oogle.util.byteable.Decoder;
import oogle.util.type.Type;
import oogle.util.type.Types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public class SQLDecoder implements Decoder {
    private final Type type;
    private final ResultSet set;
    private final Consumer<SQLException> exceptions;
    private int offset;
    private int size;
    private int index;

    public SQLDecoder(Type type, ResultSet set, Consumer<SQLException> exceptions, int offset) {
        this.type = type;
        this.set = set;
        this.exceptions = exceptions;
        this.offset = offset;
        this.size = type.size();
    }

    @Override
    public byte[] readBytes() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.BYTE_ARRAY)) {
            this.incorrectType("BYTE_ARRAY");
        }

        try {
            return this.set.getBytes(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return null;
        }
    }

    @Override
    public boolean readBoolean() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.BOOLEAN)) {
            this.incorrectType("BOOLEAN");
        }

        try {
            return this.set.getBoolean(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return false;
        }
    }

    @Override
    public byte readByte() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.BYTE)) {
            this.incorrectType("BYTE");
        }

        try {
            return this.set.getByte(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return -1;
        }
    }

    @Override
    public short readShort() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.SHORT)) {
            this.incorrectType("SHORT");
        }

        try {
            return this.set.getShort(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return -1;
        }
    }

    @Override
    public int readInt() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.INT)) {
            this.incorrectType("INT");
        }

        try {
            return this.set.getInt(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return -1;
        }
    }

    @Override
    public long readLong() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.LONG)) {
            this.incorrectType("LONG");
        }

        try {
            return this.set.getLong(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return -1L;
        }
    }

    @Override
    public float readFloat() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.FLOAT)) {
            this.incorrectType("FLOAT");
        }

        try {
            return this.set.getFloat(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return -1.0F;
        }
    }

    @Override
    public double readDouble() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.DOUBLE)) {
            this.incorrectType("DOUBLE");
        }

        try {
            return this.set.getDouble(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return -1.0D;
        }
    }

    @Override
    public String readString() {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.STRING)) {
            this.incorrectType("STRING");
        }

        try {
            return this.set.getString(this.index++ + this.offset);
        } catch (SQLException var2) {
            this.exceptions.accept(var2);
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return this.size != this.index;
    }

    private void outSize() {
        throw new ArrayIndexOutOfBoundsException("Read over type, max " + this.size);
    }

    private void incorrectType(String called) {
        throw new IllegalArgumentException("Read incorrect type, called " + called + ", need " + this.type.get(this.index).name());
    }
}
