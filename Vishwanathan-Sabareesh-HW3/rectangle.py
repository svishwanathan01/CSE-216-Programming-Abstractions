from quadrilateral import Quadrilateral
from two_d_point import TwoDPoint


class Rectangle(Quadrilateral):

    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A rectangle cannot be formed by the given coordinates.")

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.vertices == other.vertices
        return False

    def __ne__(self, other):
        return not self.__eq__(other)

    def __str__(self):
        return '(%g, %g), (%g, %g), (%g, %g), (%g, %g)' % (
            self.vertices[0].x, self.vertices[0].y, self.vertices[1].x, self.vertices[1].y,
            self.vertices[2].x, self.vertices[2].y, self.vertices[3].x, self.vertices[3].y)

    def center(self):
        """Returns the center of this rectangle, calculated to be the point of intersection of its diagonals."""
        center = TwoDPoint((self.vertices[0].x + self.vertices[2].x)/2, (self.vertices[0].y + self.vertices[2].y)/2)
        return center# TODO

    def area(self):
        """Returns the area of this rectangle. The implementation invokes the side_lengths() method from the superclass,
        and computes the product of this rectangle's length and width."""
        side_lengths = self.side_lengths()
        return side_lengths[0]*side_lengths[1]  # TODO

    def __is_member(self):
        if len(self.vertices) != 4:
            return False
        if self.vertices[0].y != self.vertices[1].y or self.vertices[2].y != self.vertices[3].y or \
                self.vertices[0].x != self.vertices[3].x or self.vertices[1].x != self.vertices[2].x:
            return False
        return True  # TODO