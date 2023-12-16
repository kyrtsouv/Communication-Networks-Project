package server;

import java.util.Map;
import java.util.HashMap;

public class Database {
    private Map<String, Account> usernameToAccount;
    private Map<Account, Integer> accountToToken;
    private Map<Integer, Account> tokenToAccount;

    private Integer nextUserAuthToken;

    public Database() {
        usernameToAccount = new HashMap<>();
        accountToToken = new HashMap<>();
        tokenToAccount = new HashMap<>();
        nextUserAuthToken = 1;
    }

    private Integer getAuthToken() {
        return nextUserAuthToken++;
    }

    public String createAccount(String username) {
        if (usernameToAccount.containsKey(username)) {
            return "Sorry, the user already exists";
        }
        if (username.length() == 0) {
            return "Invalid username";
        }
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return "Invalid username";
            }
        }

        Integer authToken = getAuthToken();
        Account account = new Account(username, authToken);
        usernameToAccount.put(username, account);
        accountToToken.put(account, authToken);
        tokenToAccount.put(authToken, account);

        return String.valueOf(authToken);
    }

    public String showAccounts(Integer authToken) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        String response = "";
        int index = 1;
        for (Account account : accountToToken.keySet()) {
            response += index + ". " + account.getUsername() + "\n";
            index++;
        }
        return response;
    }

    public String sendMessage(Integer authToken, String recipient, String msgBody) {
        if (!tokenToAccount.containsKey(authToken)) {
            return "Invalid Auth Token";
        }
        if (!usernameToAccount.containsKey(recipient)) {
            return "User does not exist";
        }
        Account sender = tokenToAccount.get(authToken);
        Account receiver = usernameToAccount.get(recipient);
        Message message = new Message(sender.getUsername(), receiver.getUsername(), msgBody, receiver.getMsgID());

        receiver.addMessage(message);
        return "OK";
    }

    public String showInbox(Integer authToken) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        String response = "";
        Map<Integer, Message> messages = account.getMessages();
        for (Integer msgIndex : messages.keySet()) {
            Message msg = messages.get(msgIndex);
            response += msgIndex + ". " + "from: " + msg.getSender() + (!msg.isRead() ? "*" : "") + "\n";
        }
        return response;
    }

    public String readMessage(Integer authToken, Integer msgID) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        Map<Integer, Message> messages = account.getMessages();
        if (!messages.containsKey(msgID)) {
            return "Message ID does not exist";
        }
        Message msg = messages.get(msgID);
        msg.setRead();
        return "(" + msg.getSender() + ") " + msg.getBody();
    }

    public String deleteMessage(Integer authToken, Integer msgID) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        Map<Integer, Message> messages = account.getMessages();
        if (!messages.containsKey(msgID)) {
            return "Message does not exist";
        }
        messages.remove(msgID);
        return "OK";
    }
}
