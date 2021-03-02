import unittest
from unittest import TestCase
from square import Square
from quadrilateral import Quadrilateral


class TestSquare(TestCase):

    def setUp(self) -> None:
        self.s1 = Square(6, 3, 3, 3, 3, 0, 6, 0)
        self.s2 = Square(6, 3, 3, 3, 3, 0, 6, 0)
        self.s3 = Square(7, 4, 4, 4, 4, 1, 7, 1)
        self.s4 = Square(-3.5, -3.5, -1.5, -3.5, -1.5, -1.5, -3.5, -1.5)
        self.s5 = Square(3.5, 3.5, 1.5, 3.5, 1.5, 1.5, 3.5, 1.5)

    def test_eq(self):
        self.assertTrue(self.s1.__eq__(self.s2))
        self.assertFalse(self.s1.__eq__(self.s3))
        self.assertFalse(self.s3.__eq__(self.s2))

    def test_ne(self):
        self.assertTrue(self.s1.__ne__(self.s3))
        self.assertTrue(self.s2.__ne__(self.s3))
        self.assertFalse(self.s1.__ne__(self.s2))

    def test_str(self):
        self.assertEqual(self.s1.__str__(), "(6, 3), (3, 3), (3, 0), (6, 0)")

    def test_snap(self):
        self.assertEqual(self.s1.snap(), Quadrilateral(6, 3, 3, 3, 3, 0, 6, 0))
        self.assertEqual(self.s4.snap(), Quadrilateral(-3, -3, -1, -3, -1, -1, -3, -1))
        self.assertEqual(self.s5.snap(), Quadrilateral(4, 4, 2, 4, 2, 2, 4, 2))

    def test__is_member(self):
        self.assertRaises(TypeError, (Square, (6, 3, 3, 3, 3, 0, 6, 0)))
        self.assertRaises(TypeError, (Square, (-3.5, -3.5, -1.5, -3.5, -1.5, -1.5, -3.5, -1.5)))
        self.assertRaises(TypeError, (Square, ))



if __name__ == '__main__':
    unittest.main()