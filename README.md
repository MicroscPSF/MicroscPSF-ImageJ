This is an Eclipse project of the ImageJ plugin for implementing the fast microscopic PSF (specifically, the Gibson-Lanni model) generation tool. Technical details can be found at [here](http://www.ee.cuhk.edu.hk/~jzli/MicroscPSF/). [The plugin](https://github.com/hijizhou/MicroscPSF-ImageJ/raw/master/target/MicroscPSF_IJ-0.1.0-SNAPSHOT-sources.jar) itself can be downloaded from the `target` folder. 

**References**:
-----------

- [1] J. Li, F. Xue and T. Blu, “Fast and Accurate Three-Dimensional Point Spread Function Computation for Fluorescence Microscopy”, J. Opt. Soc. Am. A, vol. 34, no. 6, pp. 1029-1034, 2017.
- [2] S. F. Gibson and F. Lanni, “Experimental test of an analytical model of aberration in an oil-immersion objective lens used in three-dimensional light microscopy”, J. Opt. Soc. Am. A, vol. 9, no. 1, pp. 154-166, 1992.

**Related**
-----------

It is intended as a starting point to develop a comprehensive ImageJ plugin for the PSF generation. The core codes have been finished. A nice GUI is needed for better interactions. You are encouraged to be involved in this project.

* Matlab code: https://github.com/hijizhou/MicroscPSF-Matlab.

* ImageJ plugin: https://github.com/hijizhou/MicroscPSF-ImageJ.

* Icy plugin: https://github.com/hijizhou/MicroscPSF-Icy.

* Python code (by [Dr. Kyle Douglass](http://kmdouglass.github.io/)): http://kmdouglass.github.io/posts/implementing-a-fast-gibson-lanni-psf-solver-in-python.html

**Screenshot**
-----------
![GUI of MicroscPSF](screen.png?raw=true "GUI")

**Call by ImageJ Macro Language**
-----------
```run("MicroscPSF_macro","nx=256,ny=256,nz=64,na=1.4,lambda=610,ns=1.33,ng=1.5,ni=1.5,tg=170,ti=150,pz=2000,lateral=100,axial=250,basis=100,samp=1000")```
- updated on 6/11/2022, note that the argements need to follow this format at this moment.

**Contact**
-----------
Jizhou Li (hijizhou@gmail.com)

**Acknowledgement**
-----------
[PSFgenerator](http://bigwww.epfl.ch/algorithms/psfgenerator/)

**License**
-----------
Copyright (c) 2017, Jizhou Li, Feng Xue and Thierry Blu

The source code is released under the [MIT license](https://opensource.org/licenses/MIT).
