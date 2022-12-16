package utils

/**
 * Author: njuro - AoC 2021 - https://github.com/njuro/advent-of-code-2021/tree/master/src/main/kotlin/utils
 */
data class Coordinate(val x: Int, val y: Int) {
    fun up(delta: Int = 1, offset: Boolean = false): Coordinate {
        return if (offset) down(delta, false) else copy(y = y + delta)
    }

    fun right(delta: Int = 1): Coordinate {
        return copy(x = x + delta)
    }

    fun down(delta: Int = 1, offset: Boolean = false): Coordinate {
        return if (offset) up(delta, false) else copy(y = y - delta)
    }

    fun left(delta: Int = 1): Coordinate {
        return copy(x = x - delta)
    }

    fun move(direction: Direction, offset: Boolean = false): Coordinate {
        return when (direction) {
            Direction.UP -> up(offset = offset)
            Direction.RIGHT -> right()
            Direction.DOWN -> down(offset = offset)
            Direction.LEFT -> left()
        }
    }

    fun adjacent(offset: Boolean = false): Map<Direction, Coordinate> {
        return Direction.values().associateWith { move(it, offset) }
    }

    operator fun plus(other: Coordinate): Coordinate {
        return Coordinate(x + other.x, y + other.y)
    }

    operator fun minus(other: Coordinate): Coordinate {
        return Coordinate(x - other.x, y - other.y)
    }
}