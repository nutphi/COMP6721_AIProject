import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageProcessor {

	/**
	 * topview feature
	 * @param img
	 * @return
	 */
	public static ArrayList<Integer> getTopViewList(BufferedImage img)
	{
		ArrayList<Integer> topViewList = new ArrayList<Integer>();
		for(int i = 0; i<img.getWidth()-2;++i)
		{
			for(int j = 0; j<img.getHeight()-1;++j)
			{
				try{
				Color c = new Color(img.getRGB(j, i));
				if(c.equals(Color.WHITE))
				{
					topViewList.add(j);
					break;
				}
				}catch(Exception e)
				{
					System.out.println();
				}
			}
			
		}
		return topViewList;
	}
	
	/**
	 * projection feature
	 * @param img
	 * @return
	 */
	public static ArrayList<Integer> getProjectionList(BufferedImage img)
	{
		ArrayList<Integer> projectionList = new ArrayList<Integer>();
		for(int i = 0; i<img.getWidth()-2;++i)
		{
			int whiteNum = 0;
			for(int j = 0; j<img.getHeight()-1;++j)
			{
				Color c = new Color(img.getRGB(j, i));
				if(c.equals(Color.WHITE))
				{
					++whiteNum;
				}
			}
			projectionList.add(whiteNum);
		}
		return projectionList;
	}
	
	/**
	 * create empty image 
	 * @param width
	 * @param height
	 * @return
	 */
	
	public static BufferedImage createBufferedImage(int width, int height)
	{
		return new BufferedImage(width,height, BufferedImage.TYPE_BYTE_GRAY);
	}
	
	public static BufferedImage copyBufferedImageWithBorder(BufferedImage img)
	{
		BufferedImage newImg = new BufferedImage(img.getWidth()+2,img.getHeight()+2, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				try{
					newImg.setRGB(i+1, j+1, img.getRGB(i, j));
				}catch(Exception e)
				{
					System.out.println("...");
				}
				
			}
		}
		return newImg;
	}
	
	/**
	 * 
	 * @param inputfile
	 * @return
	 */
	public static BufferedImage load(File inputfile)
	{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(inputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * 
	 * @param img
	 * @param outputfile
	 * @return
	 */
	public static boolean save(BufferedImage img ,File outputfile)
	{
		try {
			ImageIO.write(img, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
