
//package com.billthecomputerguy.MineField;
/**
* Chapter 14 Game	Zone 1b on P-703
* Filename:	MineField.java
* Written by:	William Loring
* Written on:	10-31-2010
* Revised:		05-29-2011
* Simple	java Minefield	game
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.BufferedImage; // Needed	for the Jframe	icon image
import javax.imageio.ImageIO; // Get the	image	from a file
import java.io.IOException; //	Handles exception	if	the image can't be loaded

public class MineField extends JFrame implements MouseListener, ActionListener {
	private static final long serialVersionUID = 1L;
	// Keep track of game statistics for the player
	private int gamesWon = 0;
	private int totalGames = 0;

	private int minesClicked = 0; // Track how many panels we clicked
	private int mine; // Holds the panel number of the mine
	private Random randomMine = new Random(); // Create a random object to generate random mines

	// How many clicks it takes to win
	// The difficulty of the game is controlled by the WIN variable
	// If we change WIN, we change the difficulty of the game
	private int WIN = 10;
	private final int ROWS = 4;
	private final int COLS = 5;
	private final int GAP = 4;

	// NUM is initially set to 20, but we could change the ROWS and COLS to make a
	// bigger game
	private final int NUM = ROWS * COLS;

	// Create an array of JPanel's for the grid
	private JPanel[] panel = new JPanel[NUM];
	// Create a lable for the Mine label
	private JLabel mineLabel = new JLabel("* MINE *");

	// Create Menus and MenuItems
	private JMenuItem exit = new JMenuItem("Exit");
	private JRadioButtonMenuItem radio1 = new JRadioButtonMenuItem("Easy: 5 squares to win");
	private JRadioButtonMenuItem radio2 = new JRadioButtonMenuItem("Medium: 10 squares to win", true);
	private JRadioButtonMenuItem radio3 = new JRadioButtonMenuItem("Diffucult: 15 squares to win");
	private JMenuItem newGameMenuItem = new JMenuItem("New Game");
	private JMenuItem about = new JMenuItem("About");

	// Display game statistics for the player
	private JTextField txtWins = new JTextField("Wins: 0 out of 0", 10);
	private JTextField txtHitRate = new JTextField(" 0%", 4);
	private JTextField txtMine = new JTextField();

	/**********************************************************************
	 * Constructor
	 */
	public MineField() {
		setLayout(new BorderLayout());
		setTitle("Mine Field");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		composeMenus();
		addActionListeners();
		layoutComponents();
		// Generate random integer from 0 - 19, NUM is upper bound, not included
		// If NUM is changed, the range for the random number is changed
		// This is where the mine is hidden
		mine = randomMine.nextInt(NUM);
		txtMine.setText("Mine: " + mine);
	}

	/**********************************************************************
	 * Main decision point, what panel did we click?
	 */
	public void mouseClicked(MouseEvent e) {
		minesClicked++; // Track how many times we have clicked
		Object source = e.getSource(); // Get the pane object we clicked

		// Go through each panel, find the one we clicked
		for (int x = 0; x < NUM; ++x) {
			if (source == panel[x]) { // We clicked this panel

				if (minesClicked == WIN) { // We clicked WIN amount of times

					// We hit the mine and lost
					if (mine == x) {
						panel[x].setBackground(Color.RED);// Set the mine panel red
						panel[x].add(mineLabel); // Add the word * MINE * to the label
						panel[x].validate(); // Re layout the component
						repaint(); // Redraw the program to ensure we see everything

						// Allow the user to choose to play again, or exit the game
						int answer = JOptionPane.showConfirmDialog(null,
								"You hit the mine, sorry you lost.\nPlay again?", "Play again?",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						// If the user clicks Yes
						if (answer == JOptionPane.YES_OPTION) {
							totalGames++; // Track number of games played
							panel[x].remove(mineLabel); // Remove the mine label
							panel[x].validate(); // Re layout the component
							playAgain(); // Reset the gameboard
						} else
							System.exit(0); // Quit the program
					}

					// We missed the mine and won
					else {
						panel[x].setBackground(Color.WHITE); // Set the panel white that we clicked
						repaint(); // Redraws the screen to make sure we see everything

						// Allow the user to choose to play again, or exit the game
						int answer = JOptionPane.showConfirmDialog(null, "You won!\nPlay again?", "Play again?",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						// If the user clicks Yes
						if (answer == JOptionPane.YES_OPTION) {
							totalGames++; // Increment the number of games played
							gamesWon++; // Increment the number of games won
							playAgain(); // Reset the gamboard
						} else
							System.exit(0); // Quit the program
					}
				}

				// We hit the mine and lost
				else if (mine == x) {
					panel[x].setBackground(Color.RED); // Set the mine panel red
					panel[x].add(mineLabel); // Add the word * MINE * to the label
					panel[x].validate(); // Re layout the component
					repaint(); // Redraw the program to ensure we see everything

					// Ask the the user to choose to play again, or exit the game
					int answer = JOptionPane.showConfirmDialog(null, "You hit the mine, sorry you lost.\nPlay again?",
							"Play again?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					// If the user clicks Yes
					if (answer == JOptionPane.YES_OPTION) {
						totalGames++; // Track number of games played
						panel[x].remove(mineLabel); // Remove the mine label
						panel[x].validate(); // Re layout the component
						playAgain(); // Reset the gameboard
					} else
						System.exit(0); // Quit the program
				}

				// We didn't win or hit the mine, turn that panel white
				else {
					panel[x].setBackground(Color.WHITE); // Turn the selected panel white
					repaint(); // Redraws the screen to make sure we see everything
					panel[x].removeMouseListener(this); // Remove the ability to click the panel
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	/**********************************************************************
	 * Start a new game, clear all statistics
	 */
	private void newGame() {
		gamesWon = 0;
		totalGames = 0;
		playAgain();
	}

	/**********************************************************************
	 * Reset the game so we can play again
	 */
	public void playAgain() {
		// Update the statusBar statistics
		txtWins.setText("Wins: " + gamesWon + " out of " + totalGames);
		txtHitRate.setText(" " + HitRate.Calculate(gamesWon, totalGames));

		// Generate a new random mine between 0 and NUM
		mine = randomMine.nextInt(NUM);
		txtMine.setText("Mine: " + mine);

		// Reset the click counter to 0
		minesClicked = 0;

		// Reset the panels to their default state
		for (int x = 0; x < NUM; ++x) {
			// Remove the MouseListener from the panel
			panel[x].removeMouseListener(this);

			// Set the panel blue to indicate that the panel is clear, no mines
			panel[x].setBackground(Color.BLUE);

			// Add the MouseListener to enable each panel to accept clicks
			panel[x].addMouseListener(this);
		}
		validate(); // Re layout all components
		repaint(); // Re draw all components
	}

	/**********************************************************************
	 * Menu actions
	 */
	public void actionPerformed(ActionEvent event) {
		// Get a reference to the object that was clicked
		Object source = event.getSource();

		// Set the difficulty of the game
		if (source == radio1) {
			WIN = 5;
			newGame();
		} else if (source == radio2) {
			WIN = 10;
			newGame();
		} else if (source == radio3) {
			WIN = 15;
			newGame();
		}

		// Create a new game
		else if (source == newGameMenuItem) {
			newGame();
		}

		// Exit the program
		else if (source == exit)
			System.exit(0);

		// Display the about dialog box
		else if (source == about) {
			JOptionPane.showMessageDialog(null, "<html>Mine Field 1.0<br>" + "Created: 05/28/2010<br>"
					+ "Updated: 05/10/2018<br>" + "Click 10 safe squares and miss the mine to win.<br>"
					+ "Use the Options menu to change game difficulty.<br>" + "http://www.billthecomputerguy.com",
					"Mine Field 1.0", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**********************************************************************
	 * // Create Menus and MenuItems
	 */
	private void composeMenus() {
		JMenuBar mainBar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("Options");
		JMenu menu3 = new JMenu("Help");
		ButtonGroup group = new ButtonGroup();

		setJMenuBar(mainBar);
		mainBar.add(menu1);
		mainBar.add(menu2);
		mainBar.add(menu3);
		menu1.add(exit);
		menu2.add(newGameMenuItem);
		menu2.addSeparator();
		menu2.add(radio1);
		menu2.add(radio2);
		menu2.add(radio3);
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		menu3.add(about);
	}

	/**********************************************************************
	 * Add ActionListeners
	 */
	private void addActionListeners() {
		exit.addActionListener(this);
		newGameMenuItem.addActionListener(this);
		about.addActionListener(this);
		radio1.addActionListener(this);
		radio2.addActionListener(this);
		radio3.addActionListener(this);
	}

	/**********************************************************************
	 * Layout all componenets
	 */
	private void layoutComponents() {
		// Create the main background gameBoard with a GridLayout
		JPanel gameBoard = new JPanel(new GridLayout(ROWS, COLS, GAP, GAP));
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// Add main JPanel and statusBar to frame
		gameBoard.setBorder(BorderFactory.createEtchedBorder());
		add(gameBoard, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		txtWins.setEditable(false);
		txtHitRate.setEditable(false);
		txtMine.setEditable(false);
		statusBar.add(txtWins);
		statusBar.add(txtHitRate);
		statusBar.add(txtMine);

		// Add individual panels to main pane
		for (int x = 0; x < NUM; ++x) {

			// Create a new panel
			panel[x] = new JPanel();

			// Add the panel to the main pane
			gameBoard.add(panel[x]);

			// Set the background color and add the mouselistener,
			// We can click each panel individually
			panel[x].setBackground(Color.BLUE);
			panel[x].addMouseListener(this);
		}
	}

	/**********************************************************************
	 * Start main application
	 */
	public static void main(String[] args) {
		MineField frame = new MineField();
		final int LEFT = 100;
		final int TOP = 100;
		final int WIDTH = 400;
		final int HEIGHT = 330;
		frame.setBounds(LEFT, TOP, WIDTH, HEIGHT);

		// Read the image that will be used as the application icon.
		// Using "/" in front of the image file name will locate the
		// image at the root folder of our application. If you don't
		// use a "/" then the image file should be on the same folder
		// with your class file.
		BufferedImage image = null;
		try {
			image = ImageIO.read(frame.getClass().getResource("/minefield.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(image); // Set the application icon
		frame.setVisible(true); // Set the frame visible
	}
}