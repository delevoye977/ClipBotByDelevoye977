
import net.dv8tion.jda.core.audio.AudioReceiveHandler;
import net.dv8tion.jda.core.audio.CombinedAudio;
import net.dv8tion.jda.core.audio.UserAudio;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * Event called every 20ms it gets the voice datas
 *
 * @author delevoye977
 */
public class AudioEvent extends ListenerAdapter implements AudioReceiveHandler {

    AudioStorage storer;

    public AudioEvent() {
        super();
        this.storer = AudioStorage.getInstance();
        System.out.printf("Creation of the audio event");
    }

    @Override
    public boolean canReceiveCombined() {
        return true;
    }

    @Override
    public boolean canReceiveUser() {
        return false;
    }

    @Override
    public void handleUserAudio(UserAudio arg0) {
        System.out.printf("Not supported yet !");
    }

    /**
     *
     * @param combinedAudio is filled with the auto datas
     */
    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        this.storer.addData(combinedAudio.getAudioData(1.0));

    }

}
