import unittest
from unittest import TestCase
from quadrilateral import Quadrilateral
import math


class TestQuadrilateral(TestCase):

    def setUp(self) -> None:
        self.q1 = Quadrilateral(5, 5, 2, 5, 1, 0, 4, 0)
        self.q2 = Quadrilateral(5, 5, 2, 5, 1, 0, 4, 0)
        self.q3 = Quadrilateral(5, 5, 2, 5, 1, 0, 4, 1)

    def test_side_lengths(self):
        self.assertEqual(self.q1.side_lengths(),(3.0, math.sqrt(26), 3.0, math.sqrt(26))) # TODO

    def test_smallest_x(self):
        self.assertEqual(self.q1.smallest_x(), 1)  # TODO

    def test_eq(self):
        self.assertTrue(self.q1.__eq__(self.q2))
        self.assertFalse(self.q1.__eq__(self.q3))
        self.assertFalse(self.q3.__eq__(self.q2))

    def test_ne(self):
        self.assertTrue(self.q1.__ne__(self.q3))
        self.assertTrue(self.q2.__ne__(self.q3))
        self.assertFalse(self.q1.__ne__(self.q2))

    def test_str(self):
        self.assertEqual(self.q1.__str__(), "(5, 5), (2, 5), (1, 0), (4, 0)")


if __name__ == '__main__':
    unittest.main()

