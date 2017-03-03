package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Laurindo
 */
public class JoKenPo {

    private static final int ROCK = 0, PAPER = 1, SCISSORS = 2;

    private int player = 0, machine = 0;
    private final ImageIcon iconStandRight = new ImageIcon(getClass().getResource("/resources/stand_right.png")),
            iconStandLeft = new ImageIcon(getClass().getResource("/resources/stand_left.png")),
            iconRockRight = new ImageIcon(getClass().getResource("/resources/rock_right.png")),
            iconRockLeft = new ImageIcon(getClass().getResource("/resources/rock_left.png")),
            iconPaperRight = new ImageIcon(getClass().getResource("/resources/paper_right.png")),
            iconPaperLeft = new ImageIcon(getClass().getResource("/resources/paper_left.png")),
            iconScissorsRight = new ImageIcon(getClass().getResource("/resources/scissors_right.png")),
            iconScissorsLeft = new ImageIcon(getClass().getResource("/resources/scissors_left.png"));

    private JPanel mainPanel;
    private JFrame window;
    private JPanel panelLeft, panelButtons;
    private JLabel labelRight;
    private JLabel labelLeft;
    private JLabel labelRock, labelPaper, labelScissors, labelPlayerScore, labelMachineScore;
    private JPanel panelRight;
    private JPanel scoreBoard;

    public JoKenPo() {
        initialize();
    }

    private void initialize() {

        labelLeft = new JLabel(iconStandLeft);
        labelRight = new JLabel(iconStandRight);

        labelRock = new JLabel(iconRockLeft);
        labelRock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (labelRock.isEnabled()) {
                    play(ROCK);
                }
            }

        });
        labelPaper = new JLabel(iconPaperLeft);
        labelPaper.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (labelPaper.isEnabled()) {
                    play(PAPER);
                }
            }

        });
        labelScissors = new JLabel(iconScissorsLeft);
        labelScissors.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (labelScissors.isEnabled()) {
                    play(SCISSORS);
                }
            }

        });

        labelPlayerScore = new JLabel("You: 0");
        labelMachineScore = new JLabel("Me: 0");

        panelButtons = new JPanel(new BorderLayout());
        panelButtons.add(labelRock, BorderLayout.NORTH);
        panelButtons.add(labelPaper, BorderLayout.CENTER);
        panelButtons.add(labelScissors, BorderLayout.SOUTH);

        scoreBoard = new JPanel(new BorderLayout());
        scoreBoard.add(labelPlayerScore, BorderLayout.NORTH);
        scoreBoard.add(labelMachineScore, BorderLayout.SOUTH);

        panelLeft = new JPanel(new BorderLayout());
        panelLeft.add(panelButtons, BorderLayout.WEST);
        panelLeft.add(labelLeft, BorderLayout.CENTER);

        panelRight = new JPanel(new BorderLayout());
        panelRight.add(labelRight, BorderLayout.WEST);
        panelRight.add(scoreBoard, BorderLayout.CENTER);

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(panelLeft);
        mainPanel.add(panelRight);

        window = new JFrame();
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.add(mainPanel, BorderLayout.CENTER);
//        window.add(scoreBoard, BorderLayout.SOUTH);

    }

    private void play(final int move) {

        new Thread("play") {
            @Override
            public void run() {
                labelRock.setEnabled(false);
                labelPaper.setEnabled(false);
                labelScissors.setEnabled(false);
                try {
                    int machineMove = ThreadLocalRandom.current().nextInt(3);
                    //Do the "playing" animation
                    // JO
                    labelLeft.setIcon(iconRockLeft);
                    labelRight.setIcon(iconRockRight);
                    Thread.sleep(250);
                    labelLeft.setIcon(iconStandLeft);
                    labelRight.setIcon(iconStandRight);
                    Thread.sleep(250);
                    // KEN
                    labelLeft.setIcon(iconRockLeft);
                    labelRight.setIcon(iconRockRight);
                    Thread.sleep(250);
                    labelLeft.setIcon(iconStandLeft);
                    labelRight.setIcon(iconStandRight);
                    Thread.sleep(250);

                    // PO!
                    if (move == ROCK && machineMove == ROCK) {
                        labelLeft.setIcon(iconRockLeft);
                        labelRight.setIcon(iconRockRight);
                    } else if (move == ROCK && machineMove == PAPER) {
                        labelLeft.setIcon(iconRockLeft);
                        labelRight.setIcon(iconPaperRight);
                        machine++;
                    } else if (move == ROCK && machineMove == SCISSORS) {
                        labelLeft.setIcon(iconRockLeft);
                        labelRight.setIcon(iconScissorsRight);
                        player++;
                    } else if (move == PAPER && machineMove == ROCK) {
                        labelLeft.setIcon(iconPaperLeft);
                        labelRight.setIcon(iconRockRight);
                        player++;
                    } else if (move == PAPER && machineMove == PAPER) {
                        labelLeft.setIcon(iconPaperLeft);
                        labelRight.setIcon(iconPaperRight);
                    } else if (move == PAPER && machineMove == SCISSORS) {
                        labelLeft.setIcon(iconPaperLeft);
                        labelRight.setIcon(iconScissorsRight);
                        machine++;
                    } else if (move == SCISSORS && machineMove == ROCK) {
                        labelLeft.setIcon(iconScissorsLeft);
                        labelRight.setIcon(iconRockRight);
                        machine++;
                    } else if (move == SCISSORS && machineMove == PAPER) {
                        labelLeft.setIcon(iconScissorsLeft);
                        labelRight.setIcon(iconPaperRight);
                        player++;
                    } else if (move == SCISSORS && machineMove == SCISSORS) {
                        labelLeft.setIcon(iconScissorsLeft);
                        labelRight.setIcon(iconScissorsRight);
                    }

                    labelPlayerScore.setText("You: " + player);
                    labelMachineScore.setText("Me: " + machine);

                    Thread.sleep(600);
                    labelLeft.setIcon(iconStandLeft);
                    labelRight.setIcon(iconStandRight);
        window.pack();
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    labelRock.setEnabled(true);
                    labelPaper.setEnabled(true);
                    labelScissors.setEnabled(true);
                }
            }
        }.start();
    }

    private void run() {
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        JoKenPo t = new JoKenPo();
        t.run();
    }

}
