# Image Processing

> Simple utility to manipulate images

[GitHub Project](https://github.com/rigon/ImageProc) - [Download](https://github.com/rigon/ImageProc/releases/latest/download/ImageProc.jar)

![Screenshot](screenshot.jpg)

## Features

- View image channels
  - [RGB](https://en.wikipedia.org/wiki/RGB_color_model) (red, green, blue)
  - [HSI](https://en.wikipedia.org/wiki/HSL_and_HSV) (hue, saturation, intensity)
  - [YCbCr](https://en.wikipedia.org/wiki/YCbCr)
- Show image histograms
  - [RGB](https://en.wikipedia.org/wiki/RGB_color_model) (red, green, blue)
  - [HSI](https://en.wikipedia.org/wiki/HSL_and_HSV) (hue, saturation, intensity)
  - [YCbCr](https://en.wikipedia.org/wiki/YCbCr)
- Apply manipulations to the image
  - Convert to grayscale
  - Invert colors
  - Mirror X
  - Mirror Y
  - Use HSI values as RGB
  - Intensity slicing
  - Contrast stretching
  - Contrast stretching 5% - 95%
  - Contrast stretching - Histogram Equalization
- Apply filters over the image
  - For edge detection: [Derivative](https://en.wikipedia.org/wiki/Image_derivative), [Sobel](https://en.wikipedia.org/wiki/Sobel_operator), Sobel horizontal, Sobel vertical, [Roberts](https://en.wikipedia.org/wiki/Roberts_cross)
  - For de-noising: [Gaussian](https://en.wikipedia.org/wiki/Gaussian_filter), [Mean](https://en.wikipedia.org/wiki/Geometric_mean_filter), [Median](https://en.wikipedia.org/wiki/Median_filter)
  - For smoothing: [Erosion](https://en.wikipedia.org/wiki/Erosion_(morphology)), [Dilation](https://en.wikipedia.org/wiki/Dilation_(morphology)), Erosion and Dilation of the grayscale
- Detection using OpenCV:
  - Face
  - Eyes
  - Mouth

## How to run:

You'll need:
 - [Java](https://openjdk.org/) installed in your system
 - [Download the latest build](https://github.com/rigon/ImageProc/releases/latest/download/ImageProc.jar) of the project
 - Then, just run:

       java -jar ImageProc.jar

## How to build

This project uses Maven to manage dependencies. In fact, only [OpenCV](https://github.com/openpnp/opencv) is required.

To install them:

    mvn install

To create the runnable JAR file with dependencies:

    mvn clean package
