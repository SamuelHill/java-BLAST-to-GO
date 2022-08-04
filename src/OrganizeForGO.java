import java.util.*;
import java.io.*;
/*
 * Given a list of contigs, this class will isolate the BLAST Hits and GOs in a nice format
 */
public class OrganizeForGO
{
    private static String contigListFile; 
    private static String corContigListFile;
    private static String goListFile;
    private static ArrayList<String> goInOrder = new ArrayList<String>();
    private static ArrayList<Integer> whereIndiciesSwitch = new ArrayList<Integer>();

    public static void main( String[] args ) 
    {
        try
        {
            contigListFile = args[0];
            corContigListFile = args[1];
            goListFile = args[2];
        }
        catch( Exception anException )
        {
            System.out.println( "Correct Usage: java OrganizeForBLAST contigListFile corContigListFile " +  
                "goListFile" );
            System.exit( 0 );
        }
        ArrayList<String> corContigListArray = loadContigList(corContigListFile);
        ArrayList<String> contigListArray = loadContigList(contigListFile);
        ArrayList<String> goListArray = loadArrayList(goListFile);
        gatherList( goListArray, contigListArray, corContigListArray);
        writeOrganizedFile( contigListArray );
    }

    public static ArrayList<String> loadArrayList(String filename)
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
                tmpList.add(line);
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

    public static void gatherList(ArrayList<String> goListArray, ArrayList<String> contigListArray, ArrayList<String> corContigListArray )
    {
        ArrayList<String> tmp = new ArrayList<String>();

        for( int i = 0; i < contigListArray.size(); i++ )
        {
            tmp = new ArrayList<String>();

            for( int j = 0; j < corContigListArray.size(); j++ )
            {
                /* System.out.println(i + ": " + contigListArray.get(i) );
                System.out.println(j + ": " + corContigListArray.get(j) ); 
                System.out.println("i size can be: " + contigListArray.size() );
                System.out.println("j size can be: " + corContigListArray.size() ); */
                if( contigListArray.get(i).equals(corContigListArray.get(j)) )
                {
                    if( !(tmp.contains(goListArray.get(j) ) ) )
                    {
                        //System.out.println("Yes");
                        tmp.add(goListArray.get(j));
                    }
                }

            }
            if(!corContigListArray.contains(contigListArray.get(i)))
            {
                tmp.add("NO GO ID");
            }
            for( int k = 0; k < tmp.size(); k++)
            {
                goInOrder.add(tmp.get(k));
            }
            if( whereIndiciesSwitch.size() > 0  && tmp.size() != 0 )
            {
                //System.out.println( tmp.size() + whereIndiciesSwitch.get(whereIndiciesSwitch.size() - 1) );
                whereIndiciesSwitch.add( ( tmp.size() + whereIndiciesSwitch.get(whereIndiciesSwitch.size() - 1) ));
            }
            else if( tmp.size() != 0 )
            {
                // System.out.println( tmp.size() );
                whereIndiciesSwitch.add(tmp.size() - 1);
            }
        }

    }

    public static void writeOrganizedFile( ArrayList<String> contigListArray )
    {
        try
        {
            BufferedWriter outputFile = 
                new BufferedWriter( new FileWriter( "OrganziedGOCorrelatedDataFor_" + contigListFile) ); 

            int i = 0;
            outputFile.write(contigListArray.get(i) + ": \t" );
            for(int j = 0; j < goInOrder.size(); j++ )
            {
                if(j != whereIndiciesSwitch.get(i) )
                {
                    outputFile.write(goInOrder.get(j) + "; ");
                }
                else
                {
                    i++;
                    outputFile.write(goInOrder.get(j) + ";");
                    outputFile.write("\n");
                    outputFile.write(contigListArray.get(i) + ": \t");
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
