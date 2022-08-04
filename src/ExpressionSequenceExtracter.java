import java.util.*;
import java.io.*;
/*
 * This class will extract the sequences of contigs specified from a list into a new FASTA file, that can
 * be run through BLAST2GO
 */
public class ExpressionSequenceExtracter
{
    private static String mainFASTAFile;
    private static String higherExpressionContigList;
    public static void main( String[] args )
    {
        try
        {
            higherExpressionContigList = args[0];
            mainFASTAFile = args[1];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java ExpressionSequenceExtractor listOfExpressionContigs " +
                                "FASTAFileFromCLC" );
            System.exit( 0 );
        }
        String[] contigListArray = readContigFile( higherExpressionContigList );
        String[] newHigherExpressionFASTAFileArray = prepareFromFASTACLCFile( mainFASTAFile, contigListArray );
        writeNewFASTAWithHigherExpression( newHigherExpressionFASTAFileArray );
    }
    
    public static String[] readContigFile( String filename )
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            String line;
            Scanner linScanner;
            
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
                contigNames[i] = line.replace(' ', '_'); //this is needed to add underscores, as found in the FASTA file
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
    
    public static String[] prepareFromFASTACLCFile(String filename, String[] contigListArray )
    {
        try
        {
           Scanner scanner = new Scanner( new File( filename ) );  
           String line;
           String[] newFileInArray = new String[contigListArray.length];
           while( scanner.hasNextLine() )
           {
               line = scanner.nextLine();
               if( line.startsWith(">") )
               {
                   for(int i = 0; i < contigListArray.length; i++ )
                   {
                       if( line.contains(contigListArray[i]) )
                       {
                           newFileInArray[i] = line;
                           line = scanner.nextLine();
                           while( !line.startsWith(">") )
                           {
                               newFileInArray[i] += "\n" + line;
                               line = scanner.nextLine();
                           }
                       }
                   }
               }
           }
           System.out.println( "It returned this!" );
           return newFileInArray;
        }
        catch( Exception anException )
        {
            anException.printStackTrace();
        }
        return null;
    }
    
    public static void writeNewFASTAWithHigherExpression(String[] newHigherExpressionFASTAArray )
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "ExpressionFASTAList" ), 999999 ); 
            for( int i = 0; i < newHigherExpressionFASTAArray.length; i++)
            {
                if( newHigherExpressionFASTAArray[i].equals(null) )
                {
                    continue;
                }
                System.out.println( newHigherExpressionFASTAArray[i] );
                outputFile.write(newHigherExpressionFASTAArray[i]); 
                System.out.println( i );
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
