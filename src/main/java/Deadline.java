public class Deadline extends Todo {
    private String end;

    public Deadline(String name, String end) {
        super(name);
        this.end = end;
    }

    public String End() {
        return this.end;
    }

    @Override
    public String toString() {
        String line = Name(this) + "(by: " + this.end + ")";
        return line;
    }
}
