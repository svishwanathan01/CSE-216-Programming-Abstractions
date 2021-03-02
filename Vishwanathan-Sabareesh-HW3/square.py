from rectangle import Rectangle
from quadrilateral import Quadrilateral
import math


class Square(Rectangle):

    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A square cannot be formed by the given coordinates.")

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

    def snap(self):
        """Snaps the sides of the square such that each corner (x,y) is modified to be a corner (x',y') where x' is the
        integer value closest to x and y' is the integer value closest to y. This, of course, may change the shape to a
        general quadrilateral, hence the return type. The only exception is when the square is positioned in a way where
        this approximation will lead it to vanish into a single point. In that case, a call to snap() will not modify
        this square in any way."""

        x_values = [self.vertices[0].x, self.vertices[1].x, self.vertices[2].x, self.vertices[3].x]
        y_values = [self.vertices[0].y, self.vertices[1].y, self.vertices[2].y, self.vertices[3].y]


        for i in range(4):
            if x_values[i] == 0 or isinstance(x_values[i], int):
                pass
            elif x_values[i] - math.floor(x_values[i]) < 0.5:
                x_values[i] = math.floor(x_values[i])
            else:
                x_values[i] = math.ceil(x_values[i])

        for i in range(4):
            if y_values[i] == 0 or isinstance(y_values[i], int):
                pass
            elif y_values[i] - math.floor(y_values[i]) < 0.5:
                y_values[i] = math.floor(y_values[i])
            else:
                y_values[i] = math.ceil(y_values[i])

        if x_values[0] == x_values[1] == x_values[2] == x_values[3] and \
                y_values[0] == y_values[1] == y_values[2] == y_values[3]:
            return self

        # for i in range(4):
        #     print(x_values[i], y_values[i])

        return Quadrilateral(x_values[0], y_values[0],
                             x_values[1], y_values[1],
                             x_values[2], y_values[2],
                             x_values[3], y_values[3])  # TODO


    def __is_member(self):
        side_lengths = self.side_lengths()
        if len(self.vertices) != 4:
            return False

        side = side_lengths[0]
        for l in side_lengths:
            if l != side:
                return False

        if self.vertices[0].y != self.vertices[1].y or self.vertices[2].y != self.vertices[3].y or \
                self.vertices[0].x != self.vertices[3].x or self.vertices[1].x != self.vertices[2].x:
            return False
        return True

