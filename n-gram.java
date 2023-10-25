//Code by: Binh Tran
// anagram method that finds 2-gram words and sort them in order of most frequent to least frequent

import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;




class Solution{

    public static String[] cleanText(String txt){
    // """takes a string of text txt as a parameter 
    // and returns a string containing the words in txt 
    // after it has been “cleaned” 
        String cleaned= "";
        String[] symbols = {",", ".", "!", ":", "?", ";", "'"}; 
        for(String symbol: symbols){
            if(txt.contains(symbol)){
                txt = txt.replace(symbol, "");
            }
        }   
        txt = txt.toLowerCase();
        cleaned += txt;
        String[] list = cleaned.split("\\s+"); // gives an array
         return list;

}
    public static HashMap<String, Integer> nGram(List<String> file, int num){
        //look for 2n-gram words and make it the key in HashMap if it's not already one. Increment value of one if it is. 

        HashMap<String, Integer> table = new HashMap<>();
        for(int i = 0; i < file.size(); i++){
            String ngram = file.get(i) + " ";
            if((file.size() - i) >= num){
                for(int x = 1; x < num; x++){
                    ngram += file.get( i + x) + " ";
            }
        } 
            if (table.containsKey(ngram)){
                int value = table.get(ngram);
                table.replace(ngram, value + 1);
            }else{
                table.put(ngram, 1);
            }   
        } return table;
        


    }
    //read the file by lines, call cleanText and make a list of words
    public static List<String> readFile() {            
        List<String> list = new ArrayList<>();

        
        try{
            File file = new File("Moby-Dick.txt");
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(!line.isEmpty()){
                    String[] cleaned = cleanText(line); //get rid of symbols: ;.,:"' return clean;
                    Collections.addAll(list, cleaned); //creates a list of words

                }
              
            }       
            
            sc.close();
            

        } catch (FileNotFoundException e) {
            System.out.println("The file was not found.");
        }   
        return list;         

    }
    
    //credit: https://stackoverflow.com/q/68653561/22186466 with modified parameter
    private static Map<String, Integer> sortByValue(HashMap<String, Integer> unsortMap, final boolean order)
    {
        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    // for each key-value pair, it adds to the result
    private static String printMap(Map<String, Integer> map, int num) { 
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (String ngram : map.keySet() ) {
            if(count < num){
            sb.append("word: "+ ngram);
            sb.append(": ");
            sb.append("frequency: "+map.get(ngram));
            sb.append("\n");
            count++;
            }else{
                break;
            }
           
        }
        return sb.toString();
    }


public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.print("enter an number for the n-gram: ");
    int num = input.nextInt();
  
     
    int size = sortByValue(nGram(readFile(), num), false).size(); 
    
    //get the number of the first x values to show
    Scanner scan = new Scanner(System.in);
    int val;
    do{
        
         System.out.print("The number of ngram is " + size + "\n"+ "choose first x n-gram to display: ");
         val = scan.nextInt();
         if(val>size){
            System.out.println("value is bigger than the number of n-gram. Try again");
         }
    }while(val > size);
    scan.close();
    input.close(); 
     

    //opens a text file, clear it, and add the n-grams to it
    File file = new File("result.txt");
    String result = printMap(sortByValue(nGram(readFile(), num), false), val);
    try {
        PrintWriter printer = new PrintWriter(file);
        printer.print("");
        printer.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.append(result);
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    }
}


