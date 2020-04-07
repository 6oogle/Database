package oogle.util.type;

public class TypeEntry {
    private final String name;
    private final Types type;

    public TypeEntry(String name, Types type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return this.name;
    }

    public Types type() {
        return this.type;
    }

    @Override
    public boolean equals(Object object) {
        return object == this || (object instanceof TypeEntry && ((TypeEntry) object).type == this.type);
    }
}
