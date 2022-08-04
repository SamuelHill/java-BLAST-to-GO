import java.util.*;
import java.io.*;
/*
 * This class creates a file representing the number of times that each GO ID shows up in the results
 * gathered from BLAST2GO and gives what GO term is linked to each GO ID. 
 */
public class Categorizer
{
    private static ArrayList<Integer> goIterations;
    private static String goListFile;
    private static String contigListFile;
    private static String goDatabaseFile;
    public static void main( String[] args )
    {
        try
        {
            goListFile = args[0];
            contigListFile = args[1];
            goDatabaseFile = args[2];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java Categorizer goListFile " + 
                "contigFile goDatabase" );
            System.exit( 0 );
        }
        String[] goListArray = loadGoListArray(goListFile);
        String[] contigListArray = loadContigArray(contigListFile);
        String[] goDatabaseArray = loadGoDatabase(goDatabaseFile);
        ArrayList<String> newGoListArray = condenseList(goListArray, contigListArray);
        writeOrganizedFile(newGoListArray, goDatabaseArray);
    }

    public static String[] loadContigArray(String filename)
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

    public static String[] loadGoListArray(String filename)
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

    public static String[] loadGoDatabase(String filename)
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

    public static ArrayList<String> condenseList( String[] goListArray, String[] contigListArray )
    {
        ArrayList<String> newGoList = new ArrayList<String>();
        goIterations = new ArrayList<Integer>();
        String pastSequence = "";
        String pastGOID = "";
        for(int i = 0; i < goListArray.length; i++)
        {
            if(pastSequence.equals(contigListArray[i]) && pastGOID.equals(goListArray[i]) )
            {
                continue;
            }
            if(newGoList.contains(goListArray[i]) )
            {
                goIterations.set( newGoList.indexOf(goListArray[i]), goIterations.get(newGoList.indexOf(goListArray[i])) + 1);
                pastSequence = contigListArray[i];
                pastGOID = goListArray[i];
                continue;
            }
            newGoList.add(goListArray[i]);
            goIterations.add(1);
            pastSequence = contigListArray[i];
            pastGOID = goListArray[i];
        }

        return newGoList;
    }

    public static void writeOrganizedFile( ArrayList<String> goListArray, String[] goDatabaseFile )
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "newCategorizedFile") ); 
            Scanner lineScanner;
            String line;
            String token = "";
            for( int i = 0; i < goListArray.size(); i++)
            {
                for( int j = 0; j < goDatabaseFile.length; j++ )
                {
                    if(goDatabaseFile[j].contains(goListArray.get(i)))
                    {
                        line = goDatabaseFile[j];
                        lineScanner = new Scanner( line );
                        lineScanner.next();
                        while( lineScanner.hasNext() )
                        {
                            token += lineScanner.next() + "";
                        }
                    }
                }
                outputFile.write(goListArray.get(i) + ";" + token + ";" + goIterations.get(i) );
                outputFile.newLine();
                token = "";
            }
            outputFile.close();
        }
        catch (Exception anException)
        {
            anException.printStackTrace();
        }
    }

}
