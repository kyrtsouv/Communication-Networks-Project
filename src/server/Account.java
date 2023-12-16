package server;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String username;
    private final Integer authToken;

    private List<Message> messageBox;
    private Integer msgID;

    public Account(String username, Integer authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messageBox = new ArrayList<>();
        this.msgID = 1;
    }

    public String getUsername() {
        return username;
    }

    public Integer getAuthToken() {
        return authToken;
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }

    public Integer getMsgID() {
        return msgID++;
    }

    public void addMessage(Message message) {
        messageBox.add(message);
    }

}
