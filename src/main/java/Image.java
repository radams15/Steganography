import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Image {
    File file;
    public BufferedImage img;

    final int maxTextSize = 255*3;

    final int inc = 16;

    private List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    public Image(String url){
        file = new File(url);
        try {
            img = ImageIO.read(file);
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    public char changeChar(char chr, boolean encoding){
        if(encoding) {
            chr ^= 2;
        }else{
            chr ^= 2;
        }

        return chr;
    }

    public void addText(String text){
        int i=0;
        int width = img.getWidth();
        int height = img.getHeight();

        if(text.length() == 0){ // no text
            return;
        }

        if(text.length() > maxTextSize){
            text = text.substring(0, maxTextSize);
        }

        while(text.length() % 3 != 0){ // we need the string top equally divide by 3
            text += " ";
        }

        List<String> strings = splitEqually(text, text.length()/3);

        img.setRGB( 0, 0, new Color(strings.get(0).length(), strings.get(1).length(), strings.get(2).length()) .getRGB() );

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(x%inc == 0 && y%inc == 0 && x != 0 && y != 0){
                    if( i > strings.get(0).length()-1 ){
                        break;
                    }
                    Color colour = new Color(changeChar(strings.get(0).charAt(i), true), changeChar(strings.get(1).charAt(i), true), changeChar(strings.get(2).charAt(i), true));
                    img.setRGB(x, y, colour.getRGB());
                    i ++;
                }
            }
        }
    }

    String getText(){
        String red = "";
        String green = "";
        String blue = "";

        int i=0;
        int width = img.getWidth();
        int height = img.getHeight();

        int subStrLength = new Color(img.getRGB(0, 0)).getBlue();// the length of the string segment. could be red, green or blue as they're the same

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(x%inc == 0 && y%inc == 0 && x != 0 && y != 0){
                    if( i > subStrLength-1 ){
                        break;
                    }
                    Color colour = new Color(img.getRGB(x, y));

                    red += changeChar((char)colour.getRed(), false);
                    green += changeChar((char)colour.getGreen(), false);
                    blue += changeChar((char)colour.getBlue(), false);

                    i ++;
                }
            }
        }

        return red+green+blue;
    }

    public void save(String fileName){
        try{
            ImageIO.write(img, "png", new File(fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
