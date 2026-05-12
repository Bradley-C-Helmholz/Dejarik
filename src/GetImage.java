import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GetImage {

    public BufferedImage getImage(String type){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("resources\\" + type + ".png").getAbsoluteFile());
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image not found");
        }
        return img;
    }
}
