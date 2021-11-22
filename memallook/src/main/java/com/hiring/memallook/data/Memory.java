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
            memoryBlockList.add(0, new MemoryBlock(calculateNextTag(), 0, numberOfPages));
            return Optional.of(mostRecentTag);
        }

        for (int i = 1; i < memoryBlockList.size(); i++) {
            MemoryBlock curBlock = memoryBlockList.get(i);
            MemoryBlock prevBlock = memoryBlockList.get(i - 1);
            if (curBlock.start - prevBlock.end >= numberOfPages) {
                memoryBlockList.add(i - 1,
                        new MemoryBlock(calculateNextTag(),
                                prevBlock.end,
                                prevBlock.end + numberOfPages));
                return Optional.of(mostRecentTag);
            }
        }

        // Check if the final slot is big enough for the chunk
        MemoryBlock lastBlock = memoryBlockList.get(memoryBlockList.size() - 1);
        if (numPages - lastBlock.end > numberOfPages) {
            memoryBlockList.add( memoryBlockList.size(),
                    new MemoryBlock(calculateNextTag(),
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

    public String getMostRecentTag() {
        return mostRecentTag;
    }
    public void setMostRecentTag(String nextTag) {
        this.mostRecentTag = nextTag;
    }

    private String calculateNextTag() {
        String newTag = Integer.toString(Integer.parseInt(mostRecentTag) + 1);
        mostRecentTag = newTag;
        return mostRecentTag;
    }

}
