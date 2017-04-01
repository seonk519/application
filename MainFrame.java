package application;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author 1100356803
 *
 */
public class MainFrame extends JFrame {
	private Level			level			= Level.MIDDLE;
	private MineGUI			mineGUI			= null;
	/* menu */
	private JMenuBar		menuBar			= null;
	private JMenu			fileMenu		= null;
	private JMenuItem		newItem			= null;
	private JMenuItem		lowLvItem		= null;
	private JMenuItem		midlLvItem		= null;
	private JMenuItem		highLvItem		= null;
	private JMenuItem		bestTimeItem	= null;
	private JMenuItem		exitItem		= null;

	private JMenu			helpMenu		= null;
	private JMenuItem		helpItem		= null;
	private JMenuItem		inforItem		= null;

	private String			helpString		= "Help";
	private String			inforString		= "Credit to Seon Kim";
	private String			endString		= "Do you want to exit the game?";

	public MainFrame( String title ) {
		super( title );
		initMenu();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.mineGUI = new MineGUI(this.level);
		this.setContentPane(this.mineGUI);
		this.pack();
		this.setCenterPosition();
		// frame icon
		MediaTracker tracker = new MediaTracker( this );
		Image img = Toolkit.getDefaultToolkit().getImage( "IMG/reset.gif" );
		tracker.addImage( img, 0 );
		setIconImage( img );
		this.setResizable(false);
		this.setVisible(true);
	}

	/* Inner class [ActionListener]*/
	class newActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeGame();
		}
	}
	class levelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == lowLvItem ) {
				changeLevel(Level.LOW);
			} else if (e.getSource() == midlLvItem ) {
				changeLevel(Level.MIDDLE);
			} else {
				changeLevel(Level.HIGH);
			}
		}
	}
	class bestTimeActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			bestTimeShow();
		}
		public void bestTimeShow() {
			String score = null;
			File file[] 	= new File[3];
			file[0]	= new File("lowscore.dat");
			file[1]	= new File("middlescore.dat");
			file[2]	= new File("highscore.dat");
			String title[]	= {"1st", "2nd", "3rd"};
			score = bestTimeRead( file[0], title[0] ) + bestTimeRead( file[1], title[1] ) + bestTimeRead( file[2], title[2] );
			JOptionPane.showMessageDialog( MainFrame.this, score, "The highest score is", JOptionPane.INFORMATION_MESSAGE );
		}
		public String bestTimeRead(File file, String level) {
			String text		= null;
			String score	= null;
			if ( file.exists() ) {
				try {
					BufferedReader reader = new BufferedReader( new FileReader(file));
					text = reader.readLine();
					reader.close();
					if ( text == null ) score = level + " " + " " + "second";
					else {
						String[] temp = text.split("/");
						score = level + " " + temp[0] + "second " + temp[1];
					}
				} catch( IOException ex ) { System.err.println(" "); }
			} else {
				try {
					FileWriter writer = new FileWriter( file );
					writer.close();
					score = level + " " + " " + " second";
				} catch(IOException ex) { System.err.println(" "); }
			}
			return score + "\n";
		}
	}
	class exitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result;
			result = JOptionPane.showConfirmDialog( MainFrame.this, endString, "Game End",
														JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
			if ( result == JOptionPane.YES_OPTION ) System.exit(0);
		}
	}
	class dialogActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ( e.getSource() == helpItem ) {
				JOptionPane.showMessageDialog( MainFrame.this, helpString, "Help", JOptionPane.INFORMATION_MESSAGE );
			} else if ( e.getSource() == inforItem ) {
				JOptionPane.showMessageDialog( MainFrame.this, inforString, "Information", JOptionPane.INFORMATION_MESSAGE );
			}
		}
	} // Inner class end


	/* Member Method */
	public void initMenu() {
		menuBar			= new JMenuBar();
		fileMenu		= new JMenu( "File" );
		newItem			= new JMenuItem( "New Game" );
		lowLvItem		= new JMenuItem( "Easy" );
		midlLvItem		= new JMenuItem( "Normal" );
		highLvItem		= new JMenuItem( "Hard" );
		bestTimeItem	= new JMenuItem( "Best Time" );
		exitItem		= new JMenuItem( "Exit" );

		newItem.addActionListener( new newActionListener() );
		lowLvItem.addActionListener( new levelActionListener() );
		midlLvItem.addActionListener( new levelActionListener() );
		highLvItem.addActionListener( new levelActionListener() );
		bestTimeItem.addActionListener( new bestTimeActionListener() );
		exitItem.addActionListener( new exitActionListener() );

		fileMenu.add( newItem );
		fileMenu.addSeparator();
		fileMenu.add( lowLvItem );
		fileMenu.add( midlLvItem );
		fileMenu.add( highLvItem );
		fileMenu.addSeparator();
		fileMenu.add( bestTimeItem );
		fileMenu.add( exitItem );

		menuBar.add( fileMenu );

		helpMenu		= new JMenu( "Help" );
		helpItem		= new JMenuItem( "HELP" );
		inforItem		= new JMenuItem( "Info" );

		helpItem.addActionListener( new dialogActionListener() );
		inforItem.addActionListener( new dialogActionListener() );

		helpMenu.add( helpItem );
		helpMenu.addSeparator();
		helpMenu.add( inforItem );

		menuBar.add( helpMenu );

		this.setJMenuBar( menuBar );
	}
	public void setCenterPosition() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = this.getSize();
		int xpos = (int)( screen.getWidth() / 2 - frm.getWidth() / 2 );
		int ypos = (int)( screen.getHeight() / 2 - frm.getHeight() / 2 );
		this.setLocation( xpos, ypos );
	}
	public void changeLevel(Level lv) {
		this.level = lv;
		changeGame();
	}
	public void changeGame() {
		this.remove(this.mineGUI);
		this.mineGUI = new MineGUI(this.level);
		this.setContentPane(this.mineGUI);
		this.pack();
		this.setCenterPosition();
		this.setResizable(false);
		this.setVisible(true);
	}
	public static void main ( String args[] )
	{
		new MainFrame( "MineSweeper" );
	}
}
