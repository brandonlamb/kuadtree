package com.brandonlamb.kuadtree

import java.util.ArrayList

class Tree<T>(private val zone: Rectangle, private val level: Int) {
  private val nodes = mutableListOf<Node<T>>()
  private var regions: Array<Tree<T>> = emptyArray()

  /**
   * Insert an element into the  tree
   */
  fun insert(r: Rectangle, element: T) {
    val region = findRegion(r, true)


    if (region == REGION_SELF || level == MAX_LEVEL) {
      nodes.add(Node(r, element))
      return
    }

    regions[region].insert(r, element)

    if (nodes.size >= MAX_ITEM_BY_NODE && this.level < MAX_LEVEL) {
      // redispatch the elements
      val tempNodes = ArrayList<Node<T>>()
      val length = nodes.size

      for (i in 0 until length) {
        tempNodes.add(nodes[i])
      }

      nodes.clear()

      tempNodes.forEach { insert(it.r, it.element) }
    }
  }

  fun getElements(list: ArrayList<T>, r: Rectangle): ArrayList<T> {
    val region = findRegion(r, false)

    val length = nodes.size
    for (i in 0 until length) {
      list.add(nodes[i].element)
    }

    if (region != REGION_SELF) {
      regions[region].getElements(list, r)
    } else {
      getAllElements(list, true)
    }

    return list
  }

  fun getAllElements(list: ArrayList<T>, firstCall: Boolean): ArrayList<T> {
    if (regions.isNotEmpty()) {
      regions[REGION_NW].getAllElements(list, false)
      regions[REGION_NE].getAllElements(list, false)
      regions[REGION_SW].getAllElements(list, false)
      regions[REGION_SE].getAllElements(list, false)
    }

    if (!firstCall) {
      val length = nodes.size
      for (i in 0 until length) {
        list.add(nodes[i].element)
      }
    }

    return list
  }

  fun getAllZones(list: ArrayList<Rectangle>) {
    list.add(this.zone)
    if (regions.isNotEmpty()) {
      regions[REGION_NW].getAllZones(list)
      regions[REGION_NE].getAllZones(list)
      regions[REGION_SW].getAllZones(list)
      regions[REGION_SE].getAllZones(list)
    }
  }

  private fun split() {
    val newWidth = zone.width / 2
    val newHeight = zone.height / 2
    val newLevel = level + 1

    regions = arrayOf(
      // REGION_NW
      Tree(Rectangle(zone.x, zone.y + zone.height / 2, newWidth, newHeight), newLevel),

      // REGION_NE
      Tree(Rectangle(zone.x + zone.width / 2, zone.y + zone.height / 2, newWidth, newHeight), newLevel),

      // REGION_SW
      Tree(Rectangle(zone.x, zone.y, newWidth, newHeight), newLevel),

      // REGION_SE
      Tree(Rectangle(zone.x + zone.width / 2, zone.y, newWidth, newHeight), newLevel)
    )
  }

  private fun findRegion(r: Rectangle, split: Boolean): Int {
    var region = REGION_SELF

    if (nodes.size >= MAX_ITEM_BY_NODE && level < MAX_LEVEL) {
      // we don't want to split if we just need to retrieve
      // the region, not inserting an element
      if (regions.isEmpty() && split) {
        // then create the subregions
        split()
      }

      // can be null if not splitted
      if (regions.isEmpty()) {
        return region
      }

      when {
        regions[REGION_NW].zone.contains(r) -> region = REGION_NW
        regions[REGION_NE].zone.contains(r) -> region = REGION_NE
        regions[REGION_SW].zone.contains(r) -> region = REGION_SW
        regions[REGION_SE].zone.contains(r) -> region = REGION_SE
      }
    }

    return region
  }

  companion object {
    var MAX_ITEM_BY_NODE = 5
    var MAX_LEVEL = 10

    private const val REGION_SELF = -1
    private const val REGION_NW = 0
    private const val REGION_NE = 1
    private const val REGION_SW = 2
    private const val REGION_SE = 3
  }
}

