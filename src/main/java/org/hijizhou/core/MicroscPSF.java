package org.hijizhou.core;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.GUI;
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

public class MicroscPSF extends JDialog implements ChangeListener, ActionListener,
ItemListener, WindowListener, TextListener, Runnable {

private String defaultMessage = "(c) 2017 CUHK";
private WalkBar walk = new WalkBar(this.defaultMessage, true, false, true);
private JLabel labNxyz = new JLabel("Size (X, Y, Z)");
private JTextField txtNx = new JTextField("256", 6);
private JTextField txtNy = new JTextField("256", 6);
private JTextField txtNz = new JTextField("64", 6);

private JLabel labNA = new JLabel("Numerical Aperture");
private JTextField txtNA = new JTextField("1.4", 6);

private JLabel labLambda = new JLabel("Wavelength (nm)");
private JTextField txtLambda = new JTextField("610", 6);

private JLabel labNs = new JLabel("Specimen RI");
private JTextField txtNs = new JTextField("1.33", 6);

private JLabel labNg = new JLabel("Coverslip RI");
private JTextField txtNg = new JTextField("1.5", 6);

private JLabel labNi = new JLabel("Immersion RI");
private JTextField txtNi = new JTextField("1.5", 6);

private JLabel labTg = new JLabel("Coverslip thickness (um)");
private JTextField txtTg = new JTextField("170", 6);

private JLabel labTi = new JLabel("Working distance (um)");
private JTextField txtTi = new JTextField("150", 6);

private JLabel labPz = new JLabel("Particle position (nm)");
private JTextField txtPz = new JTextField("2000", 6);

private JLabel labLateral = new JLabel("Lateral size (nm)");
private JTextField txtLateral = new JTextField("100", 6);

private JLabel labAxial = new JLabel("Axial size (nm)");
private JTextField txtAxial = new JTextField("250", 6);

private JLabel labBasis = new JLabel("Basis Number");
private JTextField txtBasis = new JTextField("100", 6);

private JLabel labSamp = new JLabel("Sampling Number");
private JTextField txtSamp = new JTextField("1000", 6);

private JButton bnRun = new JButton("Run");

	public MicroscPSF(){
		super(new Frame(), "Fast Microscopic PSF Generation");
		this.walk
		.fillAbout(
				"Demo",
				"Beta 22/05/2017",
				"Microscopic PSF Generation",
				"Department of Electronic Engineering<br/>The Chinese University of Hong Kong",
				"Jizhou Li (hijizhou@gmail.com)",
				"2017",
				"<p style=\"text-align:left\"><b>References:</b><br>[1] J. Li, F. Xue and T. Blu, “Fast and Accurate 3D PSF Computation for Fluorescence Microscopy”, J. Opt. Soc. Am. A, vol. 34, no. 6, 2017.<br>[2] S. F. Gibson and F. Lanni, “Experimental test of an analytical model of aberration in an oil-immersion objective lens used in three-dimensional light microscopy”, J. Opt. Soc. Am. A, vol. 9, no. 1, pp. 154-166, 1992.<br><br><b>Acknowledgements:</b><br>PSF Generator");
		GridPanel pnControls = new GridPanel("PSF settings");
		pnControls.place(0, 0, this.labNxyz);
		pnControls.place(0, 1, this.txtNx);
		pnControls.place(0, 2, this.txtNy);
		pnControls.place(0, 3, this.txtNz);
	
		pnControls.place(1, 0, this.labNA);
		pnControls.place(1, 1, this.txtNA);
		pnControls.place(1, 2, this.labLambda);
		pnControls.place(1, 3, this.txtLambda);
		
		pnControls.place(2, 0, this.labNs);
		pnControls.place(2, 1, this.txtNs);
		pnControls.place(2, 2, this.labNg);
		pnControls.place(2, 3, this.txtNg);
		
		pnControls.place(3, 0, this.labNi);
		pnControls.place(3, 1, this.txtNi);
		pnControls.place(3, 2, this.labTg);
		pnControls.place(3, 3, this.txtTg);
		
		pnControls.place(4, 0, this.labTi);
		pnControls.place(4, 1, this.txtTi);
		pnControls.place(4, 2, this.labPz);
		pnControls.place(4, 3, this.txtPz);
		
		pnControls.place(5, 0, this.labLateral);
		pnControls.place(5, 1, this.txtLateral);
		pnControls.place(5, 2, this.labAxial);
		pnControls.place(5, 3, this.txtAxial);
		
		GridPanel pnParameter = new GridPanel("Approximation settings");
		pnParameter.place(0, 0, this.labBasis);
		pnParameter.place(0, 1, this.txtBasis);
		pnParameter.place(0, 2, this.labSamp);
		pnParameter.place(0, 3, this.txtSamp);
		
		
		GridPanel pnRun = new GridPanel();
		
		pnRun.place(0, 0, bnRun);
		this.walk.getButtonClose().addActionListener(this);
		this.bnRun.addActionListener(this);
		addWindowListener(this);
		
		GridPanel pnMain = new GridPanel(false, 7);
		int row = 0;
		pnMain.place(row++, 0, pnControls);
		pnMain.place(row++, 0, pnParameter);
		pnMain.place(row++, 0, pnRun);
		pnMain.place(row++, 0, this.walk);
		
		add(pnMain);
		pack();
		GUI.center(this);
		setVisible(true);
		IJ.wait(0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GibsonLanni gl = new GibsonLanni();
		this.walk.reset();
		this.walk.setMessage("Generating....");
		IJ.showStatus("Processing...");
		this.bnRun.setText("Stop");
				
		// set parameters
		
		gl.setNx(new Integer(txtNx.getText()).intValue());
		gl.setNy(new Integer(txtNy.getText()).intValue());
		gl.setNz(new Integer(txtNz.getText()).intValue());
		
		gl.setNA(new Double(txtNA.getText()).doubleValue());
		gl.setLambda(new Double(txtLambda.getText()).doubleValue()*1E-09D);
		gl.setNs(new Double(txtNs.getText()).doubleValue());
		gl.setNg(new Double(txtNg.getText()).doubleValue());
		gl.setNi(new Double(txtNi.getText()).doubleValue());
		
		gl.setTg(new Double(txtTg.getText()).doubleValue()*1E-06D);
		gl.setTi0(new Double(txtTi.getText()).doubleValue()*1E-06D);
		gl.setpZ(new Double(txtPz.getText()).doubleValue()*1E-09D);
		gl.setResLateral(new Double(txtLateral.getText()).doubleValue()*1E-09D);
		gl.setResAxial(new Double(txtAxial.getText()).doubleValue()*1E-09D);
		
		gl.setNumBasis(new Integer(txtBasis.getText()).intValue());
		gl.setNumSamp(new Integer(txtSamp.getText()).intValue());
		
		long startTime = System.currentTimeMillis();
		
		ImageStack stack = gl.compute();
		
		long endTime = System.currentTimeMillis();

		ImagePlus ipPSF = new ImagePlus("Computed PSF", stack);
		
		ipPSF.show();
		IJ.run("Fire");
		
		this.walk.setMessage("Computing time: " + (endTime - startTime) + " ms");
		this.bnRun.setText("Start");
		IJ.showStatus("Finished");
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {

		Class<?> clazz = MicroscPSF.class;

		// start ImageJ
		new ImageJ();

		// run the plugin
		// System.out.println(clazz.getName());
		IJ.runPlugIn(clazz.getName(), "");
	}

	
}



