import java.util.List;

public class Rectangle extends Quadrilateral implements SymmetricTwoDShape {
    private final TwoDPoint[] vertices = new TwoDPoint[4];

    public Rectangle(double... vertices) {
        this(TwoDPoint.ofDoubles(vertices));
    }

    public Rectangle(List<TwoDPoint> vertices){ super(vertices); }

    /**
     * The center of a rectangle is calculated to be the point of intersection of its diagonals.
     *
     * @return the center of this rectangle.
     */
    @Override
    public Point center() {
        TwoDPoint center = new TwoDPoint((vertices[0].getX() + vertices[2].getX())/2, (vertices[0].getY() + vertices[2].getY())/2);
        return null; // TODO
    }

    @Override
    public boolean isMember(List<? extends Point> vertices) {
        double x = ((TwoDPoint)vertices.get(0)).getX();
        double y = ((TwoDPoint)vertices.get(1)).getX();
        if(!(((TwoDPoint)vertices.get(0)).getX() == ((TwoDPoint)vertices.get(3)).getX() &&
                ((TwoDPoint)vertices.get(1)).getX() == ((TwoDPoint)vertices.get(2)).getX()))
            return false;
        else if(!(Math.abs(((TwoDPoint)vertices.get(1)).getX() - ((TwoDPoint)vertices.get(0)).getX()) ==
                Math.abs(((TwoDPoint)vertices.get(3)).getX() - ((TwoDPoint)vertices.get(2)).getX()) &&
                Math.abs(((TwoDPoint)vertices.get(1)).getY() - ((TwoDPoint)vertices.get(2)).getY()) ==
                        Math.abs(((TwoDPoint)vertices.get(0)).getY() - ((TwoDPoint)vertices.get(3)).getY())))
            return false;
        return true;// TODO
    }

    @Override
    public double area() {
        double[] sideLengths = getSideLengths();
        return sideLengths[0]*sideLengths[1]; // TODO
    }
}
