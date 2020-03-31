package oogle.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SQLConnection implements Supplier<Connection> {
    private final String url;
    private final String user;
    private final String pass;
    private final Consumer<SQLException> error;
    private Connection connection;

    public SQLConnection(String url, String user, String pass, Consumer<SQLException> error) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.error = error;
    }

    public SQLConnection(String url, String user, String pass) {
        this(url, user, pass, Throwable::printStackTrace);
    }

    public Connection get() {
        if (this.connection == null) {
            this.connect();
        }

        try {
            if (this.connection.isClosed()) {
                this.connect();
            }
        } catch (SQLException var2) {
            this.connect();
        }

        return this.connection;
    }

    private void connect() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException var2) {
            this.error.accept(var2);
        }

    }
}
