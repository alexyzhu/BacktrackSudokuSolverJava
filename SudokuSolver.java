import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SudokuSolver {
	public static int[][] sgrid;  // sudoku grid
	
	
	public static int[] getNextCell()
	{
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if(sgrid[i][j] == 0)
					return new int[] {i, j};
			}
		}
		return new int[] {-1};
	}
	
	
	public static boolean isCellValid(int row, int col, int num)
	{
		for(int r=0; r < 9; ++r)
		{
			if(sgrid[r][col] == num)
				return false;
		}
		
		for(int c=0; c<9; c++)
		{
			if(sgrid[row][c] == num)
				return false;
		}
		
		int boxRow = 3*(row/3);
		int boxCol = 3*(col/3);
		
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(sgrid[boxRow+i][boxCol+j] == num)
					return false;
			}
		}
		return true;
	}
	
	
	public static void inputPuzzle()
	{
		System.out.print("Input File Name: ");
		Scanner inpFileName = new Scanner(System.in);
		String fileName = inpFileName.next();
		File ogridFile = new File(fileName);
		try
		{
			Scanner fileReader = new Scanner(ogridFile);
			sgrid = new int[9][9];
			
			int i = 0;
			while(fileReader.hasNextLine())
			{
				String row = fileReader.nextLine();
				String[] rowNums = row.split(" ", -1);
				int j = 0;
				for(int x=0; x<9; x++)
				{
					sgrid[i][j++] = Integer.parseInt(rowNums[x]);
				}
				i++;
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File Not Found");
		}
	}
	
	
	public static boolean solvePuzzle()
	{
		int[] curCell = getNextCell();
	    if(curCell[0] == -1)
	        return true;
	    
	    for(int num=1;num<10;num++)
	    {
	    	if(isCellValid(curCell[0], curCell[1], num))
	    	{
	    		sgrid[curCell[0]][curCell[1]] = num;
	    		if(solvePuzzle())
	    			return true;
	    		sgrid[curCell[0]][curCell[1]] = 0;
	    	}
	    }
	    return false;
	}
	
	
	public static void printSolved()
	{
		for(int[] row: sgrid)
		{
			for(int num: row)
			{
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args)
	{
		inputPuzzle();
		boolean solved = solvePuzzle();
		if(solved)
			printSolved();
		else
			System.out.println("No Solution");
	}
}
