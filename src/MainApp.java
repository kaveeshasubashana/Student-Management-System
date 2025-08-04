// src/MainApp.java
import view.LoginFrame; // Import the LoginFrame class from the view package
import javax.swing.SwingUtilities; // Import SwingUtilities for GUI thread management

// MainApp is the entry point of the Student Management System application.
public class MainApp {

    // The main method is where the application execution begins.
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures that the GUI creation and updates
        // are performed on the Event Dispatch Thread (EDT).
        // This is crucial for Swing applications to maintain thread safety and responsiveness.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of the LoginFrame
                LoginFrame loginFrame = new LoginFrame();
                // Make the login frame visible to the user
                loginFrame.setVisible(true);

            
            }
        });


    }
}
