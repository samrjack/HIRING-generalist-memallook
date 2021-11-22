package com.hiring.memallook.data;

/**
 * A class to track individual memory blocks in the simulated memory.
 */
public class MemoryBlock {

    // A unique identifying tag for the block
    public String tag;

    // The start of the block's area (inclusive)
    public int start;

    // The end of the block's area (exclusive)
    public int end;

    public MemoryBlock(String tag, int start, int end) {
        this.tag = tag;
        this.start = start;
        this.end = end;
    }
}
