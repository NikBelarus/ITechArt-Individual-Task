package command;

import command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<CommandType, Command> commandMap = new HashMap<>();

    public CommandFactory(){
        commandMap.put(CommandType.GET_PHOTO, new GetStandartPhoto());
        commandMap.put(CommandType.GET_CONTACT, new GetContact());
        commandMap.put(CommandType.POST_CONTACT, new AddContact());
        commandMap.put(CommandType.PUT_CONTACT, new EditContact());
        commandMap.put(CommandType.POST_EMAIL, new SendEmail());
        commandMap.put(CommandType.GET_CONTACTS, new ShowAllContacts());
        commandMap.put(CommandType.POST_CONTACTS, new ShowContactsByIds());
        commandMap.put(CommandType.DELETE_CONTACTS, new DeleteContacts());
        commandMap.put(CommandType.POST_CONTACTS_IDS, new SearchContacts());
        commandMap.put(CommandType.POST_ATTACHMENT, new DownloadAttachment());
        commandMap.put(CommandType.POST_EMAILS, new GetEmails());
    }

    public Command createCommand(String commandName) {
        return commandMap.get(CommandType.valueOf(commandName));
    }
}
