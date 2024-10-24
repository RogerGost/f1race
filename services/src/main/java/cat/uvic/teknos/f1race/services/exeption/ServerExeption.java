package cat.uvic.teknos.f1race.services.exeption;

public class ServerExeption extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ServerExeption() {
    }

    public ServerExeption(String message) {
        super(message);
    }

    public ServerExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerExeption(Throwable cause) {
        super(cause);
    }

    public ServerExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
