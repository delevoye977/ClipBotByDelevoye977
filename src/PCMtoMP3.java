
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Singleton class converts PCm files to WAV then MP3
 *
 * @author delevoye977
 */
public class PCMtoMP3 {

    private PCMtoMP3() {
    }

    private static PCMtoMP3 INSTANCE = null;

    /**
     *
     * @return instance of the class PCMtoMP3
     */
    public static PCMtoMP3 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PCMtoMP3();
        }
        return INSTANCE;
    }

    /**
     * transcode PCM into WAV into MP3
     *
     * @param source name of PCM file
     * @param sortie name of MP3 file
     */
    public void decode(String source, String sortie) {
        //ffmpeg -f s16be -ar 48.0k -ac 2 -i vocal.pcm file.wav
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("ffmpeg -y -f s16be -ar 48.0k -ac 2 -i " + source + " " + sortie + ".wav");
            //eats all the outputs of the process
            new Thread() {
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                // Traitement du flux de sortie de l'application si besoin est
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }.start();

            //eats all the outputs of the process
            new Thread() {
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                // Traitement du flux d'erreur de l'application si besoin est
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }.start();
            Thread.sleep(4000);

            System.out.println("Vers mp3...");
            //ffmpeg -i input.wav -vn -ar 44100 -ac 2 -ab 192k -f mp3 output.mp3
            Process process2 = runtime.exec("ffmpeg -y -i " + sortie + ".wav -vn -ar 48000 -ac 2 -ab 192k -f mp3 " + sortie + ".mp3");
            //eats all the outputs of the process
            new Thread() {
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process2.getInputStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }.start();

            //eats all the outputs of the process
            new Thread() {
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process2.getErrorStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                            }
                        } finally {
                            reader.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }.start();
            Thread.sleep(4000);

        } catch (IOException ex) {
            Logger.getLogger(PCMtoMP3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PCMtoMP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
