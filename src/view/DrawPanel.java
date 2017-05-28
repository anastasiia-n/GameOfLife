package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


/**
 * The Class DrawPanel.
 */
public class DrawPanel extends JPanel {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cell width. */
    private int cellW;
    
    /** The list of points. */
    private List<Point> points;
    
    /** The list of colors. */
    private List<Color> colors;
	
    /**
     * Instantiates a new draw panel.
     *
     * @param ps the Point list
     * @param cellW the cell width
     */
    public DrawPanel(List<Point> ps, int cellW) {
    	update(ps, cellW);
    }
    
    /**
     * Updates the current panel.
     *
     * @param ps the Point list
     * @param cellW the cell width
     */
    public void update(List<Point> ps, int cellW) {
        points = ps;
        colors = new ArrayList<Color>();
        for (int i = 0; i < points.size(); i++) {
			colors.add(Color.WHITE);
		}
        this.cellW = cellW;
        setBackground(Color.WHITE);
        repaint();
    }
    
	/**
	 * Redraw cells.
	 *
	 * @param col the Color list
	 */
	public void redrawCells(List<Color> col) {
		colors = col;
		repaint();
	}
	
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int min = points.size() < colors.size() ? points.size() : colors.size();

        for (int i = 0; i < min; i++) {
			Point point = points.get(i);
			Color color = i < colors.size() ? colors.get(i) : Color.WHITE;
			
			if(color == Color.WHITE) {
            	g2.setColor(Color.LIGHT_GRAY);
            	g2.drawRect(point.x, point.y, cellW, cellW);
            }
            else {
            	g2.setColor(color);
            	g2.fillRect(point.x, point.y, cellW, cellW);
            }
		}
    }
}