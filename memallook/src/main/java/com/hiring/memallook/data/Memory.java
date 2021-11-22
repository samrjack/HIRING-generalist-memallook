package com.hiring.memallook.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Memory {
    public final int numPages;
    public final int pageSize;
    private String mostRecentTag = "0";
    public List<MemoryBlock> memoryBlockList = new ArrayList<>();

    public Memory(int numPages, int pageSize) {
        this.numPages = numPages;
        this.pageSize = pageSize;
    }

    /**
     * Allocate a new chunk of memory and assign it the next valid Tag.
     * @param size the size of the chunk to be allocated in bytes.
     * @return empty if the block can't be allocated and the tag otherwise.
     */
    public Optional<String> allocate(int size) {
        // Number of pages needed to store at least SIZE bytes.
        int numberOfPages = (size + pageSize - 1) / numPages;

        if (numberOfPages > numPages)
            return Optional.empty();

        // check between the beginning of the memory space and the first allocated chunk first, then check
        // between the rest of the chunks
        if (this.memoryBlockList.size() == 0 || this.memoryBlockList.get(0).start >= numberOfPages) {
            memoryBlockList.add(0, new MemoryBlock(createNextTag(), 0, numberOfPages));
            return Optional.of(mostRecentTag);
        }

        for (int i = 1; i < memoryBlockList.size(); i++) {
            MemoryBlock curBlock = memoryBlockList.get(i);
            MemoryBlock prevBlock = memoryBlockList.get(i - 1);
            if (curBlock.start - prevBlock.end >= numberOfPages) {
                memoryBlockList.add(i - 1,
                        new MemoryBlock(createNextTag(),
                                prevBlock.end,
                                prevBlock.end + numberOfPages));
                return Optional.of(mostRecentTag);
            }
        }

        // Check if the final slot is big enough for the chunk
        MemoryBlock lastBlock = memoryBlockList.get(memoryBlockList.size() - 1);
        if (numPages - lastBlock.end > numberOfPages) {
            memoryBlockList.add( memoryBlockList.size(),
                    new MemoryBlock(createNextTag(),
                            lastBlock.end,
                            lastBlock.end + numberOfPages));
            return Optional.of(mostRecentTag);
        } else {
            return Optional.empty();
        }
    }

    public boolean deallocate(String tag) {
        for (int i = 0; i < memoryBlockList.size(); i++) {
            if (memoryBlockList.get(i).tag.equals(tag)) {
                memoryBlockList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Defragments the memory blocks to remove the empty space from between the allocated blocks.
     */
    public void defragment() {
        if(memoryBlockList.size() > 0) {
            MemoryBlock curBlk = memoryBlockList.get(0);
            curBlk.end -= curBlk.start;
            curBlk.start = 0;
        }
        for (int i = 1; i < memoryBlockList.size(); i++) {
            MemoryBlock curBlk = memoryBlockList.get(i);
            MemoryBlock prevBlk = memoryBlockList.get(i - 1);

            curBlk.end -= curBlk.start - prevBlk.end;
            curBlk.start = prevBlk.end;
        }
    }

    /**
     * Gets the most recently used tag in order to allow saving or referencing later.
     * @return the last tag used for an allocation.
     */
    public String getMostRecentTag() {
        return mostRecentTag;
    }

    /**
     * Sets the most recent to so that it can start in the middle of the tag options.
     * @param nextTag The tag to start calculating from.
     */
    public void setMostRecentTag(String nextTag) {
        this.mostRecentTag = nextTag;
    }

    /**
     * Calculates the next memory tag based on the current memory tag.
     * @return Next memory tag to be used.
     */
    private String createNextTag() {
        String newTag = Integer.toString(Integer.parseInt(mostRecentTag) + 1);
        mostRecentTag = newTag;
        return mostRecentTag;
    }
}
