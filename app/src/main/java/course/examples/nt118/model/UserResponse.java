package course.examples.nt118.model;

public class UserResponse {
    private String message;
    private User user;

    public String getMessage() { return message; }
    public User getUser() { return user; }

    public static class User {
        private String id;
        private String name;
        private String email;
        private String avatar;

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getAvatar() { return avatar; }
    }
}

