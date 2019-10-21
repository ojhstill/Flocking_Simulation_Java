// Y3857872
package frame;

// Import Java Classes:
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Import Project Classes:
import bird.IntelligentBird;
import bird.Predator;

// GUI Class:
public class GUI {

    // GUI Class Variables:
    private JFrame guiFrame;
    private boolean radarVisible;
    private boolean vectorVisible;
    private boolean localCentreVisible;

    // GUI Buttons:
    private JButton programExitButton;
    private JButton addBirdButton;
    private JButton add10BirdsButton;
    private JButton removeBirdButton;
    private JButton clearBirdsButton;
    private JButton addPredatorButton;
    private JButton removePredatorButton;
    private JButton radiusVisibleButton;
    private JButton vectorVisibleButton;
    private JButton localCentreVisibleButton;

    // GUI Sliders:
    private JSlider birdSpeedSlider;
    private JSlider birdCohesionSlider;
    private JSlider birdSeparationSlider;
    private JSlider birdAlignmentSlider;

    // GUI Labels:
    private JLabel birdCountLabel;
    private JLabel predatorCountLabel;
    private JLabel birdSpeedLabel;
    private JLabel birdCohesionLabel;
    private JLabel birdSeparationLabel;
    private JLabel birdAlignmentLabel;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                                Constructors
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public GUI(JFrame frame){
        guiFrame = frame;
    }

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                                            GUI Class Public Methods
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // Initialise GUI Method:
    public void initGUI() {

        // UPPER CONTROL PANEL:

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout());
        upperPanel.setBackground(new Color(180,160,200));

        // Initialise 'exit' button to upper panel.
        programExitButton = new JButton("Exit");
        programExitButton.setEnabled(true);
        upperPanel.add(programExitButton);

        // Initialise 'add bird' button to upper panel.
        addBirdButton = new JButton("Add Bird");
        addBirdButton.setEnabled(true);
        upperPanel.add(addBirdButton);

        // Initialise 'add 10 birds' button to upper panel.
        add10BirdsButton = new JButton("Add 10 Birds");
        add10BirdsButton.setEnabled(true);
        upperPanel.add(add10BirdsButton);

        // Initialise 'remove bird' button to upper panel.
        removeBirdButton = new JButton("Remove Bird");
        removeBirdButton.setEnabled(false);
        upperPanel.add(removeBirdButton);

        // Initialise 'clear birds' button to upper panel.
        clearBirdsButton = new JButton("Clear All");
        clearBirdsButton.setEnabled(true);
        upperPanel.add(clearBirdsButton);

        // Initialise 'add predator' button to upper panel.
        addPredatorButton = new JButton("Add Predator");
        addPredatorButton.setEnabled(true);
        upperPanel.add(addPredatorButton);

        // Initialise 'remove predator' button to upper panel.
        removePredatorButton = new JButton("Remove Predator");
        removePredatorButton.setEnabled(false);
        upperPanel.add(removePredatorButton);

        // Initialise 'bird speed' slider to upper panel.
        birdSpeedLabel = new JLabel("Speed: 100");
        upperPanel.add(birdSpeedLabel);

        birdSpeedSlider = new JSlider(100, 900, 100);
        birdSpeedSlider.setOpaque(false);
        birdSpeedSlider.setMajorTickSpacing(400);
        birdSpeedSlider.setMinorTickSpacing(50);
        birdSpeedSlider.setPaintTicks(true);
        birdSpeedSlider.setSnapToTicks(true);
        upperPanel.add(birdSpeedSlider);

        // Add 'bird count' label to upper panel.
        birdCountLabel = new JLabel("Bird Count: 1");
        upperPanel.add(birdCountLabel);

        // Add 'predator count' label to upper panel.
        predatorCountLabel = new JLabel("Predator Count: 0");
        upperPanel.add(predatorCountLabel);

        // Initialise lower control panel.
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout());
        lowerPanel.setBackground(new Color(250,210,210));

        // Add 'cohesion' label to lower panel.
        birdCohesionLabel = new JLabel("Cohesion: 0%");
        lowerPanel.add(birdCohesionLabel);

        // Initialise 'cohesion' slider to lower panel.
        birdCohesionSlider = new JSlider(0, 100, 0);
        birdCohesionSlider.setOpaque(false);
        birdCohesionSlider.setMajorTickSpacing(50);
        birdCohesionSlider.setMinorTickSpacing(10);
        birdCohesionSlider.setPaintTicks(true);
        birdCohesionSlider.setSnapToTicks(true);
        lowerPanel.add(birdCohesionSlider);

        // Add 'separation' label to lower panel.
        birdSeparationLabel = new JLabel("Separation: 0%");
        lowerPanel.add(birdSeparationLabel);

        // Initialise 'separation' slider to lower panel.
        birdSeparationSlider = new JSlider(0, 100, 0);
        birdSeparationSlider.setOpaque(false);
        birdSeparationSlider.setMajorTickSpacing(50);
        birdSeparationSlider.setMinorTickSpacing(10);
        birdSeparationSlider.setPaintTicks(true);
        birdSeparationSlider.setSnapToTicks(true);
        lowerPanel.add(birdSeparationSlider);


        // Initialise 'alignment' label to lower panel.
        birdAlignmentLabel = new JLabel("Alignment: 0%");
        lowerPanel.add(birdAlignmentLabel);

        birdAlignmentSlider = new JSlider(0, 100, 0);
        birdAlignmentSlider.setOpaque(false);
        birdAlignmentSlider.setMajorTickSpacing(50);
        birdAlignmentSlider.setMinorTickSpacing(10);
        birdAlignmentSlider.setPaintTicks(true);
        birdAlignmentSlider.setSnapToTicks(true);
        lowerPanel.add(birdAlignmentSlider);

        // Initialise 'radar visible' button to lower panel.
        radiusVisibleButton = new JButton("Radar On");
        radiusVisibleButton.setEnabled(true);
        lowerPanel.add(radiusVisibleButton);

        // Initialise 'vector visible' button to lower panel.
        vectorVisibleButton = new JButton("Vector On");
        vectorVisibleButton.setEnabled(true);
        lowerPanel.add(vectorVisibleButton);

        // Initialise 'local centre visible' button to lower panel.
        localCentreVisibleButton = new JButton("L.Centre On");
        localCentreVisibleButton.setEnabled(true);
        lowerPanel.add(localCentreVisibleButton);

        // Add upper and lower control panels to frame.
        guiFrame.add(upperPanel, BorderLayout.NORTH);
        guiFrame.add(lowerPanel, BorderLayout.SOUTH);
    }

    // Initialise GUI Event Listeners Method:
    public void addListeners(Canvas canvas, List<IntelligentBird> birds, List<Predator> predators) {

        // DEFINE GUI COMPONENT BEHAVIOURS:

        // Define 'exit' button behaviour.
        programExitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                // Stop program and exit with code '0'.
                System.out.println("Program Ended.");
                System.exit(0);
            }
        });

        // Define 'add bird' button behaviour.
        addBirdButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                // Add new bird.
                birds.add(new IntelligentBird(canvas));

                // Initialise bird's properties.
                birds.get(birds.size() - 1).setVelocity(birdSpeedSlider.getValue());
                birds.get(birds.size() - 1).setMaxVelocity(birdSpeedSlider.getValue());
                birds.get(birds.size() - 1).setCohesion(birdCohesionSlider.getValue());
                birds.get(birds.size() - 1).setSeparation(birdSeparationSlider.getValue());
                birds.get(birds.size() - 1).setAlignment(birdAlignmentSlider.getValue());
                birds.get(birds.size() - 1).setRadius(birdSeparationSlider.getValue() + 100);
                birds.get(birds.size() - 1).setRadarVisible(radarVisible);
                birds.get(birds.size() - 1).setVectorVisible(radarVisible);
                birds.get(birds.size() - 1).setLocalCentreVisible(localCentreVisible);
            }
        });

        // Define 'add 10 birds' button behaviour.
        add10BirdsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                // Repeat 10 times:
                for (int i = 0; i < 10; i++) {

                    // Add new bird.
                    birds.add(new IntelligentBird(canvas));

                    // Initialise bird's properties.
                    birds.get(birds.size() - 1).setVelocity(birdSpeedSlider.getValue());
                    birds.get(birds.size() - 1).setMaxVelocity(birdSpeedSlider.getValue());
                    birds.get(birds.size() - 1).setCohesion(birdCohesionSlider.getValue());
                    birds.get(birds.size() - 1).setSeparation(birdSeparationSlider.getValue());
                    birds.get(birds.size() - 1).setAlignment(birdAlignmentSlider.getValue());
                    birds.get(birds.size() - 1).setRadius(birdSeparationSlider.getValue() + 100);
                    birds.get(birds.size() - 1).setRadarVisible(radarVisible);
                    birds.get(birds.size() - 1).setVectorVisible(radarVisible);
                    birds.get(birds.size() - 1).setLocalCentreVisible(localCentreVisible);
                }
            }
        });

        // Define 'remove bird' button behaviour.
        removeBirdButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                // Remove bird at end of 'birds' list.
                birds.remove(birds.size() - 1);
            }
        });

        // Define 'clear birds' button behaviour.
        clearBirdsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                // Clear all birds and predators.
                birds.clear();
                predators.clear();
            }
        });


        // Define 'add predator' button behaviour.
        addPredatorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                // Add new predator.
                predators.add(new Predator(canvas));

                // Initialise predator's properties.
                predators.get(predators.size() - 1).setVelocity(birdSpeedSlider.getValue());
                predators.get(predators.size() - 1).setMaxVelocity(birdSpeedSlider.getValue());
                predators.get(predators.size() - 1).setRadarVisible(radarVisible);
                predators.get(predators.size() - 1).setVectorVisible(vectorVisible);
                predators.get(predators.size() - 1).setLocalCentreVisible(localCentreVisible);
            }
        });

        // Define 'remove predator' button behaviour.
        removePredatorButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                // Remove predator at end of 'predators' list.
                predators.remove(predators.size() - 1);
            }
        });

        // Define 'bird speed' slider behaviour.
        birdSpeedSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                for (IntelligentBird bird : birds) {
                    bird.setVelocity(birdSpeedSlider.getValue());
                    bird.setMaxVelocity(birdSpeedSlider.getValue());
                }
                for (Predator predator : predators) {
                    predator.setVelocity(birdSpeedSlider.getValue());
                    predator.setMaxVelocity(birdSpeedSlider.getValue());
                }

                birdSpeedLabel.setText("Speed: " + birdSpeedSlider.getValue());
            }
        });

        // Define 'cohesion' slider behaviour.
        birdCohesionSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                for (IntelligentBird bird : birds)
                    bird.setCohesion(birdCohesionSlider.getValue());

                birdCohesionLabel.setText("Cohesion: " + birdCohesionSlider.getValue() + "%");
            }
        });

        // Define 'separation' slider behaviour.
        birdSeparationSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                for (IntelligentBird bird : birds) {
                    bird.setSeparation(birdSeparationSlider.getValue());
                    bird.setRadius(birdSeparationSlider.getValue() + 100);
                }

                birdSeparationLabel.setText("Separation: " + birdSeparationSlider.getValue() + "%");
            }
        });

        // Define 'alignment' slider behaviour.
        birdAlignmentSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                for (IntelligentBird bird : birds)
                    bird.setAlignment(birdAlignmentSlider.getValue());

                birdAlignmentLabel.setText("Alignment: " + birdAlignmentSlider.getValue() + "%");
            }
        });

        // Define 'radar visible' button behaviour.
        radiusVisibleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!radarVisible) {
                    for (IntelligentBird bird : birds)
                        bird.setRadarVisible(true);
                    for (Predator predator : predators)
                        predator.setRadarVisible(true);
                    radiusVisibleButton.setText("Radar Off");
                    radarVisible = true;

                } else {
                    for (IntelligentBird bird : birds)
                        bird.setRadarVisible(false);
                    for (Predator predator : predators)
                        predator.setRadarVisible(false);
                    radiusVisibleButton.setText("Radar On");
                    radarVisible = false;
                }
            }
        });


        // Define 'vector visible' button behaviour.
        vectorVisibleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!vectorVisible) {
                    for (IntelligentBird bird : birds)
                        bird.setVectorVisible(true);
                    for (Predator predator : predators)
                        predator.setVectorVisible(true);
                    vectorVisibleButton.setText("Vector Off");
                    vectorVisible = true;

                } else {
                    for (IntelligentBird bird : birds)
                        bird.setVectorVisible(false);
                    for (Predator predator : predators)
                        predator.setVectorVisible(false);
                    vectorVisibleButton.setText("Vector On");
                    vectorVisible = false;
                }
            }
        });

        // Define 'local centre visible' button behaviour.
        localCentreVisibleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!localCentreVisible) {
                    for (IntelligentBird bird : birds)
                        bird.setLocalCentreVisible(true);
                    for (Predator predator : predators)
                        predator.setLocalCentreVisible(true);
                    localCentreVisibleButton.setText("L.Centre Off");
                    localCentreVisible = true;

                } else {
                    for (IntelligentBird bird : birds)
                        bird.setLocalCentreVisible(false);
                    for (Predator predator : predators)
                        predator.setLocalCentreVisible(false);
                    localCentreVisibleButton.setText("L.Centre On");
                    localCentreVisible = false;
                }
            }
        });

        // Refresh frame.
        guiFrame.revalidate();
    }

    // Update GUI Method:
    public void updateGUI(List<IntelligentBird> birds, List<Predator> predators){

        // Update bird count if bird is eaten by an active predator.
        birdCountLabel.setText("Bird Count: " + birds.size());

        // Update predator count.
        predatorCountLabel.setText("Predator Count: " + predators.size());

        // Update JButtons.
        if (birds.size() < 200)
            addBirdButton.setEnabled(true);
        else addBirdButton.setEnabled(false);

        if (birds.size() < 191)
            add10BirdsButton.setEnabled(true);
        else add10BirdsButton.setEnabled(false);

        if (birds.size() > 0)
            removeBirdButton.setEnabled(true);
        else removeBirdButton.setEnabled(false);

        if (predators.size() < 3)
            addPredatorButton.setEnabled(true);
        else addPredatorButton.setEnabled(false);

        if (predators.size() > 0)
            removePredatorButton.setEnabled(true);
        else removePredatorButton.setEnabled(false);

        if (birds.size() == 0 && predators.size() == 0)
            clearBirdsButton.setEnabled(false);
        else clearBirdsButton.setEnabled(true);

    }
}
