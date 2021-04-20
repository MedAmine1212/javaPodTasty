/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import jaco.mp3.player.MP3Player;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author khail
 */
public class LoadAudio extends Thread{
    private Clip line = null;
    public static LoadAudio instance;
    private MP3Player mp3Player;
    private boolean isMp3;
    private String audioUrl;
    public static LoadAudio getInstance() {
        if(instance == null){
            instance = new LoadAudio();
        }
        return instance;
    }
    
    public static void destroyInstance() {
        instance = null;
        System.out.println("audioLoader destroyed");
    }
    
    @Override
    public void run(){
        if  (!audioUrl.contains("mp3")) {
            isMp3= false;
         try{
         URL url = new URL("http://127.0.0.1:8000/Files/podcastFiles/"+audioUrl);
          HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            InputStream bufferedIn = new BufferedInputStream(httpcon.getInputStream());
            AudioInputStream in = AudioSystem.getAudioInputStream(bufferedIn);
            line = AudioSystem.getClip();
            line.open(in);
          

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        } else {
            
            isMp3= true;
            try {
                mp3Player = new MP3Player(new URL("http://127.0.0.1:8000/Files/podcastFiles/"+audioUrl));
            } catch (MalformedURLException ex) {
                Logger.getLogger(LoadAudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void main(String[] args) {
        instance.start();
    }
    
    public void startAudio() {
        // Start
        if(!isMp3 && line!=null) {
            line.start();
        } else if(mp3Player!= null){
            mp3Player.play();
        }
    }
    public void pauseAudio() {
        if(!isMp3 && line!=null) {
            line.stop();
        } else if(mp3Player!= null){
            mp3Player.pause();
        }
    }
    
    public void stopAudio() throws IOException {
        if(!isMp3 && line!=null) {
            line.stop();
            line.close();
        } else if(mp3Player!= null){
            mp3Player.stop();
            mp3Player = null;
        }
    }
    
    public void setAudioUrl(String url) {
        audioUrl = url;
    }
    
    public void repeat() throws IOException {
        if(isMp3 && mp3Player!= null) {
            mp3Player.stop();
            mp3Player.play();
            } else if (line!=null) {
            line.setMicrosecondPosition(0);
            line.start();
            }
    }
}
