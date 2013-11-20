

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class pixelColor extends GraphicsProgram {
	
	public static final int APPLICATION_WIDTH = 1000;
	public static final int APPLICATION_HEIGHT = 750;
	public int WIDTH = 1000;
	public int HEIGHT = 750;
	
	
	public int[][] allColors;
	
	private int pixelX = 400;
	private int pixelY = 200;
	
	private static int CIRCLE_RADIUS = 10;
	
	private double SCALE = .25;
	
	private GOval circle;
	
	private GRect square;
	
	private GLine line;
	
	private GLine line2;
	
	private GImage picture;
	
	private BufferedImage image = null;
	
	private int blockPixelX = 20;
	private int blockPixelY = 20;
	
	
	private String image_name = "IMG_0744.JPG";
	
	
	public void run(){
		
		setup();
		
		
		
		/*JButton button = new JButton();
		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(button);
		
		*/
		
		println("Blocks high: " + HEIGHT/(blockPixelY*SCALE));
		
		println("Blocks across: " + WIDTH/(blockPixelX*SCALE));
		
		for(int y2=0;y2<HEIGHT/(blockPixelY*SCALE); y2++){
			
			for(int x2=0;x2<WIDTH/(blockPixelX * SCALE); x2++){
				
				//keeps track of colors totals
				int runingTotalRed = 0;
				
				int runingTotalGreen = 0;
				
				int runingTotalBlue = 0;

				
				//checks all the pixels in the square
				for(int x=x2 * blockPixelX;x<blockPixelX + (blockPixelX * x2); x++){
					
					for(int y=y2 * blockPixelY;y<blockPixelY + (blockPixelY * y2) ; y++){
						
						int clr= image.getRGB(x, y);
						
						int red = (clr & 0x00ff0000)>>16;
						
						int green = (clr & 0x0000ff00)>>8;
						
						int blue = (clr & 0x000000ff);
					
						
						runingTotalRed = runingTotalRed + red;
						
						runingTotalGreen = runingTotalGreen + green;
						
						runingTotalBlue = runingTotalBlue + blue;
					
					}
					
				}
				
				//gets the red average over the given area
				int redAverage = runingTotalRed/(blockPixelX*blockPixelY);
				
				//gets the green average over the given area
				int greenAverage = runingTotalGreen/(blockPixelX*blockPixelY);
				
				//gets the blue average over the given area
				int blueAverage = runingTotalBlue/(blockPixelX*blockPixelY);
				
				int[] legoColor = colorPicker(redAverage,greenAverage,blueAverage);
			/*
				//creates the square 
				GRect rect1 = new GRect(blockPixelX *SCALE * x2, blockPixelY * SCALE * y2, blockPixelX*SCALE, blockPixelY*SCALE);
				rect1.setFilled(true);
				rect1.setFillColor(new Color(redAverage, greenAverage, blueAverage));
				rect1.setColor(new Color(redAverage, greenAverage, blueAverage));
				add(rect1);
				*/
				GRect rect1 = new GRect(blockPixelX *SCALE * x2, blockPixelY * SCALE * y2, blockPixelX*SCALE, blockPixelY*SCALE);
				rect1.setFilled(true);
				rect1.setFillColor(new Color(legoColor[0], legoColor[1], legoColor[2]));
				rect1.setColor(new Color(legoColor[0], legoColor[1], legoColor[2]));
				add(rect1);
				
			}//for loop x2
			
		}//for loop y2 brace	
		
		
		
		
		
	
		
	}//run brace
	
	public void setup(){
		

		//loads image
		File file = new File(image_name);
		
		try {
			 image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//makes image
		picture = new GImage(image_name);
		
		if(picture.getHeight()*SCALE > APPLICATION_HEIGHT){
			SCALE = .175;
		}
		this.setSize((int)(picture.getWidth()*SCALE), (int)(picture.getHeight()*SCALE));
		WIDTH = (int) (picture.getWidth()*SCALE);
		HEIGHT = (int) (picture.getHeight() * SCALE);
		
		
		picture.scale(SCALE);
		add(picture,0, 0);
		
		
					
			
		int clr= image.getRGB(pixelX,pixelY);
		
		int red = (clr & 0x00ff0000)>>16;
		
		int green = (clr & 0x0000ff00)>>8;
		
		int blue = (clr & 0x000000ff);
		
		
		setupColorArray();
		
		
		//makes circle
		circle = new GOval(pixelX*SCALE + CIRCLE_RADIUS/2,pixelY*SCALE + CIRCLE_RADIUS/2, CIRCLE_RADIUS,CIRCLE_RADIUS);
		circle.setColor(Color.pink);
		add(circle);

		//makes square
		square = new GRect(100 + pixelX*SCALE, pixelY*SCALE - 50, 50, 50);
		square.setColor(Color.pink);
		square.setFilled(true);
		square.setFillColor(new Color(red, green, blue));
		add(square);
		
		//makes line
		line = new GLine(pixelX*SCALE + CIRCLE_RADIUS, pixelY*SCALE +5, 100 + pixelX*SCALE, pixelY*SCALE - 50);
		line.setColor(Color.pink);
		add(line);
		
		//makes line2
		line2 = new GLine(pixelX*SCALE + CIRCLE_RADIUS, pixelY*SCALE +5 + CIRCLE_RADIUS, 100 + pixelX*SCALE, pixelY*SCALE);
		line2.setColor(Color.pink);
		add(line2);
	
		
		
		//adds mouse movemts 
		addMouseListeners();
		
	}//setup brace
	
	
	
	public void tracker(){
		
		//makes a program that shows a color of a given pixel.
		int	 clr= image.getRGB(pixelX,pixelY);
		
		int red = (clr & 0x00ff0000)>>16;
		
		int green = (clr & 0x0000ff00)>>8;
		
		int blue = (clr & 0x000000ff);
		
		square.setFillColor(new Color(red, green, blue));
		
	
	}
	
	
	
	
	public void mouseMoved(MouseEvent mouse) {
		
		//makes a square with lines show a pixel on the screen. note that the big pixels that make up
		//the block image are infront of  the square with lines.
		
		circle.setLocation(mouse.getX() - CIRCLE_RADIUS/2, mouse.getY() - CIRCLE_RADIUS/2);
		square.setLocation(mouse.getX() + 95, mouse.getY() - 50 - CIRCLE_RADIUS);
		line.setLocation(mouse.getX() + CIRCLE_RADIUS/2, mouse.getY() +5 - CIRCLE_RADIUS);
		line2.setLocation(mouse.getX() + CIRCLE_RADIUS/2, mouse.getY() +5);
		
		pixelX = (int) (mouse.getX()/SCALE);
		pixelY = (int) (mouse.getY()/SCALE);
		
		
		tracker();
		
		
	}

		
	public int[] colorPicker(int averageR, int averageG, int averageB){
		
		int lowestScore = 1000;
		
		int[] closestColor = null; //set inside loop by doing ... closestColor = allColors[i];
			
		
		
		for(int i=0; i<allColors.length; i++){
			
			
			
			//looks at differance in red
			int R_Differance = allColors[i][0] -averageR;
		
			if(R_Differance < 0){
				//to find the closest color we subtrack the two numbers. if the numbers are reversed 
				//and we wound get a negative number, we switch the numbers around. this is what this code does.
				R_Differance = -R_Differance;
			
			}
		
		
			//looks at differance in green
			int G_Differance = allColors[i][1] -averageG;
			
			if(G_Differance < 0){
				//if number is negative switch numbers around.
				G_Differance = -G_Differance;
			}
		
		
			
			//looks at differance in blue
			int B_Differance = allColors[i][2] -averageB;
			
			if(B_Differance < 0){
				
				//if number is negative switch numbers around.
				B_Differance = -B_Differance;
			}
		 
			//keeps track of the total score of number points.
			int total_score;
			
			total_score = R_Differance + G_Differance + B_Differance;
			
			// updates smallest.
			if(total_score < lowestScore){
				
				lowestScore = total_score;
				
				closestColor = allColors[i];
				
				
			}
			
		}//for loop brace


		return closestColor;
	}//colorPicker
		
		
	public void setupColorArray(){
		
		//sets up the array of all the colors. this goes in the order of R,G,B
		
		int[] black = new int[3];
		black[0] = 32;
		black[1] = 47;
		black[2] = 57;
		
		int[] darkGrey = new int[3];
		darkGrey[0] = 112;
		darkGrey[1] = 112;
		darkGrey[2] = 112;
		
		int[] grey = new int[3];
		grey[0] = 168;
		grey[1] = 168;
		grey[2] = 168;
		
		
		int[] white = new int[3];
		white[0] = 255;
		white[1] = 255;
		white[2] = 255;
		
		int[] darkRed = new int[3];
		darkRed[0] = 119;
		darkRed[1] = 26;
		darkRed[2] = 26;
	
		int[] red = new int[3];
		red[0] = 189;
		red[1] = 46;
		red[2] = 27;
	
	
		int[] brown = new int[3];
		brown[0] = 102;
		brown[1] = 57;
		brown[2] = 20;
		
		
		int[] lightBrown = new int[3];
		lightBrown[0] = 152;
		lightBrown[1] = 139;
		lightBrown[2] = 112;
		
		
		int[] orange = new int[3];
		orange[0] = 230;
		orange[1] = 139;
		orange[2] = 60;
		
		
		int[] yellow = new int[3];
		yellow[0] = 255;
		yellow[1] = 218;
		yellow[2] = 65;
		
	
		int[] darkGreen = new int[3];
		darkGreen[0] = 29;
		darkGreen[1] = 73;
		darkGreen[2] = 32;
	
		int[] dirtyGreen = new int[3];
		dirtyGreen[0] = 131;
		dirtyGreen[1] = 156;
		dirtyGreen[2] = 139;
	
		int[] green = new int[3];
		green[0] = 63;
		green[1] = 142;
		green[2] = 57;
	
		int[] neonGreen = new int[3];
		neonGreen[0] = 195;
		neonGreen[1] = 218;
		neonGreen[2] = 64;

		int[] navyBlue = new int[3];
		navyBlue[0] = 28;
		navyBlue[1] = 59;
		navyBlue[2] = 98;
	
	
		int[] blue = new int[3];
		blue[0] = 32;
		blue[1] = 107;
		blue[2] = 184;
	
	
		int[] lightBlue = new int[3];
		lightBlue[0] = 130;
		lightBlue[1] = 171;
		lightBlue[2] = 220;
	
	
		int[] gunmetalGray = new int[3];
		gunmetalGray[0] = 126;
		gunmetalGray[1] = 145;
		gunmetalGray[2] = 170;
	
	
		int[] purple = new int[3];
		purple[0] = 150;
		purple[1] = 61;
		purple[2] = 130;
	
	
		int[] brightPurple = new int[3];
		brightPurple[0] = 170;
		brightPurple[1] = 134;
		brightPurple[2] = 204;
	
		int[] lightPurple = new int[3];
		lightPurple[0] = 196;
		lightPurple[1] = 177;
		lightPurple[2] = 129;
	//its a long list.
	
	//adds the arrays. three numbers per array. 21 arrays.
		//assings a number to each color.
		allColors = new int[21][3];
	
		allColors[0] = black;
		allColors[1] = darkGrey;
		allColors[2] = grey;
		allColors[3] = white;
		allColors[4] = darkRed;
		allColors[5] = red;
		allColors[6] = brown;
		allColors[7] = lightBrown;
		allColors[8] = orange;
		allColors[9] = yellow;
		allColors[10] = darkGreen;
		allColors[11] = dirtyGreen;
		allColors[12] = green;
		allColors[13] = neonGreen;
		allColors[14] = navyBlue;
		allColors[15] = blue;
		allColors[16] = lightBlue;
		allColors[17] = gunmetalGray;
		allColors[18] = purple;
		allColors[19] = brightPurple;
		allColors[20] = lightPurple;		
	
	}
	
		/*
		  
		  backup of the numbers 
		  
Black,32,47,57
Dark-Grey,112,112,112
Grey,168,168,168
White,255,255,255
Dark-Red,119,26,26
Red,189,46,27
Brown,102,57,20
Light-Brown,152,139,112
Orange,230,139,60
Yellow,255,218,65
Dark-Green,29,73,32
Dirty-Green,131,156,139
Green,63,142,57
Neon-Green,195,218,64
Navy-Blue,28,59,98
Blue,32,107,184
Light-Blue,130,171,220
Gunmetal-Gray,126,145,170
Purple,150,61,130
Bright-Purple,170,134,204
Light-Purple,196,177,129

		*/ 
		
	
	
	
	
}//end brace