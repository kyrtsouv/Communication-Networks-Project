package server;

public class Message implements java.io.Serializable {

    private final String sender;
    private final String receiver;
    private final String body;
    private final String msgID;

    private boolean isRead;

    public Message(String sender, String receiver, String body, Integer msgID) {
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.msgID = String.valueOf(msgID);

        this.isRead = false;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public String getMsgID() {
        return msgID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead() {
        isRead = true;
    }
}
