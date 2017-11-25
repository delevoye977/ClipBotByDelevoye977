
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

public class ClipBot {

    public static JDA botDeClip;

    public static void main(String[] args) {

        String token = "SET-YOUR-TOKEN";
        try {
            botDeClip = new JDABuilder(AccountType.BOT)
                    .setToken(token) //The token of the account that is logging in.
                    .addEventListener(new MyMessageHandler()) //An instance of a class that will handle events.
                    .buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.

        } catch (LoginException e) {
            //If anything goes wrong in terms of authentication, this is the exception that will represent it
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }

}
