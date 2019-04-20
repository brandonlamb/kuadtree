package com.brandonlamb.kuadtree

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RectangleTest {
  @Test
  fun `Should contain r2`() {
    val r1 = Rectangle(0f, 0f, 10f, 10f)
    val r2 = Rectangle(0f, 0f, 10f, 10f)

    // contains
    assertTrue(r1.contains(r2))
  }

  @Test
  fun `Should not contain r2`() {
    val r1 = Rectangle(0f, 0f, 10f, 10f)
    val r2 = Rectangle(0f, 0f, 11f, 10f)

    // dont contains
    r2.width = 11f
    assertFalse(r1.contains(r2))
  }
}
