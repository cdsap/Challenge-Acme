package com.cdsap.challenge.app.photo

import android.graphics.Bitmap

class LRUImageCache(private val capacity: Int) {

    private val elements = mutableMapOf<String, Node>()
    private val start = Node("", null)
    private val end = Node("", null)
    private var sizeCache = capacity

    init {
        start.next = end
        end.previous = start
    }

    fun get(key: String): Bitmap? {
        if (elements.containsKey(key)) {
            val node = elements[key]!!
            remove(node)
            addAtTheEnd(node)
            return node.image
        }
        return null
    }

    fun put(key: String, image: Bitmap) {
        if (elements.containsKey(key)) {
            remove(elements[key]!!)
        }
        val newNode = Node(key, image)
        elements[key] = newNode
        addAtTheEnd(newNode)
        if (sizeCache > capacity) {
            val nodeToRemove = start.next
            remove(nodeToRemove!!)
            elements.remove(nodeToRemove.key)
        }
    }

    private fun remove(node: Node) {
        updateSizeCache(node, Operation.REMOVE)
        val next = node.next
        val previous = node.previous
        previous?.next = next
        next?.previous = previous
    }

    private fun addAtTheEnd(node: Node) {
        updateSizeCache(node, Operation.ADD)
        val endPrevious = end.previous
        endPrevious?.next = node
        end.previous = node
        node.next = end
        node.previous = endPrevious
    }


    @Synchronized private fun updateSizeCache(node: Node, operation: Operation) {
        if (node.image != null) {
            val size = node.image.byteCount / 1024
            when (operation) {
                Operation.ADD -> sizeCache += size
                Operation.REMOVE -> sizeCache -= size
            }
        }
    }
}

enum class Operation {
    ADD,
    REMOVE
}

data class Node(val key: String, val image: Bitmap?) {
    var next: Node? = null
    var previous: Node? = null
}

