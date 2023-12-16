package server;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private final String username;
    private final Integer authToken;

    private Map<Integer, Message> messages;
    private int msgID;

    public Account(String username, Integer authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messages = new HashMap<>();
        this.msgID = 1;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAuthToken() {
        return authToken;
    }

    public Map<Integer, Message> getMessages() {
        return messages;
    }

    public Integer getMsgID() {
        return msgID++;
    }

    public void addMessage(Message message) {
        messages.put(msgID, message);
    }

}
