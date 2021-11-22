package com.hiring.memallook.data;

public class MemoryBlock {

    public String tag;
    public int start;
    public int end;

    public MemoryBlock(String tag, int start, int end) {
        this.tag = tag;
        this.start = start;
        this.end = end;
    }
}
