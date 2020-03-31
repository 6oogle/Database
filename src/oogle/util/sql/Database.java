//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oogle.util.sql;

import oogle.util.type.Type;
import oogle.util.type.TypeEntry;
import oogle.util.type.Types;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Database<K, V> {
    private final Type<K> keyType;
    private final Type<V> valueType;
    private final String table;
    private final Supplier<Connection> connection;
    private final Consumer<SQLException> exceptions;
    private final String remove;
    private final String select;
    private final String write;

    public Database(Type<K> keyType, Type<V> valueType, String table, Supplier<Connection> connection, Consumer<SQLException> exceptions) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.table = table;
        this.connection = connection;
        this.exceptions = exceptions;
        StringBuilder k = new StringBuilder();
        TypeEntry[] var7 = keyType.array();
        int var8 = var7.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            TypeEntry entry = var7[var9];
            k.append(entry.name()).append(" = ? AND ");
        }

        String str = k.toString();
        str = str.substring(0, str.length() - 5);
        this.remove = "DELETE FROM " + table + " WHERE " + str;
        this.select = "SELECT * FROM " + table + " WHERE " + str;
        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();
        TypeEntry[] var17 = valueType.array();
        int var11 = var17.length;

        int var12;
        TypeEntry entry;
        for(var12 = 0; var12 < var11; ++var12) {
            entry = var17[var12];
            keys.append(entry.name()).append(',');
            values.append("?,");
        }

        k = new StringBuilder();
        var17 = keyType.array();
        var11 = var17.length;

        for(var12 = 0; var12 < var11; ++var12) {
            entry = var17[var12];
            k.append(entry.name()).append(",");
            values.append("?,");
        }

        String f = k.toString();
        String one = keys.toString();
        String two = values.toString();
        this.write = "INSERT INTO " + table + " (" + f + one.substring(0, one.length() - 1) + ") VALUES(" + two.substring(0, two.length() - 1) + ")";
    }

    public Database(Type<K> keyType, Type<V> valueType, String table, Supplier<Connection> connection) {
        this(keyType, valueType, table, connection, Throwable::printStackTrace);
    }

    public void create() {
        try {
            StringBuilder builder = new StringBuilder();
            TypeEntry[] var2 = this.valueType.array();
            int var3 = var2.length;

            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
                TypeEntry entry = var2[var4];
                builder.append(entry.name()).append(' ').append(this.typesToString(entry.type())).append(", ");
            }

            String str = builder.toString();
            builder = new StringBuilder();
            TypeEntry[] var12 = this.keyType.array();
            var4 = var12.length;

            for(int var15 = 0; var15 < var4; ++var15) {
                TypeEntry entry = var12[var15];
                builder.append(entry.name()).append(' ').append(this.typesToString(entry.type())).append(", ");
            }

            Connection connection = (Connection)this.connection.get();
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + this.table + "(" + builder.toString() + str.substring(0, str.length() - 2) + ")");
            StringBuilder sb = new StringBuilder();
            TypeEntry[] var16 = this.keyType.array();
            int var17 = var16.length;

            for(int var7 = 0; var7 < var17; ++var7) {
                TypeEntry entry = var16[var7];
                sb.append(entry.name()).append(',');
            }

            String st = sb.toString();

            try {
                connection.createStatement().execute("CREATE INDEX key_index ON " + this.table + "(" + st.substring(0, st.length() - 1) + ")");
            } catch (SQLException var9) {
            }
        } catch (SQLException var10) {
            this.exceptions.accept(var10);
        }

    }

    public void remove(K key) {
        Connection connection = (Connection)this.connection.get();

        try(PreparedStatement statement = connection.prepareStatement(this.remove)) {
            SQLEncoder encoder = new SQLEncoder(statement, this.keyType, this.exceptions, 1);
            this.keyType.encode(encoder, key);
            statement.execute();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

    }

    public V read(K key) {
        Connection connection = this.connection.get();

        try(PreparedStatement statement = connection.prepareStatement(this.select)) {
            SQLEncoder encoder = new SQLEncoder(statement, this.keyType, this.exceptions, 1);
            this.keyType.encode(encoder, key);
            ResultSet set = statement.executeQuery();
            SQLDecoder decoder = new SQLDecoder(this.valueType, set, this.exceptions, this.keyType.size() + 1);
            return this.valueType.decode(decoder);
        } catch (SQLException var15) {
            this.exceptions.accept(var15);
            return null;
        }
    }

    public void write(K key, V value) {
        Connection connection = this.connection.get();

        try(PreparedStatement statement = connection.prepareStatement(this.write)) {
            SQLEncoder encoder = new SQLEncoder(statement, this.keyType, this.exceptions, 1);
            this.keyType.encode(encoder, key);
            encoder = new SQLEncoder(statement, this.valueType, this.exceptions, this.keyType.size() + 1);
            this.valueType.encode(encoder, value);
            statement.execute();
        } catch (SQLException var13) {
            this.exceptions.accept(var13);
        }

    }

    private String typesToString(Types type) {
        switch(type) {
            case BYTE_ARRAY:
                return "MEDIUMBLOB";
            case BOOLEAN:
                return "BOOL";
            case BYTE:
                return "TINYINT";
            case SHORT:
                return "SMALLINT";
            case INT:
                return "INT";
            case LONG:
                return "BIGINT";
            case FLOAT:
                return "FLOAT";
            case DOUBLE:
                return "DOUBLE";
            case STRING:
                return "MEDIUMTEXT";
            default:
                throw new IllegalArgumentException("Unsupported type " + type);
        }
    }
}
