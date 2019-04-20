package com.brandonlamb.kuadtree

data class Rectangle(var x: Float, var y: Float, var width: Float, var height: Float) {
  fun contains(r: Rectangle): Boolean = (
    width > 0 &&
      height > 0 &&
      r.width > 0 &&
      r.height > 0 &&
      r.x >= x &&
      r.x + r.width <= x + width &&
      r.y >= y &&
      r.y + r.height <= y + height
    )

  override fun toString(): String = "x: $x y: $y w: $width h: $height"
}
