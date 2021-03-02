/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point {

    private double x, y, z;

    public ThreeDPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        double[] coordinates = new double[3];
        coordinates[0] = this.x;
        coordinates[1] = this.y;
        coordinates[2] = this.z;

        return coordinates; // TODO
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getZ() { return z; }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public void setZ(double z) { this.z = z; }
}
