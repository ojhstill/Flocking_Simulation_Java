// Y3857872

// Import Java Classes:
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Import Project Classes:
import bird.IntelligentBird;
import bird.Predator;
import frame.Canvas;
import frame.GUI;
import tools.Utils;
import static tools.Utils.randomWithLimits;

// Main Flocking Simulation Class:
public class FlockingSimulation extends JFrame {
	
	// Frame class variables:
	private static final long serialVersionUID = 1L;
    private static Canvas canvas;
    private static GUI gui;

    // Program class variables:
    private static List<IntelligentBird> birds;
    private static List<Predator> predators;
    private static boolean continueRunning;
    private static int flockDynamics = 0;

    // Run Program Method:
    private void runProgram() {

        System.out.println("Initialising Canvas...");
        setupCanvas();

        // Initialise lists for both the birds and predators.
        birds = Collections.synchronizedList(new ArrayList<IntelligentBird>());
        predators = Collections.synchronizedList(new ArrayList<Predator>());

        System.out.println("Initialising GUI...");
        setupGUI();

        System.out.println("Initialising Program...");
        continueRunning = true;
        gameLoop();
    }

    // Canvas Setup Method (Called within 'runProgram'):
    private void setupCanvas() {

        // Create new canvas - reference and adapted from Stuart Porter [1]
        canvas = new Canvas();

        // Initialise canvas.
        canvas.setDoubleBuffered(true);
        canvas.setOpaque(false);
        setTitle("Bird Flocking Simulator - Y3857872");

        // Set program to 'Nimbus' theme if supported, ...
        try {
            for(UIManager.LookAndFeelInfo theme : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(theme.getName())) {
                    UIManager.setLookAndFeel(theme.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // ... else ignore and use default theme.
            System.err.println("\tGUI theme not supported.");
        }

        // Fullscreen mode - reference and adapted from Michael Myers [2]
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (gd.isFullScreenSupported()) {
            // Set program to fullscreen if supported, ...
            setUndecorated(true);
            setAlwaysOnTop(true);
            gd.setFullScreenWindow(this);
        } else {
            // ... else set screen size to 1280x720.
            System.err.println("\tFull screen not supported.");
            setSize(1366, 768);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        // Add background image - reference from Ithalu Dominguez [3]
        JLabel background = new JLabel(new ImageIcon("background.jpg"));

        // Construct new main JPanel to overlay background and canvas.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        // Overlay canvas.
        mainPanel.add(canvas, BorderLayout.CENTER);
        mainPanel.add(background, BorderLayout.CENTER);

        // Add panel to frame.
        this.add(mainPanel);

        System.out.println("\tCanvas Initialised.");
    }

    // GUI Setup Method (Called within 'runProgram'):
    private void setupGUI() {

        // Create and initialise GUI.
        gui = new GUI(this);
        gui.initGUI();

        // Add event listeners to GUI components.
        gui.addListeners(canvas, birds, predators);

        System.out.println("\tGUI Initialised.");
    }

    // Game Loop Method (Called within 'runProgram'):
    private void gameLoop() {

        // Initialise a single bird with a bearing of 0 to the centre of the canvas.
        birds.add(new IntelligentBird(canvas, canvas.getWidth() / 2, canvas.getHeight() / 2, 0));

        // Set frame rate (20ms - 50Hz, 17ms ~ 60Hz).
        int deltaTime = 17;
        int frameCount = 0;

        System.out.println("\tRunning Program.");

        // Start of game loop ...
        while (continueRunning) {

            // Draw all birds and predators.
            synchronized (birds) {
                for (IntelligentBird bird : birds)
                    bird.draw();
            }
            synchronized (predators) {
                for (Predator predator : predators)
                    predator.draw();
            }

            // Freeze frame.
            Utils.pause(deltaTime);

            // Update all bird and predator behaviours, positions and bearings.
            synchronized (birds) {
                for (IntelligentBird bird : birds) {
                    // Update each bird's flocking parameters.
                    bird.setDynamics(flockDynamics);
                    bird.flock(birds, predators);
                    // Update bird's position.
                    bird.update(deltaTime);
                }
            }
            synchronized (predators) {
                for (Predator predator : predators) {
                    // Update each predator's hunting parameters.
                    predator.hunt(birds);
                    // Update predator's position.
                    predator.update(deltaTime);
                }
            }

            // Update GUI (Used to update bird count is bird is eaten).
            gui.updateGUI(birds, predators);

            // Wipe canvas clear.
            canvas.clear(); // Alternative to using 'undraw()' for each bird - increases performance.

            // Update 'flock dynamics' variable and frame count.
            if (frameCount % 240 == 0)
                flockDynamics = randomWithLimits(-50, 50);

            frameCount++;
        } /* ... end of game loop. */
    }

    // Main Method:
    public static void main(String[] args) {
        new FlockingSimulation().runProgram();
    }
}