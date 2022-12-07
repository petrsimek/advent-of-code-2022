import java.io.File

/**
 * Advent of Code 2022 - Day 7 - https://adventofcode.com/2022/day/7
 */
object Day7 {
    val inputList = File("src/main/resources/input7.txt").readText(Charsets.UTF_8).split("\n")

    class Dir {
        var parent: Dir? = null
        var name: String = ""
        var size: Long = 0L
        var subdirs: MutableList<Dir> = mutableListOf()
    }

    private fun constructTree(inputList: List<String>): Dir {
        val topDir = Dir().apply { name = "/" }
        var currentDir = topDir
        for (row in 1 until inputList.size) {
            val parts = inputList.get(row).split(" ")

            if (parts[0] == "$") {
                if (parts[1] == "cd") {
                    currentDir = if (parts[2] == "..") currentDir.parent!! else currentDir.subdirs.first { it.name == parts[2] }
                }
            } else if (parts[0] == "dir") {
                currentDir.subdirs.add(Dir().apply { name = parts[1];parent = currentDir })
            } else if (parts[0] != "ls") {
                currentDir.size += parts[0].toLong()
            }
        }
        calculateSizes(topDir)
        return topDir
    }

    fun calculateSizes(dir: Dir) {
        dir.subdirs.forEach {
            calculateSizes(it)
        }
        dir.size = dir.size + dir.subdirs.map { it.size }.sum()
    }

    fun getFolderSizes(dir: Dir): List<Long> {
        return dir.subdirs.map { getFolderSizes(it) }.flatten().toMutableList().plus(dir.size)
    }

    private fun partOne(inputList: List<String>): Long {
        val topDir = constructTree(inputList)
        return getFolderSizes(topDir).filter { it <= 100000 }.sum()
    }

    private fun partTwo(inputList: List<String>): Long {
        val topDir = constructTree(inputList)
        return getFolderSizes(topDir).sorted().first { 70000000L - topDir.size + it >= 30000000L }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: " + partOne(inputList))
        println("Part 2: " + partTwo(inputList))
    }
}