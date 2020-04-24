//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oogle.util.sql;

import oogle.util.byteable.Encoder;
import oogle.util.type.Type;
import oogle.util.type.Types;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public class SQLEncoder implements Encoder {
    private final PreparedStatement statement;
    private final Type type;
    private final Consumer<SQLException> exceptions;
    private final int offset;
    private final int size;
    private int index;

    public SQLEncoder(PreparedStatement statement, Type type, Consumer<SQLException> exceptions, int offset) {
        this.statement = statement;
        this.type = type;
        this.exceptions = exceptions;
        this.offset = offset;
        this.size = type.size();
    }

    @Override
    public Encoder writeRaw(byte[] array, int offset, int size) {
        return writeBytes(array, offset, size);
    }

    @Override
    public Encoder writeBytes(byte[] array, int offset, int size) {
        byte[] newArray = new byte[size];
        System.arraycopy(array, offset, newArray, 0, size);
        writeBytes(newArray);
        return this;
    }

    @Override
    public Encoder writeBytes(byte[] array) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.BYTE_ARRAY)) {
            this.incorrectType("BYTE_ARRAY");
        }

        try {
            this.statement.setBytes(this.index++ + this.offset, array);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    @Override
    public Encoder writeBoolean(boolean b) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.BOOLEAN)) {
            this.incorrectType("BOOLEAN");
        }

        try {
            this.statement.setBoolean(this.index++ + this.offset, b);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    @Override
    public Encoder writeByte(byte b) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.BYTE)) {
            this.incorrectType("BYTE");
        }

        try {
            this.statement.setByte(this.index++ + this.offset, b);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    @Override
    public Encoder writeShort(short s) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.SHORT)) {
            this.incorrectType("SHORT");
        }

        try {
            this.statement.setShort(this.index++ + this.offset, s);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    @Override
    public Encoder writeInt(int i) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.INT)) {
            this.incorrectType("INT");
        }

        try {
            this.statement.setInt(this.index++ + this.offset, i);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    @Override
    public Encoder writeLong(long l) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.LONG)) {
            this.incorrectType("LONG");
        }

        try {
            this.statement.setLong(this.index++ + this.offset, l);
        } catch (SQLException var4) {
            this.exceptions.accept(var4);
        }

        return this;
    }

    @Override
    public Encoder writeFloat(float f) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.FLOAT)) {
            this.incorrectType("FLOAT");
        }

        try {
            this.statement.setFloat(this.index++ + this.offset, f);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    @Override
    public Encoder writeDouble(double d) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.DOUBLE)) {
            this.incorrectType("DOUBLE");
        }

        try {
            this.statement.setDouble(this.index++ + this.offset, d);
        } catch (SQLException var4) {
            this.exceptions.accept(var4);
        }

        return this;
    }

    @Override
    public Encoder writeString(String s) {
        if (this.size == this.index) {
            this.outSize();
        }

        if (!this.type.check(this.index, Types.STRING)) {
            this.incorrectType("STRING");
        }

        try {
            this.statement.setString(this.index++ + this.offset, s);
        } catch (SQLException var3) {
            this.exceptions.accept(var3);
        }

        return this;
    }

    private void outSize() {
        throw new ArrayIndexOutOfBoundsException("Write over type, max " + this.size);
    }

    private void incorrectType(String called) {
        throw new IllegalArgumentException("Write incorrect type, called " + called + ", need " + this.type.get(this.index).name());
    }
}
