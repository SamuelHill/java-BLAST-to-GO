import java.util.*; //this will be needed for use of the Scanner class
import java.io.*; //this will be needed for use of the File class
/*
 * This class, given three sample's expression values and a list of corresponding contig names, will isolate
 * contigs where the first-given sample has the lowest expression. 
 */
public class GeneLowerExpressionComparer 
{
    private static String sampleToBeComparedFileName;
    private static String sampleTwoFileName;
    private static String sampleThreeFileName;
    private static String contigListFileName;
    private static ArrayList<Integer> indicesOfLowerExpression = new ArrayList<Integer>();
    public static void main( String[] args )
    {
        try  //this try/catch will make sure that the correct type and number of command-line arguments
        //are entered
        {
            sampleToBeComparedFileName = args[0];
            sampleTwoFileName = args[1];
            sampleThreeFileName = args[2];
            contigListFileName = args[3];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java GeneLowerExpressionComparer SampleToBeCompared's_FileName " + 
                "Sample2's_FileName Sample3's_FileName ListOfContigsFileName" );
            System.exit( 0 );
        }
        double[] sampleComparedArray = readSampleFile( sampleToBeComparedFileName );
        double[] sample2Array = readSampleFile( sampleTwoFileName );
        double[] sample3Array = readSampleFile( sampleThreeFileName );
        String[] contigArray = readContigFile( contigListFileName );
        compareSampleValues( sampleComparedArray, sample2Array, sample3Array );
        writeLowerExpressionContigFile(contigArray);
        
        System.out.println("There was found to be " + indicesOfLowerExpression.size() + " contigs where sample one had lower expression than the other two samples");
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

    public static void compareSampleValues( double[] sampleArrayToBeCompared, double[] sampleArray2, double[] sampleArray3)
    {
        for( int i = 0; i < sampleArrayToBeCompared.length; i++)
        {
            if( sampleArrayToBeCompared[i] < sampleArray2[i] && sampleArrayToBeCompared[i] < sampleArray3[i] )
            {
                indicesOfLowerExpression.add(i);
            }
        }
    }

    public static void writeLowerExpressionContigFile(String[] contigArray)
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "LowerExpressionContigList") ); 
            for( int i = 0; i < indicesOfLowerExpression.size(); i++)
            {
                outputFile.write( contigArray[indicesOfLowerExpression.get(i)] ); //puts in the contig name for each index where 
                //the sample being compared has the highest normalized mean
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