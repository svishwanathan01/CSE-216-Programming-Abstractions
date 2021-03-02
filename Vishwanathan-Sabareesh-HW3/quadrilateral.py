import math

from two_d_point import TwoDPoint


class Quadrilateral:

    def __init__(self, *floats):
        points = TwoDPoint.from_coordinates(list(floats))
        self.__vertices = tuple(points[0:4])

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.vertices == other.vertices
        return False

    def __ne__(self, other):
        return not self.__eq__(other)

    def __str__(self):
        return '(%g, %g), (%g, %g), (%g, %g), (%g, %g)' % (
            self.__vertices[0].x, self.__vertices[0].y, self.__vertices[1].x, self.__vertices[1].y,
            self.__vertices[2].x, self.__vertices[2].y, self.__vertices[3].x, self.__vertices[3].y)

    @property
    def vertices(self):
        return self.__vertices

    def side_lengths(self):
        """Returns a tuple of four floats, each denoting the length of a side of this quadrilateral. The value must be
        ordered clockwise, starting from the top left corner."""
        sides = []
        sides.append(math.sqrt(
            (self.vertices[0].x - self.vertices[1].x) ** 2 + (self.vertices[0].y - self.vertices[1].y) ** 2))
        sides.append(math.sqrt(
            (self.vertices[2].x - self.vertices[1].x) ** 2 + (self.vertices[2].y - self.vertices[1].y) ** 2))
        sides.append(math.sqrt(
            (self.vertices[2].x - self.vertices[3].x) ** 2 + (self.vertices[2].y - self.vertices[3].y) ** 2))
        sides.append(math.sqrt(
            (self.vertices[0].x - self.vertices[3].x) ** 2 + (self.vertices[0].y - self.vertices[3].y) ** 2))

        return tuple(sides)  # TODO

    def smallest_x(self):
        """Returns the x-coordinate of the vertex with the smallest x-value of the four vertices of this
        quadrilateral."""
        min_x = self.__vertices[0].x
        for vert in self.__vertices:
            if min_x > vert.x:
                min_x = vert.x

        return min_x  # TODO
