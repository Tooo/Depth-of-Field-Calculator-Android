package com.cmpt276a2.model;

public class DOFCalculator {

    // (focal length)^2 / (aperture/circle of confusion)
    public static double hyperFocalDist(Lens len, double aperture, double coc) {
        int focalLength = len.getFocalLength();
        double top = focalLength * focalLength;
        double bottom = aperture*coc;
        return top/bottom;
    }

    // (hyper focal * distance)/ (hyper focal + (distance - focal length))
    public static double nearFocalPoint(Lens len, double distance, double aperture, double coc) {
        double hyperFocal = hyperFocalDist(len, aperture, coc);
        double top = hyperFocal*distance;
        double bottom = (hyperFocal +(distance-len.getFocalLength()));
        return top/bottom;
    }

    //(hyper focal * distance)/ (hyper focal + (distance - focal length)
    public static double farFocalPoint(Lens len, double distance, double aperture, double coc) {
        double hyperFocal = hyperFocalDist(len, aperture, coc);
        double farFocal = Double.POSITIVE_INFINITY;
        if (distance <= hyperFocal ) {
            double top = hyperFocal * distance;
            double bottom = (hyperFocal - (distance - len.getFocalLength()));
            farFocal = top / bottom;
        }
        return farFocal;
    }

    // far focal - near focal
    public static double depthOfField (Lens len, double distance, double aperture, double coc) {
        double farFocal = farFocalPoint(len, distance, aperture, coc);
        double nearFocal = nearFocalPoint(len, distance, aperture, coc);
        return (farFocal-nearFocal);
    }

}
