# Search pathfinding algorithms

Implementation of various pathfinding algorithms and their results for the cheapest route problem. This repository has implementations of the following algorithms:  
>A*, IDA*, DFBnB, DFID, DFS.  

This program will let you explore the differences between uninformed and informed algorithms, their results, and have a deeper understanding of how they work.

## About the algorithms

### Uninformed algorithms

DFID and DFS are both uninformed algorithms meaning they travel the searchspace without information regarding the distance between the current state and the goal state.

### Informed algorithms

A*, IDA* and DFBnB are informed algorithms meaning they travel the searchspace with a heuristic calculation, a "guess" of the distance between the current state and the goal state.

With the heuristic function we can try and guess what is the cost to travel that distance. The used heuristic function in our case is very simple and is called diagonal distance, where we calculate the amount of steps it takes from a the state to the goal where all the steps have no cost.  

In the program itself you can experiment and use a different heuristic function and see what results you may get.

## The problem

The game board is a NxN sized board that represent a topographic map, where every cell of the board has a different road type. An autonomous car moves from the start cell to the end cell, and it's goal is to find the cheapest route.

## Game rules

The car is allowed to travel one cell at a time, in every direction, as long as the cell exists or not marked as a cliff.
Allowed directions are :  
L = left
LD = left + down
D = down
RD = right + down
R = right
UR = up + right
U = up
UL = up + left

## Path cost

Traveling using basic directions (north, south, east and west) costs as the following:

| Road mark | Road type | Cost|
|-----------|-----------|-----|
| D | Dirt | 1 |  
| R | Asphalt | 3 |
| H | Sand | 5 |
| X | Cliff | - |
| S | Starting point | 0 |
| G | Goal point | 5 |

Traveling to diagonal neighboring cells costs the same but when traveling to a sand road, the cost is 10.

## Search space modeling

The modeling of the search space is implemented in the Node.java file.

To understand the model we need to understand what is the search space of the problem.

A state is a snapshot of the probmel solving process, in our case where is the car currently on the board.

![Alt text](image.png)  

The search space is all possible states of the game, where we move from one state to another using the allowed operations which are NORTH, NORTHEAST, SOUTH, etc. Using the above state example, the children of the previous state using the EAST, SOUTHEAST, SOUTH operators in respect:

![South](image-1.png)  ,  ![Alt text](image-2.png)  ,  

Since this is a pathfinding algorithm even if several states have the same coordinates but have a different path, they will not count as the same state.

Each state will hold a pointer to the previous state, and remember which operation was used in the previous step. This is so we could trace back the resulting path once we reach the goal.

The idea of that is to try and reduce the space complexity (memory used) of the program, holding only the relevant path instead of the entire search space.

## Implementation

### How to run

Clone the repository and inside the directory of the program use the following command to compile:

>$ javac *.java

Once compiled you can run the program using:

>$ java Ex1 input.txt

Where input.txt should be inside said directory. Please note that you can change the board and the commands as you wish using the pattern discussed in the Input headline.

The output of the program is an output.txt file discussed in the output headline.

### Input

The program reads a single Input.txt file using the following pattern:

> DFBnB  
> clockwise  
>with time  
>no open  
>10  
>(2,6),(6,2)  
>XXXXXXXXXX  
>XDRRRSXXXX  
>XDXXXXXXXX  
>HDHHHHHHHH  
>HDHHHHHHHH  
>HGRRHHHHHH  
>XXHHHHHHHH  
>XXHHHHHHHH  
>XHHHHHHHHH  
>XXHHHHHHHH

Pattern breakdown:  

>DFBnb

Which pathfinding algorithm should the program use.

>clockwise  

clockwise / counter-clockwise for rotation of the state operators.  

For the IDA*and the A* algorithms add "old-first" or "new-first", for when the heuristic function value of two states are the same then should the program prioritize new states use "clockwise new-first" or older states "clockwise old-first"

> with time  

with time / without time, for timing of the program.

>no open

open / no open, should the program print the current size of the future state candidates.

>10

The size of the board will be 10x10.

>XXXXXXXXXX  
>XDRRRSXXXX  
>XDXXXXXXXX  
>HDHHHHHHHH  
>HDHHHHHHHH  
>HGRRHHHHHH  
>XXHHHHHHHH  
>XXHHHHHHHH  
>XHHHHHHHHH  
>XXHHHHHHHH

The symbols X,D,R,S,H,G are the cells of the board matrice according to the rules of the board.

### Output

The output of the program is an Output.txt file in the same directory, with the results of the algorithm.

>L-L-L-LD-D-D-D  
>Num: 27  
>Cost: 17  
>0.036 seconds  

Output breakdown:

>L-L-L-LD-D-D-D  

This is the path taken using the allowed operators.

>Num: 27  

The amount of state node created in search of the path, this is the actual time complexity of the program.

>Cost: 17  

Cost result of said path.

>0.036 seconds  

Timing of the program.
