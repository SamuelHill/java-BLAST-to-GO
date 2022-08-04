import java.util.*;
import java.io.*;
/*
 * This class creates an expression data file ready to be imported into CLC Workbench to make a heat map out of. 
 */
public class CreateExpressionDataFile
{
    private static String sampleOneFileName;
    private static String sampleTwoFileName;
    private static String sampleThreeFileName;
    private static String contigListFileName;

    public static void main( String[] args )
    {
        try  //this try/catch will make sure that the correct type and number of command-line arguments
        //are entered
        {
            sampleOneFileName = args[0];
            sampleTwoFileName = args[1];
            sampleThreeFileName = args[2];
            contigListFileName = args[3];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java CreateExpressionDataFile Sample1Compared's_FileName " + 
                "Sample2's_FileName Sample3's_FileName ListOfContigsFileName" );
            System.exit( 0 );
        }
        double[] sample1Array = readSampleFile( sampleOneFileName );
        double[] sample2Array = readSampleFile( sampleTwoFileName );
        double[] sample3Array = readSampleFile( sampleThreeFileName );
        String[] contigArray = readContigFile( contigListFileName );
        writeExpressionDataFile(contigArray, sample1Array, sample2Array, sample3Array);
        System.out.println("The program is certainly set up as if sample 1 was normal, sample 2 was APO, and sample 3 was reinfect" + "\n Just letting you know" + "\n Also, it's very important that each of these files are in order with respect to each other");
        
    }

    public static double[] readSampleFile( String filename )
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
            double[] currentSampleValues = new double[ numLines ];
            int i = 0;
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                if( line.length() == 0 )
                {
                    continue;
                }
                currentSampleValues[i] = Double.parseDouble( line );
                i += 1;
            }
            scanner.close();
            return currentSampleValues;
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] readContigFile( String filename )
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
    
    public static void writeExpressionDataFile(String[] contigArray, double[] sample1Array, double[] sample2Array, double[] sample3Array)
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter("ExpressionDataFor" + contigListFileName) ); 
            outputFile.write("Feature ID;sample1;sample2;sample3");
            outputFile.newLine();
            for( int i = 0; i < contigArray.length; i++)
            {
               outputFile.write(contigArray[i] + ";" + sample1Array[i] + ";" + sample2Array[i] + ";" + sample3Array[i] );
               outputFile.newLine();
            }

            outputFile.close();
        }
        catch (Exception anException)
        {
            anException.printStackTrace();
        }
    }
}

