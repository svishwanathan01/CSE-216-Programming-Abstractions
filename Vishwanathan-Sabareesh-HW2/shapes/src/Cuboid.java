import java.util.ArrayList;
import java.util.List;

// TODO : a missing interface method must be implemented in this class to make it compile. This must be in terms of volume().
public class Cuboid implements ThreeDShape {

    private final ThreeDPoint[] vertices = new ThreeDPoint[8];

    /**
     * Creates a cuboid out of the list of vertices. It is expected that the vertices are provided in
     * the order as shown in the figure given in the homework document (from v0 to v7).
     * 
     * @param vertices the specified list of vertices in three-dimensional space.
     */
    public Cuboid(List<ThreeDPoint> vertices) {
        if (vertices.size() != 8)
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                                                             this.getClass().getName()));
        int n = 0;
        for (ThreeDPoint p : vertices) this.vertices[n++] = p;
    }

    @Override
    public double volume() {
        double length, width, height = 0;

        length = Math.abs(vertices[2].getX() - vertices[3].getX());
        width = Math.abs(vertices[2].getY() - vertices[7].getY());
        height = Math.abs(vertices[2].getZ() - vertices[1].getZ());

        return (length * width * height);// TODO
    }

    @Override
    public ThreeDPoint center() {
        double xSum = 0, ySum = 0, zSum = 0;
        for(int i = 0; i < 8; i++) {
            xSum += Math.abs(vertices[i].getX());
            ySum += Math.abs(vertices[i].getY());
            zSum += Math.abs(vertices[i].getZ());
        }

        ThreeDPoint center = new ThreeDPoint((xSum/8), (ySum/8), (zSum/8) );
        return center; // TODO
    }

    @Override
    public int compareTo(ThreeDShape o) {
        if(this.volume() == o.volume())
            return 0;
        else if(this.volume() > o.volume())
            return 1;
        else
            return -1;
    }

    public double surfaceArea() {
        double l = 0, w = 0, h = 0;
        l = Math.abs(vertices[2].getX() - vertices[3].getX());
        w = Math.abs(vertices[3].getY() - vertices[4].getY());
        h = Math.abs(vertices[1].getZ() - vertices[3].getZ());

        return 2*((l*w)+ (w*h) + (l*h));
    }
    public static Cuboid random(){
        List<ThreeDPoint> randomCuboid = new ArrayList<>();

        ThreeDPoint zeroPoint = new ThreeDPoint(Math.random()*101, Math.random()*101, Math.random()*101);
        randomCuboid.add(zeroPoint);
        ThreeDPoint onePoint = new ThreeDPoint(zeroPoint.getX() - Math.random()*101, zeroPoint.getY(), zeroPoint.getZ());
        randomCuboid.add(onePoint);
        ThreeDPoint twoPoint = new ThreeDPoint(onePoint.getX(), onePoint.getY(), onePoint.getZ()  - Math.random()*101);
        randomCuboid.add(twoPoint);
        ThreeDPoint threePoint = new ThreeDPoint(zeroPoint.getX(), twoPoint.getY(), twoPoint.getZ());
        randomCuboid.add(threePoint);
        ThreeDPoint fourPoint = new ThreeDPoint(threePoint.getX(), threePoint.getY() + Math.random()*101, threePoint.getZ());
        randomCuboid.add(fourPoint);
        ThreeDPoint fivePoint = new ThreeDPoint(fourPoint.getX(), fourPoint.getY(), fourPoint.getZ() + Math.random() * 101);
        randomCuboid.add(fivePoint);
        ThreeDPoint sixPoint = new ThreeDPoint(onePoint.getX(), fivePoint.getY() , fivePoint.getZ());
        randomCuboid.add(sixPoint);
        ThreeDPoint sevenPoint = new ThreeDPoint(sixPoint.getX(), fourPoint.getY(), fourPoint.getZ());
        randomCuboid.add(sevenPoint);

        return new Cuboid(randomCuboid);
    }



}
