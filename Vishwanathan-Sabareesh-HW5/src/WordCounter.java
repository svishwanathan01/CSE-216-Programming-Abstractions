//Sabareesh Vishwanathan
//CSE 216 HW #5


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class WordCounter {

    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES = Paths.get("input_1607386389"); // path to the folder where input text files are located
    //public static final Path FOLDER_OF_TEXT_FILES = Paths.get("input_1607460341"); // path to the folder where input text files are located
    //public static final Path FOLDER_OF_TEXT_FILES = Paths.get("SampleTXTFileFolder"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("OutputTXT.txt"); // path to the output plain-text (.txt) file
    public static final int NUMBER_OF_THREADS = 15;                // max. number of threads to spawn

    public static ArrayList<File> files = new ArrayList<>();
    public static ArrayList<Map<String, Integer>> data = new ArrayList<>();
    public static ArrayList<String> allWords = new ArrayList<>();
    public static ArrayList<Thread> threads = new ArrayList<>();
    public static final int maxNumber = NUMBER_OF_THREADS + 1;

    public static void main(String... args) {

        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program
        File folder = new File(WordCounter.FOLDER_OF_TEXT_FILES.toString());

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            throw new NullPointerException("No files found in path");
        }

        files = new ArrayList<>(Arrays.asList(listOfFiles));
        Collections.sort(files);
        int numOfFiles = files.size();
        for(int i = 0; i < numOfFiles; i++){
            data.add(null);
        }

        long start, end;


        if(NUMBER_OF_THREADS == 0 || NUMBER_OF_THREADS == 1) {
            start = System.currentTimeMillis();
            for (File file : files) {
                if(file.isFile()) { singleThread(file); }
            }
            end = System.currentTimeMillis();
        }
        else{
            for(int i = 1; i <= NUMBER_OF_THREADS; i++){ threads.add(new Thread()); }
            start = System.currentTimeMillis();
            int i = 1;
            for (File file : files) {
                if(file.isFile()){
                    if(i <= maxNumber){ multiThread(file, i); }
                }
                i++;
                if(i == maxNumber){ i = 1; }
            }
            //start = System.currentTimeMillis();
            for(Thread t : threads){
                try {
                    t.join();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            end = System.currentTimeMillis();
        }

        try { Thread.sleep(files.size()*600); }
        catch (InterruptedException ie) { ie.printStackTrace(); }

        printTable();

        System.out.println(end-start);
    }

    public static synchronized void multiThread(File file, int i){
        threads.set(i-1, new Thread(() -> {
            HashMap<String, Integer> newMap =  new HashMap<>();
            try {
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()) {

                    String line = reader.nextLine().replaceAll("\\p{Punct}", "").trim().toLowerCase();
                    String[] split = line.split(" ");

                    for(int j = 0; j < split.length; j++) {
                        if(newMap.containsKey(split[j])){ newMap.put(split[j], newMap.get(split[j])+1); }
                        else{ newMap.put(split[j], 1); }

                        if (!allWords.contains(split[j])) { allWords.add(split[j]); }
                    }
                }
                //data.add(newMap)
                data.set(files.indexOf(file), newMap);
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }));
        threads.get(i-1).start();
        //System.out.println("Running: t" + (i));
    }


    public static void singleThread(File file){
        HashMap<String, Integer> newMap = new HashMap<>();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {

                String line = reader.nextLine().replaceAll("\\p{Punct}", "").trim().toLowerCase();
                String[] split = line.split(" ");

                for (int i = 0; i < split.length; i++) {
                    if (newMap.containsKey(split[i])) { newMap.put(split[i], newMap.get(split[i]) + 1); }
                    else { newMap.put(split[i], 1); }

                    if (!allWords.contains(split[i])) { allWords.add(split[i]); }
                }
            }
            //data.add(newMap);
            data.set(files.indexOf(file), newMap);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        if(files.size() < 10){
            try { Thread.sleep(10*files.size()); }
            catch (InterruptedException ie) { ie.printStackTrace(); }
        }
        else{
            try { Thread.sleep(5); }
            catch (InterruptedException ie) { ie.printStackTrace(); }
        }

    }




    public static void printTable(){
        try {
            int max = 0;
            Collections.sort(allWords);
            for(String word : allWords){
                if(word.length() > max){ max = word.length(); }
            }

            PrintWriter writer = new PrintWriter(WordCounter.WORD_COUNT_TABLE_FILE.toString());

            writer.write(String.format("%" + (max + 1) + "s", ""));
            for (File file : files) {
                if (file.isFile()) {
                    String fileString = file.getName().replace(".txt", "");
                    writer.write(String.format("%-" + max + "s", fileString));
                }
            }
            writer.write(String.format("%-" + max + "s", "total"));
            writer.println();
            for(String word: allWords){
                writer.write(String.format("%-" + max + "s", word));
                int count = 0;
                for(Map<String, Integer> map: data){
                    if(map == data.get(0)){
                        if(map.containsKey(word)){
                            writer.write(" ");
                            writer.write(String.format("%-" + max + "s", map.get(word)));
                            count += map.get(word);
                        }
                        else {
                            writer.write(" ");
                            writer.write(String.format("%-" + max+ "s", "0"));
                        }
                    }
                    else{
                        if(map.containsKey(word)){
                            writer.write(String.format("%-" + max + "s", map.get(word)));
                            count += map.get(word);
                        }
                        else {
                            writer.write(String.format("%-" + max + "s", "0"));
                        }
                    }
                }
                writer.write(String.format("%-" + max + "s", count));
                if(word.equals(allWords.get(allWords.size()- 1)));
                else{ writer.println(); }
            }
            writer.close();
        } catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }


    }


}