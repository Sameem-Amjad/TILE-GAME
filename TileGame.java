import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class TileGame {
    public static Scanner sc;
        public static final String DELIMITER = "";
        public static final String LEFT  = "a";
        public static final String UP    = "w";
        public static final String RIGHT = "d";
        public static final String DOWN  = "s";
        public static final String QUIT  = "QUIT";
        public static final String BLANK_TILE = "O";
    
    
    public static boolean playTileGame(String[][] solution, String[][] puzzle) {
        int moves = 0;

        while (true) {
            // Print the current state of the puzzle
            printBoards(solution, puzzle);

            // Get the user's move
            String move = getMove();

            // Check if the user wants to quit
            if (move.equals(QUIT)) {
                SOPln("You quit the game.");
                return false;
            }

            // Apply the move to the puzzle
            setMove(puzzle, move);
            moves++;

            // Check if the puzzle is complete
            if (isPuzzleComplete(solution, puzzle)) {
                SOPln("Congratulations! You solved the puzzle in " + moves + " moves.");
                return true;
            }
        }
    }

    private static boolean isValidCoordinate(String[][] puzzle, int newRow, int newCol) {
    int numRows = puzzle.length;
    int numCols = puzzle[0].length;

    // Check if the new coordinates are within the bounds of the puzzle grid
    return newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols;
}

    private static void setMove(String[][] puzzle, String move) {
        int blankRow = getBlankRow(puzzle);
        int blankCol = getBlankCol(puzzle);

        int newRow = blankRow;
        int newCol = blankCol;

        if (move.equals(LEFT)) {
            newCol = blankCol - 1;
        } else if (move.equals(UP)) {
            newRow = blankRow - 1;
        } else if (move.equals(RIGHT)) {
            newCol = blankCol + 1;
        } else if (move.equals(DOWN)) {
            newRow = blankRow + 1;
        } else {
            SOPln("Invalid move. Use WASD to move or QUIT to quit the game.");
            return;
        }

        // Check if the new coordinates are valid
        if (isValidCoordinate(puzzle, newRow, newCol)) {
            // Swap the blank tile with the tile at the new coordinates
            String temp = puzzle[blankRow][blankCol];
            puzzle[blankRow][blankCol] = puzzle[newRow][newCol];
            puzzle[newRow][newCol] = temp;
        } else {
            SOPln("Invalid move. Tile cannot move in that direction.");
        }
    }

    private static int getBlankRow(String[][] puzzle) {
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length; col++) {
                if (puzzle[row][col].equals(BLANK_TILE)) {
                    return row;
                }
            }
        }
        return -1; // Blank tile not found
    }

    private static int getBlankCol(String[][] puzzle) {
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length; col++) {
                if (puzzle[row][col].equals(BLANK_TILE)) {
                    return col;
                }
            }
        }
        return -1; // Blank tile not found
    }

    private static boolean isPuzzleComplete(String[][] solution, String[][] puzzle) {
        // Compare the puzzle with the solution to check if they are equal
        for (int row = 0; row < solution.length; row++) {
            for (int col = 0; col < solution[row].length; col++) {
                if (!solution[row][col].equals(puzzle[row][col])) {
                    return false; // Puzzle is not complete
                }
            }
        }
        return true; // Puzzle is complete
    }

    private static String getMove() {
        // Get the user's move using the global scanner
        SOPln("Enter your move (WASD to move, QUIT to quit): ");
        return sc.nextLine();
    }

    public static boolean simulateTileGame( String[][] solution, String[][] puzzle,
                                            String[] instructions ) {
        return false;
    }
    
    public static void printBoards( String[][] solution, String[][] puzzle ) {
        final String spaceInbetween = "\t\t";
        //Assumes values are delimited by a space " "
        printTitle( solution, puzzle, spaceInbetween );
        
        for( int row = 0; row < solution.length; row++ ) {
            String solutionRow = "";
            String puzzleRow = "";
            for( int col = 0; col < solution[row].length; col++ ) {
                if( col != solution[row].length - 1 )
                    solutionRow += solution[row][col] + " ";
                else
                    solutionRow += solution[row][col];
                    
                puzzleRow += puzzle[row][col] + " ";
            }
            SOPln( solutionRow + spaceInbetween + puzzleRow );
        }
    }
    
    private static void printTitle( String[][] solution, String[][] puzzle, String spaceInbetween ) {
        //Get tab margins for centering titles above puzzles
        final String SOLUTION_TITLE = "Solution";
        final int SOLUTION_TITLE_WIDTH = SOLUTION_TITLE.length();
        final String PUZZLE_TITLE   = "Puzzle";
        final int PUZZLE_TITLE_WIDTH = PUZZLE_TITLE.length();
        int widthInCells = (solution[0].length * 2) - 1;
        final int TAB_SIZE = 3;
        int totalSolutionTitleTabs = 0;
        int totalPuzzleTitleTabs = 0;
        int totalSolutionTitleSpaces = 0;
        int totalPuzzleTitleSpaces = 0;
        int totalSolutionTabSpace = widthInCells - SOLUTION_TITLE_WIDTH;
        int totalPuzzleTabSpace = widthInCells - PUZZLE_TITLE_WIDTH;
        
        if( SOLUTION_TITLE_WIDTH < widthInCells ) {
            totalSolutionTitleTabs += (totalSolutionTabSpace / TAB_SIZE) / 2;
            totalSolutionTitleSpaces += (totalSolutionTabSpace % TAB_SIZE) / 2;
        }
        if( PUZZLE_TITLE_WIDTH < widthInCells ) {
            totalPuzzleTitleTabs += (totalPuzzleTabSpace / TAB_SIZE) / 2;
            totalPuzzleTitleSpaces += (totalPuzzleTabSpace % TAB_SIZE ) / 2;
        }
        
        String solutionTabMargins = "";
        String puzzleTabMargins = "";
        //Get solution tab margins
        for( int rep = 0; rep < totalSolutionTitleTabs; rep++ )
            solutionTabMargins += "\t";
        for( int rep = 0; rep < totalSolutionTitleSpaces; rep++ )
            solutionTabMargins += " ";
        //Get puzzle tab margins
        for( int rep = 0; rep < totalPuzzleTitleTabs; rep++ )
            puzzleTabMargins += "\t";
        for( int rep = 0; rep < totalPuzzleTitleSpaces; rep++ )
            puzzleTabMargins += " ";
        
        //Get title
        String title = solutionTabMargins + SOLUTION_TITLE + solutionTabMargins +
                       spaceInbetween +
                       puzzleTabMargins + PUZZLE_TITLE;
        SOPln( title );
    }
    
    private static boolean isPuzzleValid( String[][] solution ) {
        int totalBlanks = 0;
        for( int row = 0; row < solution.length; row++ )
            for( int col = 0; col < solution[row].length; col++ )
                if( solution[row][col].equals( BLANK_TILE ) )
                    totalBlanks++;
        
        if( totalBlanks == 1 )
            return true;
        
        return false;
    }
    
    private static void printResults( boolean success ) {
        if( success )
            SOPln("\nYou win!");
        else
            SOPln("\nThat one was hard. Better luck next time!");
    }
    
    private static String[][] randomizePuzzle( String[][] solution ) {
        String[][] puzzle = new String[ solution.length ][ solution[0].length ];
        puzzle = copy( solution, puzzle );
        
        Random random = new Random();
    
        for( int row = puzzle.length - 1; row > 0; row-- ) {
            for( int col = puzzle[row].length - 1; col > 0; col-- ) {
                int a = random.nextInt(row + 1);
                int b = random.nextInt(col + 1);
    
                String temp = puzzle[row][col];
                puzzle[row][col] = puzzle[a][b];
                puzzle[a][b] = temp;
            }
        }
        
        return puzzle;
    }
    
    private static String[][] copy( String[][] from, String[][] to ) {
        for( int row = 0; row < from.length; row++ )
            for( int col = 0; col < from[row].length; col++ )
                to[row][col] = from[row][col];
        
        return to;
    }
    
    private static String[][] loadPuzzle( String puzzleFileName ) {
        int numRows = totalLines( puzzleFileName );
        
        Scanner fileScanner = getScanner( puzzleFileName );
        
        //Get numCols and fill first row
        String[] firstLine = fileScanner.nextLine().split( DELIMITER );
        int numCols = firstLine.length;
        
        String[][] solution = new String[ numRows ][ numCols ];
        
        for( int col = 0; col < numCols; col++ )
            solution[0][col] = firstLine[col];
        
        //Fill rest of solution
        for( int row = 1; fileScanner.hasNextLine(); row++ ) {
            String[] line = fileScanner.nextLine().split( DELIMITER );
            for( int col = 0; col < numCols; col++ )
                solution[row][col] = line[col];
        }
        
        return solution;
    }
    
    
    public static int totalLines( String textFileName ) {
        Scanner sc = getScanner( textFileName );
        
        int totalLines = 0;
        
        while( sc.hasNextLine() ) {
            sc.nextLine();
            totalLines++;
        }
        
        sc.close();
        
        return totalLines;
    }
    
    
    public static void clearConsole() {
        System.out.print('\u000C');
    }
    
   
    public static Scanner getScanner( String textFileName ) {
        File file = new File( textFileName );
        Scanner sc = getScanner( file );
        
        return sc;
    }

    public static Scanner getScanner( File file ) {
        Scanner sc = null;
        try {
            sc = new Scanner( file );
        } catch( FileNotFoundException e ) {
            SOPln("The text file " + file.getName() + " was not found in this folder.");
        }
        
        return sc;
    }
    
    public static void SOPln( String str ) {
        System.out.println( str );
    }
    public static void SOPln() {
        System.out.println();
    }
    public static void SOP( String str ) {
        System.out.print( str );
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        // Load a puzzle from a text file
        final String PUZZLE_FILE = "puzzle_0.txt";
        String[][] solution = loadPuzzle(PUZZLE_FILE);

        // Check if the puzzle is valid
        if (!isPuzzleValid(solution)) {
            SOPln("Puzzle is not valid. Puzzles must have at least 1 blank tile\n" +
                  "and no more than 1 blank tile.");
            return; // Exit the game if the puzzle is invalid
        }

        // Randomize the starting puzzle
        String[][] puzzle = randomizePuzzle(solution);

        // Play the game
        boolean success = playTileGame(solution, puzzle);

        // Print results
        printResults(success);
    }
}
