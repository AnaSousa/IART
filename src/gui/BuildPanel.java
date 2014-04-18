package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BuildPanel extends JPanel {
	
	//images needed
	private BufferedImage cell;
	
	//other fields
	private int mapSizeH;
	private int mapSizeV;
	
	//constants
	private final int X = 40;
	private final int Y = 75;
	private final int L = 50;
	
	/**
	 * Create the panel.
	 */
	public BuildPanel() {
		
		mapSizeH = 10;
		mapSizeV = 5;
		try {
			cell = ImageIO.read(new File("resources/cell.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = 0; i < mapSizeH; i++) {
			for(int j = 0; j < mapSizeV; j++)
				g.drawImage(cell, X + i * L, Y + j * L, L, L, null);
		}
		
	//	g.drawImage(cell, 80, 5,30,30,null);
	}

}
