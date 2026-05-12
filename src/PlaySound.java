import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/** This class allows for one to play a .wav audio file if it is located
 *  in the same folder / desktop of the .jar files.
 *  Sounds and wav files will not be located if the .jar and .wav files are in a zip file.
 *  The folder must be extracted before using the program so that the sounds will play
 *
 *  Initially written by
 *  @author Vishal Garg from geeksforgeeks.org/play-audio-file-using-java/
 *
 *  With major modifications / simplification by Bradley Helmholz for Dejarik
 *  @version 1.0 Nov 2023
 */
public class PlaySound
{
    private Clip clip;
    private AudioInputStream audioInputStream;

    // constructor to initialize streams and clip
    public PlaySound(String path)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File("resources\\" + path).getAbsoluteFile());
        System.out.println(new File(path).getAbsoluteFile());
        clip = AudioSystem.getClip(); // create clip reference
        clip.open(audioInputStream); // open audioInputStream to the clip
        clip.start(); // Plays the clip
    }

}
