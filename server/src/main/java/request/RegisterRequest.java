package request;

public record RegisterRequest(String username, String password, String email) {
    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String email() {
        return email;
    }
}
