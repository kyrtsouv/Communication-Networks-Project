package common;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String username;
    private final Integer authToken;
    private List<Message> messages;

    public Account(String username, Integer authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messages = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public Integer getAuthToken() {
        return authToken;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
