import java.util.*;
import java.io.*;
/*
 * This class will isolate BLAST hits based on a given GO ID List
 */
public class IsolateBLASTHits
{
    private static String goListFile;
    private static String contigListFile;
    private static String blastHitsFile;
    private static String goIDListFile;
    public static void main( String[] args ) 
    {
        try
        {
            goIDListFile = args[0];
            goListFile = args[1];
            contigListFile = args[2];
            blastHitsFile = args[3];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java IsolateBLASTHits goIDListToIsolate goListFile " +  
                "contigListFile blastHitsFile" );
            System.exit( 0 );
        }
        String[] goIDListToIsolate = loadArray(goIDListFile);
        String[] goListArray = loadArray(goListFile);
        String[] contigListArray = loadArray(contigListFile);
        String[] blastListArray = loadArray(blastHitsFile);
        writeOrganizedFile( goListArray, blastListArray, contigListArray, goIDListToIsolate);
    }

    public static String[] loadArray(String filename)
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            String line;

            int numLines = 0;

            while( scanner.hasNextLine() ) //the purpose of this is to find out how many lines (i.e. values) there are in the sample file so I
            //can initilize my array
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    continue;
                }
                numLines += 1;
            }

            scanner.close(); //I close then redeclare my scanner to get it back to the beginning of the file

            scanner = new Scanner( new File( filename ) );
            String[] contigNames = new String[ numLines ];
            int i = 0;
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    continue;
                }
                contigNames[i] = line;
                i += 1;
            }
            scanner.close();
            return contigNames;
        }
        catch(Exception anException)
        {
            anException.printStackTrace();
        }
        return null;
    }

    public static void writeOrganizedFile( String[] goListArray, String[] blastArray, String[] contigArray, String[] goIDListToSearch )
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "BLASTHitsfor" + goIDListFile) ); 
            ArrayList<String> blastAlreadyIn = new ArrayList<String>();
            ArrayList<String> contigAlreadyIn = new ArrayList<String>();
            for( int i = 0; i < goListArray.length; i++)
            {
                for(int j = 0; j < goIDListToSearch.length; j++ )
                {
                    if(goListArray[i].contains( goIDListToSearch[j] ) )
                    {
                        if( blastAlreadyIn.contains( blastArray[i] ) && contigAlreadyIn.contains( contigArray[i]) )
                        {
                            continue;
                        }
                        outputFile.write(contigArray[i] + "; \t" + blastArray[i]);
                        outputFile.newLine();
                        blastAlreadyIn.add(blastArray[i]);
                        contigAlreadyIn.add(contigArray[i]);
                    }
                }
            }
            outputFile.close();
        }
        catch (Exception anException)
        {
            anException.printStackTrace();
        }
    }

}
