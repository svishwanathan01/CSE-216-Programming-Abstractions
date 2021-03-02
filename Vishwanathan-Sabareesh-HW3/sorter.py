from quadrilateral import Quadrilateral


class ShapeSorter:

    @staticmethod
    def sort(*args: Quadrilateral):
        return sorted(list(args), key=Quadrilateral.smallest_x)
