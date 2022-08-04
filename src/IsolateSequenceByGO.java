import java.util.*;
import java.io.*;
/*
 * 
 */
public class IsolateSequenceByGO
{
    private static String goToBeSearched;
    private static String goListFileName;
    private static String contigListFileName;
    private static ArrayList<Integer> indicesOfGOIds = new ArrayList<Integer>();
    public static void main( String args[] )
    {
        try  
        {
            goToBeSearched = args[2];
            goListFileName = args[1];
            contigListFileName = args[0];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java IsolateSequenceByGO ListOfContigsFileName " + 
                "goListFileName goToBeSearched" );
            System.exit( 0 );
        }
        String[] goListArray = readInFile( goListFileName );
        String[] contigArray = readInFile( contigListFileName );
        findGOSInArray(goToBeSearched, goListArray );
        writeNewContigFile( contigArray );
    }

    public static String[] readInFile( String filename )
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
        catch( Exception e )
        {
            e.printStackTrace();
        }   
        return null;
    }

    public static void findGOSInArray( String goToBeSearched, String[] goListArray )
    {
        try
        {
            for( int i = 0; i < goListArray.length; i++ )
            {
                if( goListArray[i].contains(goToBeSearched) )
                {
                    indicesOfGOIds.add(i);
                }
            }
        }
        catch( Exception anException )
        {
            anException.printStackTrace();
        }
    }

    public static void writeNewContigFile(String[] contigArray)
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "newContigFile-" + goToBeSearched) ); 
            ArrayList<String> stuffAlreadyAdded = new ArrayList<String>();
            for( int i = 0; i < indicesOfGOIds.size(); i++)
            {
                if(!stuffAlreadyAdded.contains(contigArray[indicesOfGOIds.get(i)]) )
                {
                    outputFile.write(contigArray[indicesOfGOIds.get(i)]);
                    outputFile.newLine();
                    stuffAlreadyAdded.add(contigArray[indicesOfGOIds.get(i)]);
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
