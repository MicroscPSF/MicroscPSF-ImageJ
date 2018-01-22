package org.hijizhou.core;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;

public class Demo_GibsonLanni {

    public static void main(String[] args) {

        new ImageJ();

        GibsonLanni gl = new GibsonLanni();

        long startTime = System.currentTimeMillis();

        gl.setNumBasis(200);
        gl.setNumSamp(1000);
        ImageStack stack = gl.compute();

        long endTime = System.currentTimeMillis();
        System.out.println("Running time: " + (endTime - startTime) / 1.0E3D);

//
        ImagePlus ipPSF = new ImagePlus("PSF ", stack);
        ipPSF.show();
        IJ.run("Fire");  //lut
		System.out.println("end!");
    }

}
