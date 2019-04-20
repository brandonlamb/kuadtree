package com.brandonlamb.kuadtree

data class Node<T> (val r: Rectangle, val element: T) {
  override fun toString(): String = "$r"
}
