package server;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

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

        Integer authToken = nextUserAuthToken++;
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
        List<Message> messages = account.getMessageBox();
        for (Message msg : messages) {
            response += msg.getID() + ". " + "from: " + msg.getSender() + (!msg.isRead() ? "*" : "") + "\n";
        }
        return response;
    }

    public String readMessage(Integer authToken, Integer msgID) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        List<Message> messages = account.getMessageBox();
        for (Message msg : messages) {
            if (msg.getID().equals(msgID)) {
                msg.setRead();
                return "(" + msg.getSender() + ") " + msg.getBody();
            }
        }
        return "Message ID does not exist";
    }

    public String deleteMessage(Integer authToken, Integer msgID) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        List<Message> messages = account.getMessageBox();
        for (Message msg : messages) {
            if (msg.getID().equals(msgID)) {
                messages.remove(msg);
                return "OK";
            }
        }
        return "Message does not exist";
    }
}
