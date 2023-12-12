package common;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final String username;
    private final String authToken;
    private Map<String, Message> messages;
    private int msgIndex;

    public Account(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messages = new HashMap<>();
        this.msgIndex = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.put(String.valueOf(msgIndex), message);
        msgIndex++;
    }

}
