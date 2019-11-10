package lt.liutikas.ToDoAPI.exception;

public class DuplicatePersonException extends Exception {
    public DuplicatePersonException(String message) {
        super(message);
    }
}
