package lt.liutikas.ToDoAPI.exception;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException() {
        super("person not found");
    }
}
