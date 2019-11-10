package lt.liutikas.ToDoAPI.exception;

public class DuplicateEntityException extends Exception {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
