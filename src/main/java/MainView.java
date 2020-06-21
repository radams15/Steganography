import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainView {
    public JPanel mainPanel;
    private JTextField hiddenTextField;
    private JButton refreshButton;
    private JButton loadButton;
    private JButton saveButton;
    private JLabel imageView;

    Image image = null;

    public void showImage(BufferedImage img) {
        java.awt.Image img2 = img.getScaledInstance(imageView.getWidth(), imageView.getHeight(), 1);
        imageView.setIcon(new ImageIcon(img2));
    }

    public void openFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Load File");
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setCurrentDirectory(workingDirectory);
        int returnVal = fc.showOpenDialog(mainPanel);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            image = new Image(file.getAbsolutePath());
            showImage(image.img);
            System.out.println(image.file);
            try{
                String data = image.getText();
                hiddenTextField.setText(data);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void saveFile(){
        if(image == null){
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save File");

        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);

        int userSelection = fileChooser.showSaveDialog(mainPanel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            image.save(fileToSave.getAbsolutePath());
        }
    }

    public void refreshImage(){
        if(image == null){
            return;
        }
        String toHide = hiddenTextField.getText();

        image.addText(toHide);

        showImage(image.img);
    }

    public MainView() {
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshImage();
                saveFile();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshImage();
            }
        });
    }

}
