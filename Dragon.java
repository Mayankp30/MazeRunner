import java.io.*;



public class Dragon

{

    private static final String INPUT_FILE =   "/Users/Mayank/IdeaProjects/Demo/src/maze.txt";

    private static final String OUTPUT_FILE =  "/Users/Mayank/IdeaProjects/Demo/src/solution.txt";



    private static int ENTER_ROW;

    private static int ENTER_COL;

    private static int EXIT_ROW;

    private static int EXIT_COL;





    private static char[][] maze = null;        //holds maze data

    private static int rows, cols;



    private static boolean isSolved = false;    //keep track of whether or not the

    //maze is solved



    private static boolean[][] wasVisited;      //array to keep track of where you've

    //already been in the maze



    public static void main(String args[])

    {



/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*      READ IN THE MAZE DATA AND STORE IT IN THE ARRAY                          */



        try

        {

            // Open the input file

            BufferedReader infile = new BufferedReader(new FileReader(INPUT_FILE));



            //read the first six lines which specify the

            //number of rows and columns of the maze

            //and where the entrance and exit are



            String numRows = infile.readLine();

            String numCols = infile.readLine();

            String enterRow = infile.readLine();

            String enterCol = infile.readLine();

            String exitRow = infile.readLine();

            String exitCol = infile.readLine();



            //Get the integer values of the numbers of rows and columns

            //stored in the input strings



            rows = Integer.valueOf(numRows).intValue();

            cols = Integer.valueOf(numCols).intValue();



            ENTER_ROW = 	Integer.valueOf(enterRow).intValue() - 1;

            ENTER_COL = 	Integer.valueOf(enterCol).intValue() - 1;



            EXIT_ROW = Integer.valueOf(exitRow).intValue() - 1;

            EXIT_COL = Integer.valueOf(exitCol).intValue() - 1;



            maze = new char[rows][cols];           //initialize the array of chars

            wasVisited = new boolean[rows][cols];  //initialize the boolean array



            //loop through the rows of the file

            for(int r = 0; r < rows; r++)

            {

                //loop through the columns of each row

                for(int c = 0; c < cols; c++)

                {

                    //store the char representing each maze location in the array

                    maze[r][c] = (char) infile.read();



                    //set initial value to false for wasVisited at each location

                    wasVisited[r][c] = false;

                }

                //ignore any extra characters on the line

                infile.readLine();

            }



            //close the file

            infile.close();

        }



        //Java requires you to handle some common error situations

        //We'll just let the program crash and print out a message.



        catch (FileNotFoundException e)

        {

            System.out.println("Input file not found.");

        }

        catch (IOException e)

        {

            System.out.println("Error reading from file.");

        }



/*      END READING IN THE MAZE DATA                                             */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */



        //Solve the maze by making this single method call:



        solveMaze(ENTER_ROW, ENTER_COL);





/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*      PRINT OUT THE SOLVED MAZE                                                */



        //Print out the solution to OUTPUT_FILE

        try

        {

            // Open the output file

            PrintWriter outfile = new PrintWriter(new FileOutputStream(OUTPUT_FILE));



            for(int r = 0; r < rows; r++)     //loop through the rows

            {

                for(int c = 0; c < cols; c++)  //move through the columns of each row

                {

                    outfile.print(maze[r][c]);  //print out each character in solved maze

                }

                outfile.println();             //start a new line after each row

            }

            //close the file

            outfile.close();

        }

        catch (IOException e)  //Catch IOExceptions

        {

            System.out.println("Error Writing to output file.");

        }



/*      END PRINTING OUT SOLUTION                                                */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */



    } //end main



    private static void solveMaze(int theRow, int theCol)

    {

        //Array to hold number of steps each cell is from start

        int[][] steps = new int[rows][cols];



        //Fill the array with -2 to indicate unprocessed cells

        for(int i = 0; i < rows; i++)

            for(int j = 0; j < cols; j++)

                steps[i][j] = -2;



        //A list of cells to process during flood fill

        int[][] toDo = new int[rows*cols][2];



        toDo[0][0] = ENTER_ROW;

        toDo[0][1] = ENTER_COL;



        steps[ENTER_ROW][ENTER_COL] = 0;



        int processNext = 0, endOfList = 1;



        while(processNext != endOfList && steps[EXIT_ROW][EXIT_COL] == -2)

        {

            //Process the cell at (toDo[processNext][1],toDo[processNext][2])



            //Store current row and column

            int cRow = toDo[processNext][0];

            int cCol = toDo[processNext][1];



            //Store current row and column number of steps

            int cSteps = steps[cRow][cCol];



            //Process N if it is within the maze

            if(cRow > 0){

                //if the cell to the north is unprocessed

                if(steps[cRow - 1][cCol] == -2){

                    //if the cell to the north is not a wall

                    if(maze[cRow - 1][cCol] != 'X'){



                        //record number of steps and add new cell to toDo list

                        steps[cRow - 1][cCol] = cSteps + 1;

                        toDo[endOfList][0] = cRow - 1;

                        toDo[endOfList][1] = cCol;

                        endOfList++;

                    }

                    else{  //otherwise it's a wall - so mark it with a -1

                        steps[cRow-1][cCol] = -1;

                    }

                }

            }



            //Process S if it is within the maze

            if(cRow < rows - 1){

                //if the cell to the south is unprocessed

                if(steps[cRow + 1][cCol] == -2){

                    //if the cell to the north is not a wall

                    if(maze[cRow + 1][cCol] != 'X'){



                        //record number of steps and add new cell to toDo list

                        steps[cRow + 1][cCol] = cSteps + 1;

                        toDo[endOfList][0] = cRow + 1;

                        toDo[endOfList][1] = cCol;

                        endOfList++;

                    }

                    else{  //otherwise it's a wall - so mark it with a -1

                        steps[cRow+1][cCol] = -1;

                    }

                }

            }



            //Process E if it is within the maze

            if(cCol < cols - 1){

                //if the cell to the east is unprocessed

                if(steps[cRow][cCol + 1] == -2){

                    //if the cell to the north is not a wall

                    if(maze[cRow][cCol + 1] != 'X'){



                        //record number of steps and add new cell to toDo list

                        steps[cRow][cCol + 1] = cSteps + 1;

                        toDo[endOfList][0] = cRow;

                        toDo[endOfList][1] = cCol + 1;

                        endOfList++;

                    }

                    else{  //otherwise it's a wall - so mark it with a -1

                        steps[cRow][cCol+ 1] = -1;

                    }

                }

            }



            //Process W if it is within the maze

            if(cCol > 0){

                //if the cell to the west is unprocessed

                if(steps[cRow][cCol - 1] == -2){

                    //if the cell to the west is not a wall

                    if(maze[cRow][cCol - 1] != 'X'){



                        //record number of steps and add new cell to toDo list

                        steps[cRow][cCol - 1] = cSteps + 1;

                        toDo[endOfList][0] = cRow;

                        toDo[endOfList][1] = cCol - 1;

                        endOfList++;

                    }

                    else{  //otherwise it's a wall - so mark it with a -1

                        steps[cRow][cCol - 1] = -1;

                    }

                }

            }



            //We've looked at the cells N,S,E,W of the current cell, so

            //we're ready to process the next one.

            processNext++;



        }//End flood fill

/*

      //Print out the results of the flood fill, to test

      for(int i = 0; i < rows; i++){

         for(int j = 0; j < cols; j++){

           System.out.print(steps[i][j] + " ");

           if(steps[i][j] < 10 && steps[i][j] >= 0)

             System.out.print(" ");

         }

         System.out.println();

      }

*/

        //Now, step backwards from the finish to the start

        //marking the path with '*'



        int bRow = EXIT_ROW;     //keep track of where you are

        int bCol = EXIT_COL;     //as you back out



        maze[EXIT_ROW][EXIT_COL] = '*';

        int currentStepsAway;



        //We know how many steps to take, so a for loop will work

        for(int i = 1; i <= steps[EXIT_ROW][EXIT_COL]; i++){



            currentStepsAway = steps[bRow][bCol];



            //look N if it's inside the maze

            if(bRow > 0 && steps[bRow - 1][bCol] == currentStepsAway - 1 ){

                maze[bRow - 1][bCol] = '*';

                bRow--;

            }

            // otherwise look S if it's inside the maze

            else if(bRow < rows - 1 && steps[bRow + 1][bCol] == currentStepsAway - 1 ){

                maze[bRow + 1][bCol] = '*';

                bRow++;

            }

            // otherwise look E if it's inside the maze

            else if(bCol < cols - 1 && steps[bRow][bCol + 1] == currentStepsAway - 1 ){

                maze[bRow][bCol + 1] = '*';

                bCol++;

            }

            // otherwise look W if it's inside the maze

            else if(bCol > 0 && steps[bRow][bCol - 1] == currentStepsAway - 1 ){

                maze[bRow][bCol - 1] = '*';

                bCol--;

            }

        }//End back trace



    }

}//end MazeRunner

