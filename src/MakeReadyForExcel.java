import java.util.*;
import java.io.*;
/*
 * This class makes a .txt file that is ready to be imported into excel, which contains all of a given list of contigs
 * (assuming they are all in the master excel file, which is also given.
 */
public class MakeReadyForExcel
{
    private static String fileOfHigherExpressionContigs;
    private static String mainExcelFile;
    public static void main( String[] args )
    {
        try
        {
            fileOfHigherExpressionContigs = args[0];
            mainExcelFile = args[1];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java MakeReadyForExcel fileWithNamesOfHigherorLowerExpessionContigs " +
                "fileNameOfMainExcelSheet");
            System.exit( 0 );
        }
        String[] higherExpressionContigArray = readContigFile( fileOfHigherExpressionContigs );
        String[] finalArrayOfNewExcel = findHigherExpressionInExcel( higherExpressionContigArray, mainExcelFile );
        writeNewReadyForExcelFile( finalArrayOfNewExcel );

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
                contigNames[i] = line.replace(' ', '_');
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

    public static String[] findHigherExpressionInExcel( String[] contigArray, String excelFile )
    {
        try
        {
            Scanner scanner = new Scanner( new File( excelFile ) );
            String line;

            String[] arrayOfHigherExpressionExcelCells = new String[contigArray.length];
            line = scanner.nextLine().trim();
            String toSearch = line;
            double percentDone = 0.0;
            for( int i = 0; i < contigArray.length; i++ )
            {
                percentDone = (double)i/contigArray.length * 100;
                System.out.println( "Currently at " + percentDone + "% done" );
                while( !toSearch.trim().equals(contigArray[i].trim()) )
                {
                    if( scanner.hasNextLine() )
                    {
                        line = scanner.nextLine().trim();
                        int indexOfEnd = line.indexOf('y');
                        String subLine = line.substring(0, indexOfEnd + 1);
                        toSearch = subLine.replace(' ', '_'); //this is needed to add underscores,
                    }
                    else
                    {
                        scanner.close();
                        scanner = new Scanner( new File( excelFile ) );
                    }
                }
                arrayOfHigherExpressionExcelCells[i] = line;
            }

            scanner.close();
            return arrayOfHigherExpressionExcelCells;
        }
        catch( Exception anException )
        {
            anException.printStackTrace();
        }
        return null;
    }

    public static void writeNewReadyForExcelFile(String[] finalArray)
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "newExcel-" + fileOfHigherExpressionContigs) ); 
            for( int i = 0; i < finalArray.length; i++)
            {
                outputFile.write( finalArray[i] );
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
