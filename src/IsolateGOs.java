import java.util.*;
import java.io.*;
public class IsolateGOs
{
    private static String goFileToBeIsolated = "";
    public static void main( String[] args )
    {
        try  
        {
            goFileToBeIsolated = args[0];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java IsolateGOs goFileToBeIsolated" );
            System.exit( 0 );
        }
        ArrayList<String> isolatedGOs = readInFile( goFileToBeIsolated );
        writeIsolatedGOs( isolatedGOs );
    }

    public static ArrayList<String> readInFile( String filename )
    {
        try
        {
            Scanner scanner = new Scanner( new File( filename ) );
            String line;

            scanner = new Scanner( new File( filename ) );
            ArrayList<String> isolatedGOS = new ArrayList<String>();
            while( scanner.hasNextLine() )
            {
                line = scanner.nextLine().trim();
                Scanner lineScanner = new Scanner( line );
                if( line.length() == 0 )
                {
                    continue;
                }
                String token = lineScanner.next();
                while( !token.startsWith("GO:") )
                {
                    //System.out.println(token);
                    token = lineScanner.next();
                }
                String GOID = token.substring( token.indexOf(":") + 1, token.length() );
                if( !isolatedGOS.contains(GOID) )
                {
                    isolatedGOS.add(GOID);
                }
            }
            scanner.close();
            return isolatedGOS;
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }   
        return null;
    }

    public static void writeIsolatedGOs(ArrayList<String> gos)
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "isolatedGOs" + goFileToBeIsolated) ); 

            for( int i = 0; i < gos.size(); i++)
            {
                outputFile.write(gos.get(i));
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