package benchmark.executer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyWriter {
    protected final String basePath = "benchmark/benchmarks/";
    protected final String delimiter = ",";

    protected void writeResultToFile(
        SortedMap<Integer, Long> result, String dir, String fileNameAvg, String header
    ) {
        //        System.out.println(dir);
        //        System.out.println(dir.isEmpty() + " " + createDir(dir) + " " +
        //        Files.isDirectory(Path.of(dir)) + " " + Files.exists(Path.of(dir)));
        if (dir.isEmpty() ||
            !(createDir(basePath + dir) || Files.isDirectory(Path.of(basePath + dir)))) {
            System.out.println("Failed to create dir, and it does not exits!");
            return;
        }
        String path = basePath + dir + "/" + fileNameAvg + ".csv";
        BufferedWriter writer = getWriter(path);
        if (writer == null) return;
        writeToFile(writer, header);

        for (Map.Entry<Integer, Long> entry : result.entrySet())
            writeToFile(writer, entry.getKey(), entry.getValue());

        closeWriter(writer, path);
    }

    private void writeToFile(BufferedWriter writer, String str) {
        try {
            writer.write(str);
            writer.newLine();
        } catch (IOException ex) { ex.printStackTrace(); }
    }
    private void writeToFile(BufferedWriter writer, Integer size, Long time) {
        writeToFile(writer, size + delimiter + time);
    }

    private void closeWriter(BufferedWriter writer, String fileName) {
        try {
            writer.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private BufferedWriter getWriter(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.createNewFile()) throw new IOException();
        } catch (IOException ex) {
            System.out.println("Error creating: " + file.getName());
            ex.printStackTrace();
            return null;
        }

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new PrintWriter(file));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }

        return writer;
    }

    private boolean createDir(String name) {
        File file = new File(name);
        return file.mkdirs();
    }
    public boolean createDirInBase(String name) {
        File file = new File(basePath + name);
        return file.mkdirs();
    }

    /*
     * Andere functies
     */

    public void writeDifference(
        SortedMap<Integer, Long> result1, SortedMap<Integer, Long> result2, String dir,
        String fileName, String header
    ) {
        SortedMap<Integer, Long> result = new TreeMap<>();
        for (Map.Entry<Integer, Long> entry : result1.entrySet()) {
            result.put(entry.getKey(), entry.getValue() - result2.get(entry.getKey()));
        }

        writeResultToFile(result, dir, fileName, header);
    }
}
