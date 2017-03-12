import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class Preprocess {
	
	public void binarized(BufferedImage img)
	{	
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color c = new Color(img.getRGB(j, i));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int grayscale = (r+g+b)/3;
				if(grayscale>=240)
					img.setRGB(j, i, Color.BLACK.getRGB());
				else
					img.setRGB(j, i, Color.WHITE.getRGB());
			}
		}
	}
	
	public void smoothing(BufferedImage img)
	{
		for (int k = 0; k < 1;++k)
			for (int i = 1; i < img.getHeight()-1; ++i)
			{
				for (int j = 1; j < img.getWidth()-1; ++j)
				{
					
					boolean a = new Color(img.getRGB(j-1, i-1)).getRed()==0?false:true;
					boolean b = new Color(img.getRGB(j-1, i)).getRed()==0?false:true;
					boolean c = new Color(img.getRGB(j-1, i+1)).getRed()==0?false:true;
					boolean d = new Color(img.getRGB(j, i-1)).getRed()==0?false:true;
					boolean e = new Color(img.getRGB(j, i+1)).getRed()==0?false:true;
					boolean f = new Color(img.getRGB(j+1, i-1)).getRed()==0?false:true;
					boolean g = new Color(img.getRGB(j+1, i)).getRed()==0?false:true;
					boolean h = new Color(img.getRGB(j+1, i+1)).getRed()==0?false:true;
					if (!(((a || b || d) && (e || g || h)) || ((b || c || e) && (d || f || g))))
					{
						img.setRGB(j,i,Color.BLACK.getRGB());
					}
				}
			}

	}
	
	//need to fix
	public BufferedImage normalize(BufferedImage img)
	{
		BufferedImage imgBorder= ImageProcessor.copyBufferedImageWithBorder(img);
		int afterwidth = 10;
		int afterheight = 64;
		double w_alpha1 = afterwidth / (imgBorder.getWidth()-2);
		double h_beta1 = afterheight / (imgBorder.getHeight()-2);

		BufferedImage normalizedImg = ImageProcessor.createBufferedImage(afterwidth, afterheight);
		int old_width = 0;
		int old_height = 0;
		for (int i = 1; i <= afterheight; ++i)
		{
			for (int j = 1; j <= afterwidth; ++j)
			{
				try{
				old_width = (int) Math.floor(j/w_alpha1);
				old_height = (int) Math.floor(i/ h_beta1);
				int rgb = imgBorder.getRGB(old_width,old_height);
				normalizedImg.setRGB(i-1, j-1, rgb);
				}catch(Exception e)
				{
					System.out.println(old_height);
					System.out.println(old_width);
					System.out.println("...");
				}
			}
		}
		return normalizedImg;
	}
	
	
	
	public static void main(String[] args) {
		Preprocess p = new Preprocess();
		BufferedImage b = ImageProcessor.load(new File("0/ID00002_p1_B12_GL.png"));
		p.binarized(b);
		p.smoothing(b);
		p.normalize(b);
		ImageProcessor.save(b, new File("image1.jpg"));
		for(int a :ImageProcessor.getTopViewList(b))
		{
			System.out.println(a);
		}
	}
}
