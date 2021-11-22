package com.hiring.memallook.commands;

import com.google.common.collect.Lists;
import com.hiring.memallook.data.Memory;
import com.hiring.memallook.data.MemoryBlock;
import com.hiring.memallook.io.FileHandler;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandLine.Command(name = "show",
        description = "Prints the simulated memory to the screen in a human " +
                "readable fashion.")
public class Display implements Runnable {

    @Override
    public void run() {
        FileHandler fileHandler = new FileHandler();
        Memory mem;
        try {
            mem = fileHandler.readMemoryFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        List<String> outArray = new ArrayList<>(Collections.nCopies(mem.numPages, "."));

        mem.memoryBlockList
                .forEach(blk -> {
                    for (int i = blk.start; i < blk.end; i++) {
                        outArray.set(i, blk.tag);
                    }
                });
        List<List<String>> squaredArray = Lists.partition(outArray, 10);
        for (List<String> strings : squaredArray)
            System.out.println(String.join("", strings));

        System.out.println("");
        System.out.println("<Allocations by tags>");
        for (MemoryBlock blk : mem.memoryBlockList) {
            System.out.format("%s:\t%d bytes\n", blk.tag, (blk.end - blk.start) * mem.pageSize);
        }
    }
}
