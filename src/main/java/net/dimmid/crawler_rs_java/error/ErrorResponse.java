package net.dimmid.crawler_rs_java.error;

public record ErrorResponse(int statusCode, String message, String path, String date) {
    public ErrorResponse(int statusCode, String message, String path, String date) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
        this.date = date;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String message() {
        return this.message;
    }

    public String path() {
        return this.path;
    }

    public String date() {
        return this.date;
    }
}
