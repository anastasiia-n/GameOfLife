package presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.CellTransitions;
import view.View;

/**
 * The Class Presenter. "Communicates" with View application part via MenuClickListener class.
 */
public class Presenter {
	
	/**
	 * The Enum Modes of drawing.
	 */
	enum Modes {
			simple,
			glider,
			spaceship,
			GGGun
	};
	
	/** The current mode. */
	private Modes mode = Modes.simple;
	
	/** The timer speed. */
	private int timerSpeed = 300;
	
	/** The view. */
	private View view;
	
	/** The timer. */
	private Timer timer;
	
	/** The matrix height. */
	private final int matrixHeight;
	
	/** The matrix width. */
	private final int matrixWidth;
	
	/** The matrix. */
	private byte[][] matrix;
	
	private CellsDrawing cellsDrawing;
	
	/** Mouse press listener. Uses mouse coordinates to get cell that was clicked */
	private MouseAdapter arrayChanged;
    
	/**
	 * Instantiates a new presenter.
	 *
	 * @param view the main view class from View component
	 */
	public Presenter(View view) {
		cellsDrawing = new CellsDrawing();
		setView(view);
		this.matrixHeight = cellsDrawing.getMatrixH();
		this.matrixWidth = cellsDrawing.getMatrixW();
	}
	
	/**
	 * Instantiates a new presenter.
	 *
	 * @param view the main view class from View component
	 * @param matrixHeight the matrix height
	 * @param matrixWidth the matrix width
	 */
	public Presenter(View view, int matrixHeight, int matrixWidth) {
		setView(view);
		this.matrixHeight = matrixHeight;
		this.matrixWidth = matrixWidth;
	}
	
	private void setView(View view) {
		if(view == null) return;
		this.view = view;
		this.view.setMenuClickListener(new MenuClickListener());
		this.view.setCellsDrawing(cellsDrawing);
		
		confPanelMouseAdapter();
		this.view.setPanelMouseAdapter(arrayChanged);
	}
	
	private void confPanelMouseAdapter() {
		arrayChanged = new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent e) {
	        	int[] cellXY = cellsDrawing.getCellMatrixCoordinates(e.getX(), e.getY());
	        	if (mode == Modes.simple) 
	        		matrix[cellXY[0]][cellXY[1]] = 1;
	        	
	        	else if (mode == Modes.glider) {
	        		CellTransitions.addGlider(matrix, cellXY[0], cellXY[1]);
	        		
	        	} else if (mode == Modes.spaceship) {
	        		CellTransitions.addSpaceship(matrix, cellXY[0], cellXY[1]);
	        		
	        	} else if (mode == Modes.GGGun) {
	        		CellTransitions.addGGGun(matrix, cellXY[0], cellXY[1]);
	        	}
	    		mode = Modes.simple;
	        	view.redrawCells(matrix);
	        }
	    };
	}
	
	/**
	 * Inits the Presenter.
	 */
	public void init() {
		view.init(matrixHeight, matrixWidth);
		view.addToSaved( FileSystem.findAllSavedFiles() );
		
		matrix = new byte[matrixHeight][matrixWidth];
		initMatrix();
	}
	
	/**
	 * Inits the matrix.
	 */
	private void initMatrix() {
		for(int i = 0; i < matrixHeight; i++) {
			for(int j = 0; j < matrixWidth; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	/**
	 * Starts new timer.
	 */
	private void startTimer() {
		if(timer == null) {
			timer = new Timer();
		}
		timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	nextGeneration();
            }
        }, 100,timerSpeed);
	}
	
	/**
	 * Stops current timer.
	 */
	private void stopTimer() {
		timer.cancel();
		timer.purge();
		timer = null;
	}
	
	/**
	 * Next generation. Sets all cells to the 'next state'.
	 */
	private void nextGeneration() {
    	byte[][] newMatrix = new byte[matrix.length][matrix[0].length];
		for(int i = 0; i < matrixHeight; i++) {
			for(int j = 0; j < matrixWidth; j++) {
				newMatrix[i][j] = CellTransitions.getNextState(matrix, j, i);
			}
		}
		matrix = newMatrix;
		view.redrawCells(matrix);
	}

	
	/**
	 * The listener interface for receiving menuClick events.
	 * The class that is interested in processing a menuClick
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addMenuClickListener<code> method. When
	 * the menuClick event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see MenuClickEvent
	 */
	public class MenuClickListener implements ActionListener, ChangeListener{
		
		/**
		 * Listens menu items clicks
		 */
		@Override
	    public void actionPerformed(ActionEvent e) {
	       String command = e.getActionCommand().toLowerCase();  
	       
	       if (command.equals("start") ) {
	    	   startTimer();
	    	   view.disableButton("Start");
	    	   view.enableButton("Stop");
	    	   
	       } else if (command.equals("stop") ) {
	    	   stopTimer();
	    	   view.disableButton("Stop");
	    	   view.enableButton("Start");
	    	   
	       } else if (command.equals("save")) {
	    	   view.addToSaved( FileSystem.saveToFile(matrix) );
	    	   
	       } else if (command.equals("clear")) {
	    	   initMatrix();
	    	   nextGeneration();
	    	   
	       } else if (command.indexOf("open") >= 0) {
	    	   FileSystem.readFromFileToMatrix(command.replaceAll("open", ""), matrix);
	    	   nextGeneration();
	    	   
	       } else if (command.indexOf("delete") >= 0) {
	    	   FileSystem.deleteFile(command.replaceAll("delete", ""));
	    	   view.deleteFromSaved(command.replaceAll("delete", ""));
	    	   
	       } else if (command.equals("glider")) {
	    	   mode = Modes.glider;
	    	   
	       } else if (command.equals("spaceship")) {
	    	   mode = Modes.spaceship;
	    	   
	       } else if (command.equals("gggun")) {
	    	   mode = Modes.GGGun;
	       }
	    }

		/**
		 * Listens changing state of the spinners.
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			String function = ( (JSpinner)e.getSource() ).getName()
								.replaceAll("spinner", "").toLowerCase();
			
			if (function.equals("speed")) {
				timerSpeed = view.getSpeedValue();
				stopTimer();
				startTimer();
				
			} else if (function.equals("size")) {
				view.setCellWidth(view.getSizeValue());
			}
		}		
	 }
}
