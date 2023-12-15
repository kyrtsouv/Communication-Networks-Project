package server;

import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

public class Database {
    private Map<String, Account> usernameToAccount;
    private Map<Account, String> accountToToken;
    private Map<String, Account> tokenToAccount;

    public Database() {
        usernameToAccount = new HashMap<>();
        accountToToken = new HashMap<>();
        tokenToAccount = new HashMap<>();
    }

    public String createAccount(String username) {
        if (usernameToAccount.containsKey(username)) {
            return "Sorry, the user already exists";
        }
        if (username.length() == 0) {
            return "Invalid username";
        }
        for (char c : username.toCharArray()) {
            if (!Character.isLetter(c) && c != '_') {
                return "Invalid username";
            }
        }

        String authToken;
        do {
            authToken = UUID.randomUUID().toString();
        } while (accountToToken.containsValue(authToken));

        Account account = new Account(username, authToken);
        usernameToAccount.put(username, account);
        accountToToken.put(account, authToken);
        tokenToAccount.put(authToken, account);

        return authToken;
    }

    public String showAccounts(String authToken) {
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

    public String sendMessage(String authToken, String recipient, String msgBody) {
        if (!tokenToAccount.containsKey(authToken)) {
            return "Invalid Auth Token";
        }
        if (!usernameToAccount.containsKey(recipient)) {
            return "User does not exist";
        }
        Account sender = tokenToAccount.get(authToken);
        Account receiver = usernameToAccount.get(recipient);
        Message message = new Message(sender.getUsername(), receiver.getUsername(), msgBody);

        receiver.addMessage(message);
        return "OK";
    }

    public String showInbox(String authToken) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        String response = "";
        Map<String, Message> messages = account.getMessages();
        for (String msgIndex : messages.keySet()) {
            Message msg = messages.get(msgIndex);
            response += msgIndex + ". " + "from: " + msg.getSender() + (!msg.isRead() ? "*" : "") + "\n";
        }
        return response;
    }

    public String readMessage(String authToken, String msgID) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        Map<String, Message> messages = account.getMessages();
        if (!messages.containsKey(msgID)) {
            return "Message ID does not exist";
        }
        Message msg = messages.get(msgID);
        msg.setRead();
        return "(" + msg.getSender() + ") " + msg.getBody();
    }

    public String deleteMessage(String authToken, String msgID) {
        if (!accountToToken.containsValue(authToken)) {
            return "Invalid Auth Token";
        }
        Account account = tokenToAccount.get(authToken);
        Map<String, Message> messages = account.getMessages();
        if (!messages.containsKey(msgID)) {
            return "Message does not exist";
        }
        messages.remove(msgID);
        return "OK";
    }
}
