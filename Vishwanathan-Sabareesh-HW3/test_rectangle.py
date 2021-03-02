import unittest
from unittest import TestCase
from rectangle import Rectangle
from two_d_point import TwoDPoint


class TestRectangle(TestCase):

    def setUp(self) -> None:
        self.r1 = Rectangle(6, 4, 2, 4, 2, 1, 6, 1)
        self.r2 = Rectangle(6, 4, 2, 4, 2, 1, 6, 1)
        self.r3 = Rectangle(5, 3, 1, 3, 1, 0, 5, 0)

    def test_eq(self):
        self.assertTrue(self.r1.__eq__(self.r2))
        self.assertFalse(self.r1.__eq__(self.r3))
        self.assertFalse(self.r3.__eq__(self.r2))

    def test_ne(self):
        self.assertTrue(self.r1.__ne__(self.r3))
        self.assertTrue(self.r2.__ne__(self.r3))
        self.assertFalse(self.r1.__ne__(self.r2))

    def test_str(self):
        self.assertEqual(self.r1.__str__(), "(6, 4), (2, 4), (2, 1), (6, 1)")

    def test_center(self):
        self.assertEqual(self.r1.center(), TwoDPoint(4, 2.5))

    def test_area(self):
        self.assertEqual(self.r1.area(), 12)

    def test__is_member(self):
        self.assertRaises(TypeError, (Rectangle, (6, 4, 2, 4, 2, 1, 6, 1)))
        self.assertRaises(TypeError, (Rectangle, ))


if __name__ == '__main__':
    unittest.main()