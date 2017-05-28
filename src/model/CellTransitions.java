package model;

/**
 * The Class CellTransitions.
 */
public class CellTransitions  {
	
	/** The directions in which adjacent cells will be checked. (there are all 8 directions) */
	private static int[][] adjacent = new int[][]{{-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}};
	
	/**
	 * No instances.
	 */
	private CellTransitions() {}
	

	/**
	 * Gets the number of live neighbors.
	 *
	 * @param matrix the matrix of cells
	 * @param x the x coordinate of the selected cell
	 * @param y the y coordinate of the selected cell
	 * @return the number of live neighbors (from 0 to 8)
	 */
	private static byte getLiveNeighbours(byte[][] matrix, int x, int y) {
		byte neighbours = 0;
		int heigth = matrix.length;
		int width = matrix[0].length;
		
		for (int[] neighbour : adjacent) {
			int nx = x + neighbour[0];
			int ny = y + neighbour[1];
			if (ny >=0 && ny < heigth)
			    if (nx >= 0 && nx < width)
			    	neighbours += matrix[ny][nx];
		}
		return neighbours;
	}
	
	/**
	 * Gets the next state of the cell.
	 *
	 * @param matrix the matrix of cells
	 * @param x the x coordinate of the selected cell
	 * @param y the y coordinate of the selected cell
	 * @return the next state (0 or 1)
	 */
	public static byte getNextState(byte[][] matrix, int x, int y) {
		byte isLive = matrix[y][x];
		byte liveNeighbourCount = getLiveNeighbours(matrix, x, y);
		
		if (isLive == 0) {
			if (liveNeighbourCount == 3) return 1;
			return 0;
		}
		
		if (liveNeighbourCount < 2) return 0;
		if (liveNeighbourCount <= 3) return 1;
		return 0;
	}
	
	/**
	 * Adds the glider object.
	 *
	 * @param matrix the matrix of cells
	 * @param x the x coordinate of the selected cell
	 * @param y the y coordinate of the selected cell
	 */
	public static void addGlider(byte[][] matrix, int x, int y) {
		//0 1 0
		//0[0]1
		//1 1 1
		if (y - 1 < 0 || x - 1 < 0 
				|| x + 1 >= matrix.length 
				|| y + 1 >= matrix[0].length) return;
		
		matrix[x][y - 1] = 1;
		matrix[x + 1][y] = 1;
		matrix[x - 1][y + 1] = 1;
		matrix[x][y + 1] = 1;
		matrix[x + 1][y + 1] = 1;
	}
	
	/**
	 * Adds the spaceship object.
	 *
	 * @param matrix the matrix of cells
	 * @param x the x coordinate of the selected cell
	 * @param y the y coordinate of the selected cell
	 */
	public static void addSpaceship(byte[][] matrix, int x, int y) {
		//1 0 0 1 0
		//0 0[0]0 1
		//1 0 0 0 1
		//0 1 1 1 1
		if (x - 1 < 0 || y - 2 < 0 
				|| x + 2 >= matrix.length 
				|| y + 2 >= matrix[0].length) return;
		
		matrix[x - 2][y - 1] = 1;
		matrix[x + 1][y - 1] = 1;
		matrix[x + 2][y] = 1;
		matrix[x - 2][y + 1] = 1;
		matrix[x + 2][y + 1] = 1;
		matrix[x - 1][y + 2] = 1;
		matrix[x][y + 2] = 1;
		matrix[x + 1][y + 2] = 1;
		matrix[x + 2][y + 2] = 1;
	}
	
	/**
	 * Adds the Gosper glider gun object.
	 *
	 * @param matrix the matrix of cells
	 * @param x the x coordinate of the selected cell
	 * @param y the y coordinate of the selected cell
	 */
	public static void addGGGun(byte[][] matrix, int x, int y) {
		//0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0
		//0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0
		//0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1
		//0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1
		//1 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0[0]0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
		//1 1 0 0 0 0 0 0 0 0 1 0 0 0 1 0 1 1 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0
		//0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0
		//0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
		//0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
		if (x - 18 < 0 || y - 4 < 0 
				|| x + 17 >= matrix.length 
				|| y + 4 >= matrix[0].length) return;
		
		matrix[x + 6][y - 4] = 1;
		matrix[x + 4][y - 3] = 1;
		matrix[x + 6][y - 3] = 1;
		matrix[x - 6][y - 2] = 1;
		matrix[x - 5][y - 2] = 1;
		matrix[x + 2][y - 2] = 1;
		matrix[x + 3][y - 2] = 1;
		matrix[x + 16][y - 2] = 1;
		matrix[x + 17][y - 2] = 1;
		matrix[x - 7][y - 1] = 1;
		matrix[x - 3][y - 1] = 1;
		matrix[x + 2][y - 1] = 1;
		matrix[x + 3][y - 1] = 1;
		matrix[x + 16][y - 1] = 1;
		matrix[x + 17][y - 1] = 1;
		matrix[x - 18][y] = 1;
		matrix[x - 17][y] = 1;
		matrix[x - 8][y] = 1;
		matrix[x - 2][y] = 1;
		matrix[x + 2][y] = 1;
		matrix[x + 3][y] = 1;
		matrix[x - 18][y + 1] = 1;
		matrix[x - 17][y + 1] = 1;
		matrix[x - 8][y + 1] = 1;
		matrix[x - 4][y + 1] = 1;
		matrix[x - 2][y + 1] = 1;
		matrix[x - 1][y + 1] = 1;
		matrix[x + 4][y + 1] = 1;
		matrix[x + 6][y + 1] = 1;
		matrix[x - 8][y + 2] = 1;
		matrix[x - 2][y + 2] = 1;
		matrix[x + 6][y + 2] = 1;
		matrix[x - 7][y + 3] = 1;
		matrix[x - 3][y + 3] = 1;
		matrix[x - 6][y + 4] = 1;
		matrix[x - 5][y + 4] = 1;
	}
}
