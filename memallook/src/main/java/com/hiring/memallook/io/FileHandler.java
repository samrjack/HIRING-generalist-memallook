package com.hiring.memallook.io;

import com.hiring.memallook.data.Memory;
import com.hiring.memallook.data.MemoryBlock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * A class to handle all writes and reads to and from files.
 */
public class FileHandler {
    private static final String FILE_LOCATION = String.join(System.getProperty("file.separator"),
            new String[]{System.getProperty("user.home"), ".config", "memallook", "memory"});
    private static final String IO_ERROR_MESSAGE = "Trouble writing to the file " + FILE_LOCATION +
                    ". Please check permissions then try again.";

    /**
     * Writes the memory model to disk.
     * @param mem the memory model to be written.
     * @throws IOException File cannot be written to.
     */
    public void writeMemoryToFile(Memory mem) throws IOException {
        removeMemoryFile();
        File outputFile = new File(FILE_LOCATION);
        outputFile.getParentFile().mkdirs();
        outputFile.createNewFile();
        try (FileWriter fw = new FileWriter(FILE_LOCATION)) {

            fw.write(String.valueOf(mem.numPages));
            fw.write("\n");
            fw.write(String.valueOf(mem.pageSize));
            fw.write("\n");
            fw.write(mem.getMostRecentTag());
            fw.write("\n");

            for (MemoryBlock block : mem.memoryBlockList) {
                String blockData = String.join(",",
                        new String[]{block.tag, String.valueOf(block.start), String.valueOf(block.end)});
                fw.write(blockData);
                fw.write("\n");
            }
        } catch (IOException e) {
            throw new IOException(IO_ERROR_MESSAGE, e);
        }
    }

    /**
     * Reads in the memory model from file.
     * @return The current memory model that's on file
     * @throws IOException No file found or cannot be read
     */
    public Memory readMemoryFromFile() throws IOException {
        Scanner in = new Scanner(new File(FILE_LOCATION));

        int numPages = Integer.parseInt(in.nextLine());
        int pageSize = Integer.parseInt(in.nextLine());
        String nextTag = in.nextLine();

        Memory mem = new Memory(numPages, pageSize);
        mem.setMostRecentTag(nextTag);
        while (in.hasNext()) {
            Scanner memBlockScanner = new Scanner(in.nextLine());
            memBlockScanner.useDelimiter(",");
            String tag = memBlockScanner.next();
            int start = Integer.parseInt(memBlockScanner.next());
            int end = Integer.parseInt(memBlockScanner.next());
            mem.memoryBlockList.add(new MemoryBlock(tag, start, end));
        }
        return mem;
    }

    /**
     * Removes the model from memory. The model can no longer be accessed after this is completed.
     * @return true if deleted successfully, false otherwise.
     */
    public boolean removeMemoryFile() {
        return new File(FILE_LOCATION).delete();
    }
}
