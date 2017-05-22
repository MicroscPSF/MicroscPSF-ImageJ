package org.hijizhou.core;

import ij.IJ;
import ij.ImageJ;
import ij.ImageStack;
import ij.ImagePlus;

public class Demo_GibsonLanni {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ImageJ();
		
		GibsonLanni gl = new GibsonLanni();

		long startTime = System.currentTimeMillis();
		
		gl.setNumBasis(100);
		gl.setNumSamp(1000);
		ImageStack stack = gl.compute();
		
		long endTime = System.currentTimeMillis();
		System.out.println("===Total:" + (endTime - startTime));

		ImagePlus ipPSF= new ImagePlus("PSF ", stack);
		ipPSF.show();
		IJ.run("Fire");  //lut
//		System.out.println("end!");
	}

}
