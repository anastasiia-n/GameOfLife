package presenter;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class maintains logic between cell's coordinates in the matrix and cell's coordinates in the panel.
 */
public class CellsDrawing {
	
	/** The cell width. It is a square, so height = width*/
	private int cellW = 10;
	
	/** The matrix width. */
	private final int matrixW;
	
	/** The matrix height. */
	private final int matrixH;
	
	/**
	 * Instantiates a new cells drawing.
	 * Can be instantiated from the package only.
	 *
	 * @param matrixW the width of matrix
	 * @param matrixH the height of matrix
	 */
	CellsDrawing(int matrixW, int matrixH) {
		this.matrixW = matrixW;
		this.matrixH = matrixH;
	}
	
	CellsDrawing() {
		int[] dimentions = getMaxMatrixDimensions();
		matrixW = dimentions[0];
		matrixH = dimentions[1];
	}
	
	/**
	 * Gets the cell coordinates in the matrix.
	 *
	 * @param panelX the cell's X coordinate in panel
	 * @param panelY the cell's Y coordinate in panel
	 * @return the cell matrix coordinates
	 */
	public int[] getCellMatrixCoordinates(int panelX, int panelY) {
		
		int[] coordinates = new int[2];
		coordinates[0] = (panelX / cellW);
		coordinates[1] = (panelY / cellW);
		
		return coordinates;
	}
	
	/**
	 * Gets the coordinates of all cells in panel.
	 *
	 * @return the list of point that specify the coordinates of the cells.
	 */
	public List<Point> getAllPanelPoints() {
		List<Point> coordinates = new ArrayList<> ();
		for (int i = 0; i < matrixH; i++) {
			for (int j = 0; j < matrixW; j++) {
				coordinates.add(new Point(i * cellW, j * cellW));
			}
		}
		return coordinates;
	}
	
	/**
	 * Gets the maximum matrix dimensions.
	 *
	 * @return the max matrix dimensions
	 */
	public int[] getMaxMatrixDimensions() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) (screenSize.getWidth() / cellW );
		int height = (int) (screenSize.getHeight() / cellW);
		return new int[] {width, height};
	}
	
	/**
	 * Sets the cell width.
	 *
	 * @param width the cell width
	 */
	public void setCellWidth(int width) {
		if(width > 0) cellW = width;
	}
	
	/**
	 * Gets the cell width.
	 *
	 * @return the cell width
	 */
	public int getCellWidth() {
		return cellW;
	}
	
	/**
	 * Gets the matrix width.
	 *
	 * @return the matrix width
	 */
	public int getMatrixW() {
		return matrixW;
	}
	
	/**
	 * Gets the matrix height.
	 *
	 * @return the matrix height
	 */
	public int getMatrixH() {
		return matrixH;
	}
}
