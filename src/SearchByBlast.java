import java.util.*;
import java.io.*;
/*
 * Given a list of contigs, this class will isolate the BLAST Hits and GOs in a nice format
 */
public class SearchByBlast
{
    private static String excelFile; 
    private static String queryFile;
    private static ArrayList<String> blastListArray = new ArrayList<String>();
    private static ArrayList<String> contigListArray = new ArrayList<String>();
    private static ArrayList<String> queryListArray = new ArrayList<String>();
    private static ArrayList<String> condensedContig;
    
    public static void main( String[] args ) 
    {
        try
        {
            excelFile = args[0];
            queryFile = args[1];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java SearchByBlast excelFile " +  
                "queryFile" );
            System.exit( 0 );
        }
        loadArrayLists(excelFile);
        loadQuery(queryFile);
        writeOutSearch(queryListArray);
    }

    public static void loadArrayLists(String filename)
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            String line = new String();
            int i = 0;
            line = scanner.nextLine().trim();
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    continue;
                }
                Scanner lineScanner = new Scanner( line );
                String token = lineScanner.next();
                StringBuilder blast = new StringBuilder();
                while(!token.contains("gi|") )
                {
                    token = lineScanner.next();
                }
                while(!token.contains("SpoMult2") )
                {
                    blast.append(" " + token);
                    token = lineScanner.next();
                }
                blastListArray.add(blast.toString());
                contigListArray.add(token);
                //contigListArray.add(token.substring(token.lastIndexOf('g') + 1, token.lastIndexOf('D')));
                i++;
            }
            scanner.close();
        }
        catch(Exception anException)
        {
            anException.printStackTrace();
        }
    }

    public static void loadQuery(String filename)
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            String line = new String();
            int i = 0;
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    continue;
                }
                queryListArray.add(line);
                i++;
            }
            scanner.close();
        }
        catch(Exception anException)
        {
            anException.printStackTrace();
        }
    }

    public static void writeOutSearch(ArrayList<String> query)
    {
        try
        {
            BufferedWriter outputFile = new BufferedWriter(new FileWriter("2searchFileFor_" + excelFile + "_" + queryFile));
            for(String queryS : query )
            {

                outputFile.write( "Query on " + excelFile + " for " + queryS + "\n");
                condensedContig = new ArrayList<String>();
                for( int i = 0; i < blastListArray.size(); i++ )
                {
                    if(blastListArray.get(i).toLowerCase().contains(queryS.toLowerCase()))
                    {
                        outputFile.write(blastListArray.get(i) + " \t \t \t " + contigListArray.get(i) + "\n");
                        if(!condensedContig.contains(contigListArray.get(i)))
                        {
                            condensedContig.add(contigListArray.get(i));
                        }
                    }
                }
                outputFile.write("CondensedContigs: \n ");
                for(int j = 0; j < condensedContig.size(); j++ )
                {
                    outputFile.write(condensedContig.get(j) + "\n");
                }
                    
                outputFile.write("\n \n \n");
                
            }
            
            outputFile.close();
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
    }
}
