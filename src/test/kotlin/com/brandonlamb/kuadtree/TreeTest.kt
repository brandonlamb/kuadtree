package com.brandonlamb.kuadtree

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.ArrayList

class TreeTest {
  @Test
  fun `Should insert elements`() {
    Tree.MAX_ITEM_BY_NODE = 1
    Tree.MAX_LEVEL = 2

    val tree = Tree<Rectangle>(Rectangle(0f, 0f, 10f, 10f), 0)

    val r1 = Rectangle(1f, 1f, 1f, 1f)
    val r2 = Rectangle(2f, 2f, 1f, 1f)
    val r3 = Rectangle(4f, 4f, 1f, 1f)
    val r4 = Rectangle(6f, 6f, 1f, 1f)
    val r5 = Rectangle(4f, 4f, 2f, 2f)
    val r6 = Rectangle(0.5f, 6.5f, 0.5f, 0.5f)

    tree.insert(r1, r1)
    tree.insert(r2, r2)
    tree.insert(r3, r3)
    tree.insert(r4, r4)
    tree.insert(r5, r5)
    tree.insert(r6, r6)

    val list = ArrayList<Rectangle>()
    tree.getElements(list, Rectangle(2f, 2f, 1f, 1f))

    val expected = ArrayList<Rectangle>()
    expected.add(r1)
    expected.add(r5)
    expected.add(r2)
    expected.add(r3)

    assertEquals(expected, list)

    list.clear()
    tree.getElements(list, Rectangle(4f, 2f, 1f, 1f))

    expected.clear()
    expected.add(r1)
    expected.add(r5)
    expected.add(r2)
    expected.add(r3)

    val zoneList = ArrayList<Rectangle>()
    tree.getAllZones(zoneList)

    assertEquals(zoneList.size.toLong(), 9)
  }

  @Test
  fun testIntersectElementsAreInserted() {
    Tree.MAX_ITEM_BY_NODE = 1
    Tree.MAX_LEVEL = 2

    val tree = Tree<Rectangle>(Rectangle(0f, 0f, 10f, 10f), 0)

    val r1 = Rectangle(1f, 1f, 1f, 1f)
    val r2 = Rectangle(2f, 2f, 1f, 1f)

    tree.insert(r1, r1)
    tree.insert(r2, r2)

    var list = ArrayList<Rectangle>()
//    tree.getElements(list, Rectangle(2f, 2f, 1f, 1f))
    tree.getElements(list, r2)

    assertTrue(list.size == 2)

    val r3 = Rectangle(11f, 11f, 1f, 1f)

    list = ArrayList()
    tree.getElements(list, r2)
  }
}
