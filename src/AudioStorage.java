/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Singleton class
 * Used to store the datas but in a intelligent way
 * @author delevoye977
 */
public class AudioStorage {
    
    
    //Size of the datas = time to remember when recording
    //take care, the time also increases the memory needed (dans you can only send 8MB files on Discord)
    private final int NUMBER_OF_SECONDS_TO_RECORD = 60; //approximately
    private final int size = 49*NUMBER_OF_SECONDS_TO_RECORD;// 49 = 1sec
    
    //Used to remember where to write and from where to read
    private int CurrentIndex = 0;
    
    //stores the datas
    private byte[][] listeData = new byte[size][3840];
      
    //Writer into a PCM file
    Writer ecriveur;
    
    //Singleton
    private static AudioStorage INSTANCE = null;
    
    /**
     * Constructor of the class used to store the datas but in a intelligent way
     */
    private AudioStorage(){
        System.out.printf("Creation of a storer");
    }

    

    /**
     * 
     * @return an instance of the class
     */
    public static AudioStorage getInstance()
    {			
            if (INSTANCE == null)
            { 	INSTANCE = new AudioStorage();	
            }
            return INSTANCE;
    }
    
    /**
     * add the datas in the storage
     * @param data datas to store
     */
    public void addData(byte[] data){
        this.listeData[CurrentIndex] = data;
        CurrentIndex = (CurrentIndex + 1) % size;
    }
    
    /**
     * Write the datas into a PCM file the convert it into WAV and then MP3
     * @param target name of the files
     */
    public void writeDatas(String target){
        String source = target+".pcm";
        this.ecriveur = new Writer(target+".pcm");
        
        
        System.out.println("Encoding...");
        //datas are read starting from the oldest one to the newest ones
        for (int i = CurrentIndex; i < size; i++){
            ecriveur.write(listeData[i]);
        }
        for (int i = 0; i < CurrentIndex; i++){
            ecriveur.write(listeData[i]);
        }
        ecriveur.forceFlush();
        System.out.println("Decoding...");
        
        PCMtoMP3.getInstance().decode(source, target);
        System.out.println("Finished !");
    }
    
}
