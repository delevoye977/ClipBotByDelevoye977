
import java.io.File;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author delevoye977
 */
public class MyMessageHandler extends ListenerAdapter {

    private String MessageStartsBy = "ù";
    private AudioStorage stockeur = AudioStorage.getInstance();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //These are provided with every event in JDA
        JDA jda = event.getJDA();                       //JDA, the core of the api.
        long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        //  This could be a TextChannel, PrivateChannel, or Group!

        String msg = message.getContent();              //This returns a human readable version of the Message. Similar to
        // what you would see in the client.
        boolean bot = author.isBot();                    //This boolean is useful to determine if the User that
        // sent the Message is a BOT or not!

        writeLog(event);

        if (msg.equals(this.MessageStartsBy + "test") && !bot) {
            channel.sendMessage("1-2 1-2 This is a test.").queue();
        }
        if (msg.equals(this.MessageStartsBy + "come") && !bot) {
            VoiceChannel chanVoix = event.getMember().getVoiceState().getChannel();
            if (chanVoix != null) {
                AudioManager audioManager = event.getGuild().getAudioManager();
                if (audioManager != null) {
                    audioManager.openAudioConnection(chanVoix);

                    audioManager.setReceivingHandler(new AudioEvent());
                }
            } else {
                channel.sendMessage("You're not in a vocal channel !").queue();
            }

        }
        if (msg.equals(this.MessageStartsBy + "quit") && !bot) {
            channel.sendMessage("A+ !").queue();
            ClipBot.botDeClip.shutdownNow();
        }
        if (msg.startsWith(this.MessageStartsBy + "mlk") && !bot) {
            String nom = msg.replace(this.MessageStartsBy + "mlk", "").replaceAll(" ", "") + "Clip";

            this.stockeur.writeDatas(nom);
            channel.sendFile(new File(nom + ".mp3"), new MessageBuilder().append("Here is the clip !").build()).queue();
        }
    }

    private void writeLog(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User author = event.getAuthor();
        String msg = message.getContent();
        if (event.isFromType(ChannelType.TEXT)) //If this message was sent to a Guild TextChannel
        {
            //Because we now know that this message was sent in a Guild, we can do guild specific things
            // Note, if you don't check the ChannelType before using these methods, they might return null due
            // the message possibly not being from a Guild!

            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

            String name;
            if (message.isWebhookMessage()) {
                name = author.getName();                //If this is a Webhook message, then there is no Member associated
            } // with the User, thus we default to the author for name.
            else {
                name = member.getEffectiveName();       //This will either use the Member's nickname if they have one,
            }                                           // otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
        } else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {
            //The message was sent in a PrivateChannel.
            //In this example we don't directly use the privateChannel, however, be sure, there are uses for it!
            PrivateChannel privateChannel = event.getPrivateChannel();

            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
        } else if (event.isFromType(ChannelType.GROUP)) //If this message was sent to a Group. This is CLIENT only!
        {
            //The message was sent in a Group. It should be noted that Groups are CLIENT only.
            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";  //A group name can be null due to it being unnamed.

            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), msg);
        }
    }
}
