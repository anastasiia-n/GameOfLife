package main;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import presenter.Presenter;
import view.View;

public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    	e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    catch (InstantiationException e) {
	    	e.printStackTrace();
	    }
	    catch (IllegalAccessException e) {
	    	e.printStackTrace();
	    }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View view = new View();
					Presenter presenter = new Presenter(view);
					presenter.init();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
