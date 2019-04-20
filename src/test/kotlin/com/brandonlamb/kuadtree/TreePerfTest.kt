package com.brandonlamb.kuadtree

import org.junit.Test
import java.lang.System.currentTimeMillis

class TreePerfTest {
  @Test
  fun `Should insert elements`() {
    val startTime = currentTimeMillis()
    val maxTest = 1000000

    for (i in 0 until maxTest) {
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

      val list = mutableListOf<Rectangle>()
      tree.getElements(list, Rectangle(2f, 2f, 1f, 1f))

      val expected = mutableListOf<Rectangle>()
      expected.add(r1)
      expected.add(r5)
      expected.add(r2)
      expected.add(r3)

      list.clear()
      tree.getElements(list, Rectangle(4f, 2f, 1f, 1f))

      expected.clear()
      expected.add(r1)
      expected.add(r5)
      expected.add(r2)
      expected.add(r3)

      val zoneList = mutableListOf<Rectangle>()
      tree.getAllZones(zoneList)
    }

    println("Total execution time for $maxTest iterations: ${currentTimeMillis() - startTime}ms")
  }
}
