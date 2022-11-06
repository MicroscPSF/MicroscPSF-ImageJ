package org.hijizhou.core;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.Macro;
import ij.gui.GUI;
import ij.plugin.PlugIn;

import org.hijizhou.utilities.GridPanel;
import org.hijizhou.utilities.WalkBar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/*
References:
	[1] J. Li, F. Xue and T. Blu, “Fast and Accurate 3D PSF Computation for Fluorescence Microscopy”, J. Opt. Soc. Am. A, vol. 34, no. 6, 2017.
	[2] S. F. Gibson and F. Lanni, “Experimental test of an analytical model of aberration in an oil-immersion objective lens used in three-dimensional light microscopy”, J. Opt. Soc. Am. A, vol. 9, no. 1, pp. 154-166, 1992.

Author: Jizhou Li (hijizhou@gmail.com)

*/

public class MicroscPSF_macro implements PlugIn {

	public MicroscPSF_macro(){
		
	}
	
	public static void main(String[] args) {

		Class<?> clazz = MicroscPSF_macro.class;

		// start ImageJ
		new ImageJ();

		// run the plugin
		// System.out.println(clazz.getName());
		IJ.runPlugIn(clazz.getName(), "");
	}

	@Override
	public void run(String arg) {
		// TODO Auto-generated method stub
GibsonLanni gl = new GibsonLanni();
		
		IJ.showStatus("Processing...");
		
		//String[] values = arg.split(",");
		
		String[] parameters =  new String[15];;
		String options = Macro.getOptions();
		String[] values = options.split(",");
		
		IJ.showStatus(" "+options);
		// extract paramters
		for(int i=0; i<15; i++) {
			parameters[i] = values[i].substring(values[i].lastIndexOf("=") + 1);
			IJ.showStatus(" "+parameters[i]);
		}
		
		gl.setNx(Integer.valueOf(parameters[0].replaceAll("\\s+","")));
		gl.setNy(Integer.valueOf(parameters[1].replaceAll("\\s+","")));
		gl.setNz(Integer.valueOf(parameters[2].replaceAll("\\s+","")));
		
		gl.setNA(Double.valueOf(parameters[3].replaceAll("\\s+","")));
		gl.setLambda(Double.valueOf(parameters[4].replaceAll("\\s+",""))*1E-09D);
		gl.setNs(Double.valueOf(parameters[5].replaceAll("\\s+","")));
		gl.setNg(Double.valueOf(parameters[6].replaceAll("\\s+","")));
		gl.setNi(Double.valueOf(parameters[7].replaceAll("\\s+","")));
		
		gl.setTg(Double.valueOf(parameters[8].replaceAll("\\s+",""))*1E-06D);
		gl.setTi0(Double.valueOf(parameters[9].replaceAll("\\s+",""))*1E-06D);
		gl.setpZ(Double.valueOf(parameters[10].replaceAll("\\s+",""))*1E-09D);
		gl.setResLateral(Double.valueOf(parameters[11].replaceAll("\\s+",""))*1E-09D);
		gl.setResAxial(Double.valueOf(parameters[12].replaceAll("\\s+",""))*1E-09D);
		
		gl.setNumBasis(Integer.valueOf(parameters[13].replaceAll("\\s+","")));
		gl.setNumSamp(Integer.valueOf(parameters[14].replaceAll("\\s+","")));
		
		long startTime = System.currentTimeMillis();
		
		ImageStack stack = gl.compute();
		
		long endTime = System.currentTimeMillis();

		ImagePlus ipPSF = new ImagePlus("Computed PSF", stack);
		
		ipPSF.show();
		IJ.run("Fire");
		
		IJ.showStatus("Finished");
	}

	
}



