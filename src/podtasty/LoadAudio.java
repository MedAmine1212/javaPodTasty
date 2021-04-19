/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author khail
 */
public class LoadAudio extends Thread{
    private SourceDataLine line = null;
    private AudioInputStream din = null;
    public static LoadAudio instance;
    private boolean play = false;
    private byte[] data;
    private int nBytesRead;
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
         try{      
         URL url = new URL("http://127.0.0.1:8000/Files/podcastFiles/1.mp3");
          HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            InputStream bufferedIn = new BufferedInputStream(httpcon.getInputStream());
            AudioInputStream in = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            if(line != null) {
                line.start();
                line.open(decodedFormat);
                data = new byte[4096];
                while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                   while (!play) {
                       System.out.println("stopped");
                   }
                        line.write(data, 0, nBytesRead);
                }
            }

        }
        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        instance.start();
    }
    
    public void startAudio() {
        // Start
        play = true;
        line.start();
    }
    public void pauseAudio() {
        play = false;
        line.stop();
    }
    
    public void stopAudio() throws IOException {
        line.stop();
        line.close();
        din.close();
    }
}
