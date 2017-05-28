package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import presenter.CellsDrawing;
import presenter.Presenter.MenuClickListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * The Class View. Communicates with Presenter via panel MouseAdapter and menu Listener.
 * Uses CellsDrawing class.
 */
public class View {
	
	/** The frame width. */
	private int frameWidth;
	
	/** The frame height. */
	private int frameHeight;
	
	/** The frame. */
	private JFrame frame;
	
	/** The main panel. */
	private DrawPanel mainPanel;
	
	/** The panel mouse adapter. */
	private MouseAdapter panelMouseAdapter;

	/** The c drawing. */
	private CellsDrawing cDrawing;
	
	/** The menu open. */
	private JMenu mnOpen;
	
	/** The menu delete. */
	private JMenu mnDelete;
	
	/** The menu item start. */
	private JMenuItem mntmStart;
	
	/** The menu item stop. */
	private JMenuItem mntmStop;
	
	/** The spinner speed. */
	private JSpinner spinnerSpeed;
	
	/** The spinner size. */
	private JSpinner spinnerSize;
	
	/** The menu listener. */
	private MenuClickListener menuListener;

	/**
	 * Instantiates a new view.
	 */
	public View() {
		frameWidth = 700;
		frameHeight = 500;
	}
	
	/**
	 * Sets the cells drawing.
	 *
	 * @param cd the new cells drawing
	 */
	public void setCellsDrawing(CellsDrawing cd) {
		if (cd == null) return;
		cDrawing = cd;
	}
	
	/**
	 * Sets the panel mouse adapter.
	 *
	 * @param adapter the new panel mouse adapter
	 */
	public void setPanelMouseAdapter(MouseAdapter adapter) {
		panelMouseAdapter = adapter;
	}
	
	/**
	 * Sets the menu click listener.
	 *
	 * @param list the new menu click listener
	 */
	public void setMenuClickListener(MenuClickListener list) {
		menuListener = list;
	}
	
	/**
	 * Redraws cells. Creates Color list on a base of the cells matrix, 
	 * calls redrawCells() from DrawPanel
	 *
	 * @param cells the cells
	 */
	public void redrawCells(byte[][] cells) {
		List<Color> colors = new ArrayList<Color>();
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[0].length; j++) {
				colors.add(cells[i][j] == 0 ? Color.WHITE : Color.BLACK);
			}
		}
		
		mainPanel.redrawCells(colors);
	}
	
	/**
	 * Enable button.
	 *
	 * @param name the name of the button to enable
	 */
	public void enableButton(String name) {
		name = name.toLowerCase().trim();
		if( name.equals("start") ) {
			mntmStart.setEnabled(true);
		} else if( name.equals("stop") ) {
			mntmStop.setEnabled(true);
		}
	}
	
	/**
	 * Disable button.
	 *
	 * @param name the name of the button to disable
	 */
	public void disableButton(String name) {
		name = name.toLowerCase().trim();
		if( name.equals("start") ) {
			mntmStart.setEnabled(false);
		} else if( name.equals("stop") ) {
			mntmStop.setEnabled(false);
		}
	}
	
	/**
	 * Sets the cell width.
	 *
	 * @param width the new cell width
	 */
	public void setCellWidth(int width) {
		cDrawing.setCellWidth(width);
		mainPanel.update(cDrawing.getAllPanelPoints(), cDrawing.getCellWidth());
	}
	
	/**
	 * Gets the size value.
	 *
	 * @return the size value
	 */
	public int getSizeValue() {
		return (int) spinnerSize.getValue();
	}
	
	/**
	 * Gets the speed value.
	 *
	 * @return the speed value
	 */
	public int getSpeedValue() {
		return (int) spinnerSpeed.getValue() * 50;
	}
	
	/**
	 * Adds the to saved.
	 *
	 * @param name the 'simple' name of the file
	 */
	public void addToSaved(String name) {
		JMenuItem oItem = new JMenuItem();
		oItem.setName(name); oItem.setText(name);
		oItem.setActionCommand("open" + name);
		oItem.addActionListener(menuListener);
		mnOpen.add(oItem);
		
		JMenuItem dItem = new JMenuItem();
		dItem.setActionCommand("delete" + name);
		dItem.setName(name); dItem.setText(name);
		dItem.addActionListener(menuListener);
		mnDelete.add(dItem);
	}
	
	/**
	 * Delete from saved.
	 *
	 * @param filename the 'simple' name of the file
	 */
	public void deleteFromSaved(String filename) {
		for (int i = 0; i < mnOpen.getItemCount(); i++) {
	        if (mnOpen.getItem(i).getName().equals(filename)) {
	    		mnOpen.remove(i); mnDelete.remove(i);
	        }
	    }
	}
	
	/**
	 * Adds the to saved.
	 *
	 * @param names the list of 'simple' names of the files
	 */
	public void addToSaved(List<String> names) {
		for(String name : names) {
			addToSaved(name);
		}
	}
	
	/**
	 * Inits the View. Sets it visible.
	 *
	 * @param matrixH the matrix height
	 * @param matrixW the matrix width
	 * @wbp.parser.entryPoint 
	 */
	public void init(int matrixH, int matrixW) {
		
		frame = new JFrame("The game of life");
		frame.setSize(frameWidth, frameHeight);	frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new DrawPanel(cDrawing.getAllPanelPoints(), cDrawing.getCellWidth());

		if(panelMouseAdapter != null)
			mainPanel.addMouseListener(panelMouseAdapter);
		
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		mntmStart = new JMenuItem("Start");
		mntmStart.setActionCommand("Start");
		mntmStart.addActionListener(menuListener);
		mnGame.add(mntmStart);
		
		mntmStop = new JMenuItem("Stop");
		mntmStop.setActionCommand("Stop");
		mntmStop.addActionListener(menuListener);
		mntmStop.setEnabled(false);
		mnGame.add(mntmStop);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setActionCommand("Save");
		mntmSave.addActionListener(menuListener);
		mnGame.add(mntmSave);
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.setActionCommand("Clear");
		mntmClear.addActionListener(menuListener);
		mnGame.add(mntmClear);

		
		
		mnOpen = new JMenu("Open");
		mnGame.add(mnOpen);
		
		mnDelete = new JMenu("Delete");
		mnGame.add(mnDelete);
		mntmStart.addActionListener(menuListener);
		
		JMenu mnAdd = new JMenu("Add...");
		menuBar.add(mnAdd);
		
		JMenuItem mntmGlider = new JMenuItem("Glider");
		mntmGlider.setActionCommand("Glider");
		mntmGlider.addActionListener(menuListener);
		mnAdd.add(mntmGlider);
		
		JMenuItem mntmSpaceship = new JMenuItem("Spaceship");
		mntmSpaceship.setActionCommand("Spaceship");
		mntmSpaceship.addActionListener(menuListener);
		mnAdd.add(mntmSpaceship);
		
		JMenuItem mntmGosperGliderGun = new JMenuItem("Gosper glider gun");
		mntmGosperGliderGun.setActionCommand("GGGun");
		mntmGosperGliderGun.addActionListener(menuListener);
		mnAdd.add(mntmGosperGliderGun);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JPanel panel = new JPanel();
		mnSettings.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTimerSpeed = new JLabel("Generation period:");
		panel.add(lblTimerSpeed, BorderLayout.NORTH);
		
		spinnerSpeed = new JSpinner();
		spinnerSpeed.setModel(new SpinnerNumberModel(6, 1, 15, 1));
		spinnerSpeed.setName("spinnerSpeed");
		spinnerSpeed.addChangeListener(menuListener);
		panel.add(spinnerSpeed, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		mnSettings.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCellSize = new JLabel("Cell size:");
		panel_1.add(lblCellSize, BorderLayout.NORTH);
		
		spinnerSize = new JSpinner();
		spinnerSize.setModel(new SpinnerNumberModel(10, 5, 30, 5));
		spinnerSize.setName("spinnerSize");
		spinnerSize.addChangeListener(menuListener);
		panel_1.add(spinnerSize, BorderLayout.SOUTH);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAboutApp = new JMenuItem("About app");
		mntmAboutApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ButtonLinksFrame message = new ButtonLinksFrame();
				message.setTitle("About app");
				message.addTextBlock("The \"game\" is a zero-player game, meaning that its evolution is determined "
						+ "by its initial state, requiring no further input. ");
				message.addText("More here: ");
				message.addLink("Conway's Game of Life", "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life");
				message.init();
			}
		});
		mnAbout.add(mntmAboutApp);
		
		JMenuItem mntmAuthor = new JMenuItem("Author");
		mntmAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ButtonLinksFrame message = new ButtonLinksFrame();
				message.setTitle("About author");
				message.addText("Made by Anastasiia Nikolaienko.");
				message.addLink("Github", "https://github.com/anastasiia-n");
				message.init();
			}
		});
		mnAbout.add(mntmAuthor);
		
		Component horizontalStrut = Box.createHorizontalStrut(451);
		menuBar.add(horizontalStrut);
		
		frame.setVisible(true);
		
	}
}
