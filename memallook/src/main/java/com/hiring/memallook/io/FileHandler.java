package com.hiring.memallook.io;

import com.hiring.memallook.data.Memory;
import com.hiring.memallook.data.MemoryBlock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class to handle all writes and reads to and from files.
 */
public class FileHandler {
    private static final String FILE_LOCATION = String.join(System.getProperty("file.separator"),
            new String[]{System.getProperty("user.home"), ".config", "memallook", "memory"});
    private static final String IO_ERROR_MESSAGE = "Trouble writing to the file " + FILE_LOCATION +
                    ". Please check permissions then try again.";
    public FileHandler() {
    }

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

    public boolean removeMemoryFile() {
        return new File(FILE_LOCATION).delete();
    }
}
