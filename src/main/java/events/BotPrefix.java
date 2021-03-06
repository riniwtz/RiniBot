package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static events.BaseCommand.*;

public class BotPrefix extends ListenerAdapter {
    public static char prefix = '-';
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        // prefix
        if (command.equals(prefix + "prefix")) {
            if (commandGroup.length == 1)
                event.getChannel().sendMessage("The bot prefix is: `" + prefix + "`").queue();
            if (commandGroup.length == 2) {
                String newPrefix = commandGroup[1];
                if (checkValidPrefixLength(event, newPrefix) && checkValidNewPrefix(event, newPrefix)) {
                    setPrefix(newPrefix.charAt(0));
                    event.getChannel().sendMessage("RiniBot new prefix is: `" + prefix + "`").queue();
                }
            }
        }
    }

    private void setPrefix(char prefix) {
        BotPrefix.prefix = prefix;
    }

    private boolean checkValidNewPrefix(GuildMessageReceivedEvent event, String newPrefix) {
        // if new prefix is the same as current prefix
        if (newPrefix.charAt(0) == prefix)
            event.getChannel().sendMessage("`" + prefix + "` has already been set").queue();
        else if (hasNumber(newPrefix))
            event.getChannel().sendMessage("**[Prefix must not contain any numerical values]**").queue();
        else
            return true;
        return false;
    }

    private boolean checkValidPrefixLength(GuildMessageReceivedEvent event, String newPrefix) {
        // Character Length
        if (newPrefix.length() == 1) return true;
        event.getChannel().sendMessage("**[Single prefix character only]**").queue();
        return false;
    }
}
