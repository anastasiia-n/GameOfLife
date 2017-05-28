package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class ButtonLinksFrame. JFrame with simple interface for using in the View class.
 */
public class ButtonLinksFrame extends JFrame {
	
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The container. */
    private Container container;
    
    /** The current panel. */
    private JPanel currentPanel;
    
    /** The width. */
    private int width = 450;
    
    /** The height. */
    private int height = 200;
    
	/**
	 * Instantiates a new button links frame.
	 */
	public ButtonLinksFrame() {
		super();
		
		container = getContentPane();
		container.setBackground(Color.WHITE);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		addNewLine();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Window#setSize(java.awt.Dimension)
	 */
	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		width = d.width;
		height = d.height;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Window#setSize(int, int)
	 */
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Adds a new panel. Can be used to add next 'row' to the frame.
	 */
	public void addNewLine() {
		currentPanel = new JPanel();
		currentPanel.setLayout(new FlowLayout());
		container.add(currentPanel);
	}
	
	/**
	 * Adds the text block.
	 *
	 * @param t the text of the block
	 */
	public void addTextBlock(String t) {
		currentPanel.add(new JLabel
				("<html> <body style='width: " + (width > 150 ? (width - 150) : 50) + "px'>" + t + "</body></html>"));
	}
	
	/**
	 * Adds the text.
	 *
	 * @param t the text of the label
	 */
	public void addText(String t) {
		currentPanel.add(new JLabel(t));
	}
	
	/**
	 * Adds the label with 'link' properties.
	 *
	 * @param text the text of the link
	 * @param url the url of the link
	 */
	public void addLink(String text, String url) {
		Map<TextAttribute, Integer> fontAttributes = new HashMap<>();
		
		JLabel link = new JLabel();
		link.setText(text);
		link.setFont(link.getFont().deriveFont(link.getFont().getStyle() | Font.ITALIC));
		link.setToolTipText(url);
		link.setPreferredSize(new Dimension(200, 50));
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					openLink(new URI(url));
					setVisible(false);
					dispose();
					
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				link.setFont(link.getFont().deriveFont(fontAttributes));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				fontAttributes.put(TextAttribute.UNDERLINE, -1);
				link.setFont(link.getFont().deriveFont(fontAttributes));
			}
		});
		currentPanel.add(link);
	}
	
	/**
	 * Open link.
	 *
	 * @param uri the uri to open
	 */
	private void openLink(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try { Desktop.getDesktop().browse(uri); } 
			catch (IOException e) { 
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Inits the frame. Sets it visible.
	 */
	public void init() {
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
}
