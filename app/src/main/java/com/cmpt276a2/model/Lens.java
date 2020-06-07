package com.cmpt276a2.model;

public class Lens {
    private String make;
    private double maxAperture;
    private int focalLength;
    private int idIcon;


    public Lens (String make, double maxAperture, int focalLength, int idIcon) {
        this.make = make;
        this.maxAperture = maxAperture;
        this.focalLength = focalLength;
        this.idIcon = idIcon;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public double getMaxAperture() {
        return maxAperture;
    }

    public void setMaxAperture(double maxAperture) {
        this.maxAperture = maxAperture;
    }

    public int getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    @Override
    public String toString() {
        return  make + " " + focalLength + "mm F" + maxAperture;
    }
}
