import java.util.*;
import java.io.*;
/*
 * Given a list of contigs, this class will isolate the BLAST Hits and GOs in a nice format
 */
public class OrganizeForBLAST
{
    private static String contigListFile; 
    private static String corContigListFile;
    private static String blastHitsFile;
    private static ArrayList<String> blastInOrder = new ArrayList<String>();
    private static ArrayList<Integer> whereIndiciesSwitch = new ArrayList<Integer>();

    public static void main( String[] args ) 
    {
        try
        {
            contigListFile = args[0];
            corContigListFile = args[1];
            blastHitsFile = args[2];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java OrganizeForBLAST contigListFile corContigListFile " +  
                "blastHitsFile" );
            System.exit( 0 );
        }
        ArrayList<String> corContigListArray = loadContigList(corContigListFile);
        ArrayList<String> contigListArray = loadContigList(contigListFile);
        ArrayList<String> blastListArray = loadArrayList(blastHitsFile);
        gatherList( blastListArray, contigListArray, corContigListArray);
        writeOrganizedFile();
    }

    public static ArrayList<String> loadArrayList(String filename)
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            ArrayList<String> tmpList = new ArrayList<String>();
            String line = new String();
            int Number = 0;
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    tmpList.add("---NA---");
                    continue;
                }
                tmpList.add(line);
                Number++;
            }
            scanner.close();
            return tmpList;
        }
        catch(Exception anException)
        {
            anException.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> loadContigList(String filename)
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            ArrayList<String> tmpList = new ArrayList<String>();
            String line = new String();
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    continue;
                }
                tmpList.add(line.replace(' ', '_'));
            }
            scanner.close();
            return tmpList;
        }
        catch(Exception anException)
        {
            anException.printStackTrace();
        }
        return null;
    }

    public static void gatherList(ArrayList<String> blastListArray, ArrayList<String> contigListArray, ArrayList<String> corContigListArray )
    {

        for( int i = 0; i < contigListArray.size(); i++ )
        {

            for( int j = 0; j < corContigListArray.size(); j++ )
            {
                if( contigListArray.get(i).equals(corContigListArray.get(j)) )
                {
                    blastInOrder.add(blastListArray.get(j));
                }

            }

        }

    }

    public static void writeOrganizedFile()
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "OrganziedCorrelatedDataFor_" + contigListFile) ); 

 
            for(int j = 0; j < blastInOrder.size(); j++ )
            {
                    outputFile.write(blastInOrder.get(j) );
                    outputFile.write("\n");
            }

            outputFile.close();
        }
        catch (Exception anException)
        {
            anException.printStackTrace();
        }
    }

}
