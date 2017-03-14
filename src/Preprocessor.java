import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Preprocessor {
	
	public void binarize(BufferedImage img)
	{	
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color c = new Color(img.getRGB(j, i));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int grayScale = (r+g+b)/3;
				if(grayScale>=240)
					img.setRGB(j, i, Color.BLACK.getRGB());
				else
					img.setRGB(j, i, Color.WHITE.getRGB());
			}
		}
	}
	
	public void smooth(BufferedImage img) {
		//thinning
		for (int k = 0; k < 1; ++k) {
			for (int i = 1; i < img.getHeight() - 1; ++i) {
				for (int j = 1; j < img.getWidth() - 1; ++j) {
					boolean a = new Color(img.getRGB(j - 1, i - 1)).getRed() != 0;
					boolean b = new Color(img.getRGB(j - 1, i)).getRed() != 0;
					boolean c = new Color(img.getRGB(j - 1, i + 1)).getRed() != 0;
					boolean d = new Color(img.getRGB(j, i - 1)).getRed() != 0;
					boolean e = new Color(img.getRGB(j, i + 1)).getRed() != 0;
					boolean f = new Color(img.getRGB(j + 1, i - 1)).getRed() != 0;
					boolean g = new Color(img.getRGB(j + 1, i)).getRed() != 0;
					boolean h = new Color(img.getRGB(j + 1, i + 1)).getRed() != 0;
					if (!(((a || b || d) && (e || g || h)) || ((b || c || e) && (d || f || g)))) {
						img.setRGB(j, i, Color.BLACK.getRGB());
					}
				}
			}
		}
	}

	public BufferedImage normalize(BufferedImage img, int afterWidth, int afterHeight) {
		BufferedImage imgBorder= ImageProcessor.copyBufferedImageWithBorder(img);
		double w_alpha1 = afterWidth / (imgBorder.getWidth()-2);
		double h_beta1 = afterHeight / (imgBorder.getHeight()-2);

		BufferedImage normalizedImg = ImageProcessor.createBufferedImage(afterWidth, afterHeight);
		int old_width = 0;
		int old_height = 0;
		for (int i = 1; i <= afterHeight; ++i)
		{
			for (int j = 1; j <= afterWidth; ++j)
			{
				old_width = (int) Math.floor(j/w_alpha1);
				old_width = old_width > imgBorder.getWidth()-1?imgBorder.getWidth()-1:old_width;
				old_height = (int) Math.floor(i/ h_beta1);
				old_height = old_height > imgBorder.getHeight()-1?imgBorder.getHeight()-1:old_height;

				int rgb = 0;
				try{
					rgb = imgBorder.getRGB(old_width,old_height);
				}catch(Exception e)
				{
					System.out.println("...");
				}
				normalizedImg.setRGB(j-1, i-1, rgb);
			}
		}
		return normalizedImg;
	}
	
	public static void main(String[] args) {
		Preprocessor p = new Preprocessor();
		BufferedImage b = ImageProcessor.load(new File("0/ID00002_p1_B12_GL.png"));
		p.binarize(b);
		p.smooth(b);
		ImageProcessor.save(p.normalize(b,64,64), new File("image1.jpg"));
		ArrayList<Integer> topView = ImageProcessor.getTopViewList(p.normalize(b,64,64));
		System.out.println(topView.size());
		for(int n:topView)
		{
			System.out.println(":"+n);
		}
	}
}
