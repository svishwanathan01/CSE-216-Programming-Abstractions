import java.util.Arrays;
import java.util.List;

public class Quadrilateral implements Positionable, TwoDShape {

    private final TwoDPoint[] vertices = new TwoDPoint[4];

    public Quadrilateral(double... vertices) { 
        this(TwoDPoint.ofDoubles(vertices));
    }

    public Quadrilateral(List<TwoDPoint> vertices) {
        int n = 0;
        for (TwoDPoint p : vertices) this.vertices[n++] = p;
        if (!isMember(vertices))
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                                                             this.getClass().getCanonicalName()));
    }

    /**
     * Given a list of four points, adds them as the four vertices of this quadrilateral in the order provided in the
     * list. This is expected to be a counterclockwise order of the four corners.
     *
     * @param points the specified list of points.
     * @throws IllegalStateException if the number of vertices provided as input is not equal to four.
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        if(points.size() !=4)
            throw new IllegalStateException();

        for(int i = 0; i < points.size(); i++)
            vertices[i] = (TwoDPoint) points.get(i);
        // TODO
    }

    @Override
    public List<TwoDPoint> getPosition() {
        return Arrays.asList(vertices);
    }

    /**
     * @return the lengths of the four sides of the quadrilateral. Since the setter {@link Quadrilateral#setPosition(List)}
     *         expected the corners to be provided in a counterclockwise order, the side lengths are expected to be in
     *         that same order.
     */
    protected double[] getSideLengths() {
        double[] sideLengths = new double[4];
        sideLengths[0] = Math.abs(vertices[1].getX() - vertices[0].getX());
        sideLengths[1] = Math.abs(vertices[1].getY() - vertices[2].getY());
        sideLengths[2] = Math.abs(vertices[3].getX() - vertices[2].getX());
        sideLengths[3] = Math.abs(vertices[0].getY() - vertices[3].getY());

        return sideLengths; // TODO
    }

    @Override
    public int numSides() { return 4; }

    @Override
    public boolean isMember(List<? extends Point> vertices) { return vertices.size() == 4; }
}
