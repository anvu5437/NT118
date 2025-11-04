package course.examples.nt118.model;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String avatar;

    public RegisterRequest(String name, String email, String password, String avatar) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }
}
