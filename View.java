import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class View extends JFrame
{
	int x;
	int y;
	int[][] board;
	Point playerLoc;
	BufferedImage bg;
	BufferedImage enemy;
	//BufferedImage player;
	
	
	public View(int x, int y) throws IOException
	{
		super("Dots Game");
		this.x = x;
		this.y = y;
		this.setBounds(100, 90, x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = this.getContentPane();
		pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		//playerLoc = new Point();
		board = new int[x/25][y/25];
		fillAra();
		//this.addKeyListener(new KBListener(playerLoc, board));
		bg = ImageIO.read(new File("background.png"));
		enemy = ImageIO.read(new File("enemy.png"));
		//player = ImageIO.read(new File("player.png"));
		
		
		this.setVisible(true);
	}
	
	public void nextGen()
	{
		int count = 0;
		int counter = 0;
		boolean[] update = new boolean[900];
		for(int j = 0; j < board.length; j++)
		{
			for(int k = 0; k < board.length; k++, counter++, count = 0)
			{
				if(k != 29 && j != 29 && board[j+1][k+1] == 2)
					count++;
				if(j != 29 && k != 0 && board[j+1][k-1] == 2)
					count++;
				if(j != 29 && board[j+1][k] == 2)
					count++;
				if(k != 29 && board[j][k+1] == 2)
					count++;
				if(k != 0 && board[j][k-1] == 2)
					count++;
				if(j != 0 && k != 29 && board[j-1][k+1] == 2)
					count++;
				if(j != 0 && board[j-1][k] == 2)
					count++;
				if(j != 0 && k != 0 && board[j-1][k-1] == 2)
					count++;
				if(board[j][k] == 2) //logic rules here
				{	
					if(count < 2 || count > 3) //if cell has less than 2 neighbors or greater than 3 it dies
						update[counter] = false;
					else //if cell has 2 or 3 neighbors it lives
						update[counter] = true;
				}
				else if((count == 3) && board[j][k] == 0) // if cell is dead and has 3 neighbors it wakes up
				{
					update[counter] = true;
				}
			}
		}
		reFillAra(update);
	}
	
	public void reFillAra(boolean[] test)
	{
		int count = 0;
		for(int j = 0; j < board.length; j++)
		{
			for(int k = 0; k < board.length; k++, count++)
			{
				if(test[count])
					board[j][k] = 2;
				else
					board[j][k] = 0;
			}
		}
	}

	public void fillAra()
	{
		Random rand = new Random();
		for(int j = 0; j < board.length; j++)
		{
			for(int k = 0; k < board.length; k++)
			{
				int z = rand.nextInt(10);
				if(z <= 3)//starting values from rand that fill the ara
					board[j][k] = 2;
				else
					board[j][k] = 0;
			}
		}
	}
	
	private void fillBoard() throws FileNotFoundException 
	{
		Scanner reader = new Scanner(new File("board.txt"));
		
		for(int i = 0; i < x/25; i++)
		{
			for(int j = 0; j < x/25 ; j++)
			{
				board[i][j] = reader.nextInt();
				if(board[i][j] == 1)
				{
					playerLoc.x = j;
					playerLoc.y = i;
				}
				//System.out.println(board[i][j]);
			}
		}
		reader.close();
	}

	public void draw(Graphics g) 
	{
		int x1 = 0;
		int y1 = 0;
		//System.out.println(x/25);
		for(int i = 0; i < (x/25); i++, y1 = y1 + 25)
		{
			for(int j = 0; j < (x/25); j++, x1 = x1 + 25 )
			{
				//System.out.println("x1:" + x1 + ",y1: " + y1);
				switch(board[i][j])
				{
					case 0:  g.drawImage(bg, x1, y1, null); break;
					case 1:  g.drawImage(player, x1, y1, null); break;
					case 2:  g.drawImage(enemy, x1, y1, null);
				}
			}
			x1 = 0;
		}
		
	}
}
