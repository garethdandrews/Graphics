import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
/**
 * @author Gareth Andrews 880256
 * This work is my own, individual effort.
 */
public class Graphics extends JFrame {
	
	private JFrame frame, histogram_frame;
	private JButton invert_button, slow_gamma_button, fast_gamma_button, correlate_button, 
		equal_button, contrast_button, histogram_button, cross_correlation_button, reset_button;
	private JLabel image_icon;
	private JTextField slow_gamma_textfield, fast_gamma_textfield, contrast_r1_textfield, contrast_r2_textfield, 
		contrast_s1_textfield, contrast_s2_textfield;
	private BufferedImage image;
	private JSlider val_slider;
	private final File image_file = new File("raytrace.jpg");
	/**
	 * Set up the GUI and read an image.
	 */
	public void Graphics() throws IOException {
		image = ImageIO.read(image_file);

		frame = new JFrame();
		frame.setBounds(100, 100, 909, 501);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(false);

        image_icon = new JLabel(new ImageIcon(image));
        image_icon.setBounds(10, 10, 634, 455);
        frame.add(image_icon);
        
        invert_button = new JButton("Invert");
        invert_button.setBounds(654, 10, 240, 25);
        frame.add(invert_button);

        equal_button = new JButton("Equalize");
        equal_button.setBounds(654, 150, 240, 25);
        frame.add(equal_button);
        
        slow_gamma_button = new JButton("Slow Gamma");
        slow_gamma_button.setBounds(774, 45, 120, 25);
        frame.add(slow_gamma_button);
        fast_gamma_button = new JButton("Fast Gamma");
        fast_gamma_button.setBounds(774, 80, 120, 25);
        frame.add(fast_gamma_button);
        
        slow_gamma_textfield = new JTextField();
        slow_gamma_textfield.setBounds(654, 45, 116, 25);
        frame.add(slow_gamma_textfield);
        
        fast_gamma_textfield = new JTextField();
        fast_gamma_textfield.setBounds(654, 80, 116, 25);
        frame.add(fast_gamma_textfield);
        
        correlate_button = new JButton("Correlate");
        correlate_button.setBounds(654, 185, 120, 25);
        //frame.add(correlate_button);
		
        contrast_button = new JButton("Contrast");
        contrast_button.setBounds(774, 115, 120, 25);
        frame.add(contrast_button);
        
        contrast_r1_textfield = new JTextField("r1");
        contrast_r1_textfield.setBounds(654, 115, 29, 25);
        frame.add(contrast_r1_textfield);
        
        contrast_s1_textfield = new JTextField("s1");
        contrast_s1_textfield.setBounds(683, 115, 29, 25);
        frame.add(contrast_s1_textfield);
        
        contrast_r2_textfield = new JTextField("r2");
        contrast_r2_textfield.setBounds(712, 115, 29, 25);
        frame.add(contrast_r2_textfield);
        
        contrast_s2_textfield = new JTextField("s2");
        contrast_s2_textfield.setBounds(741, 115, 29, 25);
        frame.add(contrast_s2_textfield);
        
        histogram_button = new JButton("View Histogram");
        histogram_button.setBounds(654, 390, 240, 25);
        frame.add(histogram_button);
        
        cross_correlation_button = new JButton("Cross Correlation");
        cross_correlation_button.setBounds(654, 185, 240, 25);
        frame.add(cross_correlation_button);
        
        reset_button = new JButton("Reset");
        reset_button.setBounds(654, 425, 240, 40);
        frame.add(reset_button);
        
		val_slider = new JSlider(0,100);
		//frame.add(val_slider);
		val_slider.setMajorTickSpacing(50);
		val_slider.setMinorTickSpacing(10);
		val_slider.setPaintTicks(true);
		val_slider.setPaintLabels(true);
		//see
		//http://docs.oracle.com/javase/7/docs/api/javax/swing/JSlider.html
		//for documentation (e.g. how to get the value, how to display vertically if you want)
        		
        // Now all the handlers
        GUIEventHandler handler = new GUIEventHandler();

        // Button handlers
        invert_button.addActionListener(handler);
        slow_gamma_button.addActionListener(handler);
        fast_gamma_button.addActionListener(handler);
        correlate_button.addActionListener(handler);
        equal_button.addActionListener(handler);
        contrast_button.addActionListener(handler);
        histogram_button.addActionListener(handler);
        cross_correlation_button.addActionListener(handler);
        reset_button.addActionListener(handler);
		val_slider.addChangeListener(handler);
		
		//Display everything
        setLocationRelativeTo(null);
        frame.setVisible(true);
    }
	
	
	/*
     *	This is the event handler for the application
	 */
	private class GUIEventHandler implements ActionListener, ChangeListener {
		//Change handler (e.g. for sliders)
		public void stateChanged(ChangeEvent e) {
			System.out.println(val_slider.getValue());
			//you could pass the value to another function to change something
			//then update the image
		}

		public void actionPerformed(ActionEvent event) {
             if (event.getSource()==invert_button) {
            	 // Call image processing function
            	 image=Invert(image);
    
                 // Update image
            	 image_icon.setIcon(new ImageIcon(image));
             } else if (event.getSource()==slow_gamma_button) {
            	 double gamma = Double.parseDouble(slow_gamma_textfield.getText());
            	 // Call image processing function
                 image=SlowGamma(image, gamma);
    
                 // Update image
                 image_icon.setIcon(new ImageIcon(image));
             } else if (event.getSource()==fast_gamma_button) {
            	 double gamma = Double.parseDouble(fast_gamma_textfield.getText());
                 // Call image processing function
                 image=FastGamma(image, gamma);
    
                 // Update image
                 image_icon.setIcon(new ImageIcon(image));
             } else if (event.getSource()==correlate_button) {
                 // Call image processing function
                 image=BlueFade(image);
    
                 // Update image
                 image_icon.setIcon(new ImageIcon(image));
             } else if (event.getSource()==equal_button) {
            	 //Call function
            	 image=Equalise(image);
					
                 // Update image
            	 image_icon.setIcon(new ImageIcon(image));
			} else if (event.getSource()==contrast_button) {
				int r1 = Integer.parseInt(contrast_r1_textfield.getText());
				int s1 = Integer.parseInt(contrast_s1_textfield.getText());
				int r2 = Integer.parseInt(contrast_r2_textfield.getText());
				int s2 = Integer.parseInt(contrast_s2_textfield.getText());
				//Call image processing function
				image=ContrastStretching(image,r1,s1,r2,s2);
				
				//Update image
				image_icon.setIcon(new ImageIcon(image));
			} else if (event.getSource()==histogram_button) {
				//Display the histogram for the current image
				ViewHistogram(image);
			} else if (event.getSource()==cross_correlation_button) {
				//Call function
           	 	image=CrossCorrelation(image);
					
                // Update image
           	 	image_icon.setIcon(new ImageIcon(image));
			} else if (event.getSource()==reset_button) {
				try { //Open the image
					image = ImageIO.read(image_file);
				} catch (IOException e) {
					e.printStackTrace();
				} // Reset the image
				image_icon.setIcon(new ImageIcon(image));
			}
		}
	}
	
	
	/*
    This function will return a pointer to an array
    of bytes which represent the image data in memory.
    Using such a pointer allows fast access to the image
    data for processing (rather than getting/setting
    individual pixels)
	 */
	public static byte[] GetImageData(BufferedImage image) {
        WritableRaster WR=image.getRaster();
        DataBuffer DB=WR.getDataBuffer();
        if (DB.getDataType() != DataBuffer.TYPE_BYTE)
            throw new IllegalStateException("That's not of type byte");
      
        return ((DataBufferByte) DB).getData();
	}
	
	
	public BufferedImage Equalise(BufferedImage image) {
        //Get image dimensions, and declare loop variables
        int w=image.getWidth(), h=image.getHeight(), i, j, c, r, g, b, grey = 0;
        //Obtain pointer to data for fast processing
        byte[] data = GetImageData(image);
        
        int g_levels = 256;
        int cumulative = 0;
		int[] histogram = new int[g_levels];
		int[] mapping = new int[g_levels];
		int[][][] int_image = new int[h][w][3];
        
        // Copy byte data to new image taking care to treat bytes as unsigned
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			int_image[j][i][c]=data[c+3*i+3*j*w]&255;
                } // colour loop
            } // column loop
        } // row loop
        
        //Generate the grey histogram
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		//Calculate the grey value for the pixel
        		r = int_image[j][i][2];
        		g = int_image[j][i][1];
        		b = int_image[j][i][0];
        		grey = (r+g+b)/3;
        		
        		//Add one to the intensity level of grey
        		histogram[grey]++;
        		
        		//Assign the grey value to each colour channel
        		int_image[j][i][2] = grey;
        		int_image[j][i][1] = grey;
        		int_image[j][i][0] = grey;
        	}
        }
        
        // Copy the processed image back to the original
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			data[c+3*i+3*j*w]= (byte) int_image[j][i][c];
                } // colour loop
            } // column loop
        } // row loop
        
        // Perform the equalisation
        for (i=0; i<g_levels; i++) {
			cumulative += histogram[i];
			mapping[i] = Math.max(0, Math.round((g_levels*cumulative)/(h*w))-1);
		}
        
        //Assign the new intensity to the old intensity
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			data[c+3*i+3*j*w] = (byte) mapping[data[c+3*i+3*j*w]&255];
                } // colour loop
            } // column loop
        } // row loop
		
        return image;
	}
	
	/*
    	This function shows how to carry out an operation on an image.
    	It obtains the dimensions of the image, and then loops through
    	the image carrying out the invert.
	 */
	public BufferedImage Invert(BufferedImage image) {
        //Get image dimensions, and declare loop variables
        int w=image.getWidth(), h=image.getHeight(), i, j, c;
        //Obtain pointer to data for fast processing
        byte[] data = GetImageData(image);
        
        //Shows how to loop through each pixel and colour
        //Try to always use j for loops in y, and i for loops in x
        //as this makes the code more readable
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			data[c+3*i+3*j*w]=(byte) (255-(data[c+3*i+3*j*w]&255));
                } // colour loop
            } // column loop
        } // row loop
        
        return image;
	}
	
	
	public BufferedImage ContrastStretching(BufferedImage image, int r1, int s1, int r2, int s2) {
		int w=image.getWidth(), h=image.getHeight(), i, j, c;
        byte[] data = GetImageData(image);
        
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
          		for (c=0; c<3; c++) {			
                	if ((data[c+3*i+3*j*w]&255)<r1) {
                		data[c+3*i+3*j*w] = (byte) ((s1/r1)*(data[c+3*i+3*j*w]&255));
                	} else if (r1<=(data[c+3*i+3*j*w]&255) && (data[c+3*i+3*j*w]&255)<=r2) {
                		data[c+3*i+3*j*w] = (byte) (((s2-s1)/(r2-r1))*((data[c+3*i+3*j*w]&255)-r1)+s1);
                	} else if ((data[c+3*i+3*j*w]&255)>=r2) {
                		data[c+3*i+3*j*w] = (byte) (((255-s2)/(255-r2))*((data[c+3*i+3*j*w]&255)-r2)+s2);
                	}
                } // colour loop
        	} // column loop
        } // row loop
		
		return image;
	}
	

	public BufferedImage SlowGamma(BufferedImage image, double gamma) {
        //Get image dimensions, and declare loop variables
        int w=image.getWidth(), h=image.getHeight(), i, j, c;
        //Obtain pointer to data for fast processing
		byte[] data = GetImageData(image);
		
		double y = 1/gamma;
		
		for (j=0; j<h; j++) {
			for (i=0; i<w; i++) {
            	for (c=0; c<3; c++) {
            		data[c+3*i+3*j*w] = (byte) (255*(Math.pow(((data[c+3*i+3*j*w]&255)/255.0),y)));
                } // colour loop
			} // column loop
		} // row loop
		
        return image;
	}
	

	public BufferedImage FastGamma(BufferedImage image, double gamma) {
        //Get image dimensions, and declare loop variables
        int w=image.getWidth(), h=image.getHeight(), i, j, c;
        //Obtain pointer to data for fast processing
		byte[] data = GetImageData(image);
		
		double y = 1/gamma;
		int[][] lookup = new int[256][3];
		
		// Populate the array with the new possible intensities
		for (i=0; i<256; i++) {
			for (c=0; c<3; c++) {
				lookup[i][c] = (int) (255*(Math.pow(((i)/255.0),y)));
			}
		}
        
		for (j=0; j<h; j++) {
			for (i=0; i<w; i++) {
            	for (c=0; c<3; c++) {
            		data[c+3*i+3*j*w] = (byte) lookup[(data[c+3*i+3*j*w]&255)][c];
                } // colour loop
			} // column loop
		} // row loop

        return image;
	}

	
	public BufferedImage BlueFade(BufferedImage image) {
        //Get image dimensions, and declare loop variables
        int w=image.getWidth(), h=image.getHeight(), i, j, c;
        //Obtain pointer to data for fast processing
        byte[] data = GetImageData(image);
        int int_image[][][];
        
        int_image = new int[h][w][3];
        
        // Copy byte data to new image taking care to treat bytes as unsigned
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			int_image[j][i][c]=data[c+3*i+3*j*w]&255;
                } // colour loop
            } // column loop
        } // row loop
        
        // Now carry out processing on this different data typed image (e.g. correlation or "bluefade"
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		int_image[j][i][0]=255*j/h; //BLUE
                int_image[j][i][1]=0; //GREEN
                int_image[j][i][2]=0; //RED
            } // column loop
        } // row loop
        
        // Now copy the processed image back to the original
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			data[c+3*i+3*j*w]=(byte) int_image[j][i][c];
                } // colour loop
            } // column loop
        } // row loop
        
        return image;
	}
	
	
	public void ViewHistogram(BufferedImage image) {
		int w=image.getWidth(), h=image.getHeight(), i, j, c, r, g, b;
		int x;
        byte[] data = GetImageData(image);
        int int_image[][][] = new int[h][w][3];
        
        double[] red = new double[256];
        double[] green = new double[256];
        double[] blue = new double[256];
        double[] brightness = new double[256];
        
        Paint[] colors = {
        		new Color(0x80ff0000, true),
                new Color(0x8000ff00, true),
                new Color(0x800000ff, true),
                new Color(0x80000000, true)
        };
        
        // Copy byte data to new image taking care to treat bytes as unsigned
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			int_image[j][i][c]=data[c+3*i+3*j*w]&255;
        		}	
        	}	
        }
        
        //Assign the RGB values for each pixel
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		r = int_image[j][i][2];
        		g = int_image[j][i][1];
        		b = int_image[j][i][0];
        		
    			x = (int) Math.round(0.2126*r + 0.7152*g + 0.0722*b);
    			
    			red[r]++;
    			green[g]++;
    			blue[b]++;
    			brightness[x]++;
        	}
        }
        
        XYSeries red_series = new XYSeries("Red", false);
        XYSeries green_series = new XYSeries("Green", false);
        XYSeries blue_series = new XYSeries("Blue", false);
        XYSeries brightness_series = new XYSeries("Brightness", false);
        
        for (i=0; i<256; i++) {
        	red_series.add(i, red[i]);
        	green_series.add(i, green[i]);
        	blue_series.add(i, blue[i]);
        	brightness_series.add(i, brightness[i]);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(red_series);
        dataset.addSeries(green_series);
        dataset.addSeries(blue_series);
        dataset.addSeries(brightness_series);
        
        JFreeChart chart = ChartFactory.createHistogram("Histogram", "Value", "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        
        XYPlot plot = (XYPlot) chart.getPlot();
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        
        plot.setDrawingSupplier(new DefaultDrawingSupplier(colors, 
        		DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        
        histogram_frame = new JFrame();
        histogram_frame.add(chartPanel);
        histogram_frame.setBounds(100, 100, 909, 501);
        histogram_frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        histogram_frame.setVisible(true);
	}
 
	
	public BufferedImage CrossCorrelation(BufferedImage image) {
		//Get image dimensions, and declare loop variables
        int w=image.getWidth(), h=image.getHeight(), i, j, c, x, y;
        //Obtain pointer to data for fast processing
        byte[] data = GetImageData(image);
        int int_image[][][] = new int[h+2][w+2][3];
        
        int[][] matrix = {
        	{-4, -1, 0, -1, -4},
        	{-1, 2, 3, 2, -1},
        	{0, 3, 4, 3, 0},
        	{-1, 2, 3, 2, -1}, 
        	{-4, -1, 0, -1, -4}
        };
        
        // Move the pixels across 2 and down 2 in the array
        
        // Add black border to image so filter can be centered
        // Copy byte data to new image taking care to treat bytes as unsigned
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			if (i<2 || i>=w-2 || j<2 || j>=h-2) {
        				int_image[j][i][c] = 0;
        			} else {
        				int_image[j][i][c]=data[c+3*i+3*j*w]&255;
        			}
        		}	
        	}	
        }
        
        int max = -9999;
        int min = 9999;
        
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			if (i>=2 && i<w-2 && j>=2 && j<h-2) {
        				int sum = 0;
        				
        				for (y=0; y<5; y++) {
            				for (x=0; x<5; x++) {
            					sum += int_image[j+x][i+y][c] * matrix[x][y];
            				}
            			}
        				
        				int_image[j][i][c] = sum;
        				
        				if (sum > max) {
        					max = sum;
        				}
        				if (sum < min) {
        					min = sum;
        				}
        			}
        		}
        	}
        }
        
        
        // Now copy the processed image back to the original
        for (j=0; j<h; j++) {
        	for (i=0; i<w; i++) {
        		for (c=0; c<3; c++) {
        			int_image[j][i][c] = ((int_image[j][i][c] - min)*255)/(max-min);
        			data[c+3*i+3*j*w] = (byte) int_image[j][i][c];
                } // colour loop
            } // column loop
        } // row loop
        
		return image;
	}
	
	
	public static void main(String[] args) throws IOException {
		Graphics e = new Graphics();
		e.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		e.Graphics();
	}
}