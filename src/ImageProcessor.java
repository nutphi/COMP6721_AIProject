import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageProcessor {

	/**
	 * Top view feature
	 * @param img BufferedImage that you want to get the feature
	 * @return Top view array of integer of the image
	 */
	public static ArrayList<Integer> getTopViewList(BufferedImage img)
	{
		ArrayList<Integer> topViewList = new ArrayList<>();
		for(int i = 0; i<img.getWidth();++i)
		{
			boolean isWhite=false;
			for(int j = 0; j<img.getHeight();++j)
			{
				try{
				Color c = new Color(img.getRGB(j, i));
				if(c.equals(Color.WHITE))
				{
					topViewList.add(j);
					isWhite=true;
					break;
				}
				}catch(Exception e)
				{
					System.out.println("i:"+i+",j:"+j);
				}
			}
			if(!isWhite)
				topViewList.add(img.getHeight());
		}
		return topViewList;
	}
	
	/**
	 * Projection feature
	 * @param img BufferedImage that you want to get the feature
	 * @return Array of projection profile of the image
	 */
	public static ArrayList<Integer> getProjectionList(BufferedImage img)
	{
		ArrayList<Integer> projectionList = new ArrayList<>();
		for(int i = 0; i<img.getWidth();++i)
		{
			int whiteNum = 0;
			for(int j = 0; j<img.getHeight();++j)
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
	 * Create empty image
	 * @param width Width of the empty image
	 * @param height Height of the empty image
	 * @return Empty image
	 */
	public static BufferedImage createBufferedImage(int width, int height)
	{
		return new BufferedImage(width,height, BufferedImage.TYPE_BYTE_GRAY);
	}

	/**
	 * Copy BufferedImage Object
	 * @param img BufferedImage that you want to copy
	 * @return Copied BufferedImage Object
	 */
	public static BufferedImage copyBufferedImageWithBorder(BufferedImage img)
	{
		BufferedImage newImg = new BufferedImage(img.getWidth()+2,img.getHeight()+2, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 1; i < img.getWidth(); i++) {
			for (int j = 1; j < img.getHeight(); j++) {
				try{
					newImg.setRGB(i+1, j+1, img.getRGB(i, j));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (int i=0; i < newImg.getWidth();i++){

			newImg.setRGB(i, 0, Color.BLACK.getRGB());
			newImg.setRGB(i, newImg.getHeight()-1, Color.BLACK.getRGB());
		}
		for (int i=0; i < newImg.getHeight();i++){
			newImg.setRGB(0, i, Color.BLACK.getRGB());
			newImg.setRGB(newImg.getWidth()-1, i, Color.BLACK.getRGB());
		}

		return newImg;
	}
	
	/**
	 * 
	 * @param inputFile File that you want to get image
	 * @return Image from the input image file
	 */
	public static BufferedImage load(File inputFile)
	{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * 
	 * @param img Image that you want to save
	 * @param outputFile Image
	 * @return true if the file is saved; otherwise false
	 */
	public static boolean save(BufferedImage img ,File outputFile)
	{
		try {
			ImageIO.write(img, "jpg", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
