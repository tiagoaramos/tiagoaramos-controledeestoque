package br.com.tiagoaramos.estoque.view.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

public class PrintableLabel implements Printable {

	private File source;

	public PrintableLabel() throws BarcodeException, OutputException {
		super();
	}

	public PrintableLabel(File source) {
		super();

		this.source = source;

	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		int result = NO_SUCH_PAGE;
		if (pageIndex == 0) {

			graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

			result = PAGE_EXISTS;

			try {

				// You may want to rescale the image to better fit the label??
				BufferedImage read = ImageIO.read(source);
				graphics.drawImage(read, 0, 0, null);

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		return result;
	}

	public static String dump(PageFormat pf) {
		Paper paper = pf.getPaper();
		return dump(paper);
	}

	public static String dump(Paper paper) {
		StringBuilder sb = new StringBuilder(64);
		sb.append(paper.getWidth()).append("x").append(paper.getHeight()).append("/").append(paper.getImageableX())
				.append("x").append(paper.getImageableY()).append(" - ").append(paper.getImageableWidth()).append("x")
				.append(paper.getImageableHeight());
		return sb.toString();
	}

	public static double fromCMToPPI(double cm) {
		return toPPI(cm * 0.393700787);
	}

	public static double toPPI(double inch) {
		return inch * 72d;
	}

}