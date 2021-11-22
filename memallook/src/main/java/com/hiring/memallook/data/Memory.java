package com.hiring.memallook.data;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    public final int numPages;
    public final int pageSize;
    public List<MemoryBlock> memoryBlockList = new ArrayList<>();

    public Memory(int numPages, int pageSize) {
        this.numPages = numPages;
        this.pageSize = pageSize;
    }
}
