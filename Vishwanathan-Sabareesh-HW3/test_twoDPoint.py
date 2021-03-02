import unittest
from unittest import TestCase
from two_d_point import TwoDPoint

class TestTwoDPoint(TestCase):

    def setUp(self) -> None:
        self.p1 = TwoDPoint(1,2)
        self.p2 = TwoDPoint(1, 2)
        self.p3 = TwoDPoint(3, 2)

    def test_from_coordinates(self):
        list_of_points = [TwoDPoint(0, 4), TwoDPoint(6, 4), TwoDPoint(6, 0), TwoDPoint(0, 0)]
        self.assertEqual(TwoDPoint.from_coordinates([0, 4, 6, 4, 6, 0, 0, 0]), list_of_points) # TODO

    def test_eq(self):
        self.assertTrue(self.p1.__eq__(self.p2))
        self.assertFalse(self.p1.__eq__(self.p3))
        self.assertFalse(self.p3.__eq__(self.p2))

    def test_ne(self):
        self.assertTrue(self.p1.__ne__(self.p3))
        self.assertTrue(self.p2.__ne__(self.p3))
        self.assertFalse(self.p1.__ne__(self.p2))

    def test_str(self):
        self.assertEqual(self.p1.__str__(), "(1, 2)")

    def test_add(self):
        self.assertEqual(self.p1.__add__(self.p3), TwoDPoint(4, 4))

    def test_sub(self):
        self.assertEqual(self.p3.__sub__(self.p1), TwoDPoint(2, 0))

if __name__ == '__main__':
    unittest.main()