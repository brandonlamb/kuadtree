package com.brandonlamb.kuadtree

import org.junit.Test
import java.lang.System.currentTimeMillis
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.measureTimeMillis

class TreePerfTest {
  @Test
  fun `Should insert elements`() {
//    val maxTest = 1_000_000
    val maxTest = 100
    val maxActors = 1_000
    val min = 0.01
    val max = 9_999.99

    Tree.MAX_ITEM_BY_NODE = 5
    Tree.MAX_LEVEL = 5

    val actors = mutableListOf<Actor>()
    for (n in 0 until maxActors) {
      val actor = Actor(n, Vector2(generate(min, max), generate(min, max)))
      actors.add(actor)
//      println("Actor: ${actor.position.x}, ${actor.position.y}")
    }

    val startTime = currentTimeMillis()

    for (i in 0 until maxTest) {
      val runtime = measureTimeMillis {
        val tree = Tree<Actor>(Rectangle(0f, 0f, 10_000f, 10_000f), 0)

        val x1 = measureTimeMillis {
          actors.forEach { actor -> tree.insert(Rectangle(actor.position.x, actor.position.y, 1f, 1f), actor) }
        }
//        println("tree.insert: ${x1}ms")

        val list = mutableListOf<Actor>()
        val x2 = measureTimeMillis {
          tree.getElements(list, Rectangle(2f, 2f, 1f, 1f))

          list.clear()
          tree.getElements(list, Rectangle(4f, 2f, 1f, 1f))
        }
//        println("tree.getElements: ${x2}ms")

//      val zoneList = mutableListOf<Rectangle>()
//      val x3 = measureNanoTime { tree.getAllZones(zoneList) }
//      println("tree.getAllZones: ${x3}ms")
      }
      println("Iteration: ${runtime}ms")
    }

    println("Total execution time for $maxTest iterations: ${currentTimeMillis() - startTime}ms")
  }

  private fun generate(min: Double, max: Double): Float {
    val x = ThreadLocalRandom.current().nextDouble(min, max).toFloat()
    return "%.2f".format(x).toFloat()
  }
}
