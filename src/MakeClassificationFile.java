import java.util.*;
import java.io.*;
/*
 * This class makes a database file usable for the Categorizer class from the GO ID database 
 * downloaded from the geneontology.org website
 */
public class MakeClassificationFile
{
    private static String goDatabaseXMLFile;
    public static void main( String[] args )
    {
        try  
        {
            goDatabaseXMLFile = args[0];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java MakeClassificationFile goDatabaseXMLFile" );
            System.exit( 0 );
        }
        String[] databaseArray = readXMLDatabaseFile( goDatabaseXMLFile );
        writeClassificationFile(databaseArray);
    }  

    public static String[] readXMLDatabaseFile( String filename )
    {
        String[] databaseInfo = null;
        try
        {
            Scanner inputFile = new Scanner( new File( filename ) ); 
            String line = inputFile.nextLine();
            int numGOIds = 0;
            while( inputFile.hasNextLine() ) //the purpose of this is to find out how many go ids there are in the xml file so I
            //can initilize my array
            {
                line = inputFile.nextLine().trim();
                if( line.trim().startsWith( "<go:accession>" ) )
                {
                    numGOIds += 1;
                }
            }
            inputFile.close();
            
            databaseInfo = new String[numGOIds]; 
            
            inputFile = new Scanner( new File( filename ) ); 
            line = inputFile.nextLine();
            while( !line.trim().startsWith( "<rdf:RDF>" ) )
            {
                line = inputFile.nextLine();
            }

            

            String goInfo ="";
            int stringNumber = 0;

            while( !line.trim().startsWith( "</go:go>" ) )
            {
                

                if( line.trim().startsWith( "<go:accession>" ) )
                {
                    goInfo = new String(); 

                    int startPosition = line.indexOf( ">" );
                    int finalPosition = line.lastIndexOf( "<" );
                    goInfo += line.substring( startPosition + 1, finalPosition );
                }

                if( line.trim().startsWith( "<go:name>" ) )
                {
                    int startPosition = line.indexOf( ">" );
                    int finalPosition = line.lastIndexOf( "<" );

                    
                    goInfo = goInfo + "    " + line.substring( startPosition + 1, finalPosition); 

                    databaseInfo[stringNumber] = goInfo;

                    stringNumber++;
                }

                line = inputFile.nextLine();
                //handle each hit one at a time
            }
        }
        catch (Exception anException)
        {
            anException.printStackTrace();
        }
        return databaseInfo; 
    }

    public static void writeClassificationFile(String[] databaseArray)
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "GoTermClassificationFile") ); 
            for( int i = 0; i < databaseArray.length; i++)
            {
                outputFile.write(databaseArray[i]);
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
