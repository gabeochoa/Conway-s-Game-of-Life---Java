import java.awt.Container;
import java.io.IOException;


public class GameMain 
{
	public static void main(String[] args) throws IOException, InterruptedException
	{
		View viewpane = new View(750,772);
		Container pane = viewpane.getContentPane();
		while(true)
		{
			viewpane.nextGen();
			Thread.sleep(250);
			viewpane.draw(pane.getGraphics());
		}
	}
}
