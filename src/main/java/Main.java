import javax.swing.*;

import java.awt.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String args[]){
        new Main().init(args);
    }

    String guiTheme = UIManager.getSystemLookAndFeelClassName();

    MainView mainView;

    public void gui(){
        try {
            if(guiTheme.equals("javax.swing.plaf.metal.MetalLookAndFeel")){
                guiTheme = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            }
            try {
                UIManager.setLookAndFeel(guiTheme);
            }catch(UnsupportedLookAndFeelException e){
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
        }catch (Exception e){
            e.printStackTrace();
            exit(1);
        }
        JFrame frame = new JFrame("Stenography");
        mainView = new MainView();
        frame.setContentPane(mainView.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(900, 500));
        frame.pack();
        frame.setVisible(true);
    }

    public void init(String args[]){

        if(args.length >= 1){
            String command = args[0];
            if(command.toLowerCase().equals("help")){
                System.out.println("Stenography:\n\tUsage:\n\t\tEncode: java -jar Stenography.jar encode FILE TEXT OUT\n\t\tDecode: java -jar Stenography.jar decode FILE");
            }else if(command.toLowerCase().equals("encode")){ // add text to image
                String file = args[1];
                Image image = new Image(file);
                String text = args[2];
                String out = args[3];
                image.addText(text);

                image.save(out);

            }else if(command.toLowerCase().equals("decode")){ // view image text
                String file = args[1];
                Image image = new Image(file);
                String text = image.getText();
                System.out.println(text);
            }
        }else{
            gui();
        }
    }
}
