package bloodbankdonationsystem;

import java.awt.Font;
import java.io.InputStream;

public class fonts {

    public static void main(String[] args) {
        // Load the font file
        InputStream is = fonts.class.getResourceAsStream("../resources/fonts/Metropolis-Regular.otf");

        try {
            // Create a Font object from the InputStream
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);

            // Use the customFont in your JavaFX or Swing application
            // For example:
            // yourLabel.setFont(customFont.deriveFont(Font.PLAIN, 12));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

