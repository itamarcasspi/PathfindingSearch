# Searching pathfinding algorithms
Implementation of various pathfinding algorithms and their results for the cheapest route problem.

# The problem

The game board is a NxN sized board that represent a topographic map, where every cell of the board has a different road type. An autonomous car moves from the start cell to the end cell, and it's goal is to find the cheapest route.

# Game rules

The car is allowed to travel one cell at a time, in every direction, as long as the cell exists or not marked as a cliff.

# Path cost

Traveling using basic directions (north, south, east and west) costs as the following:

| Road mark | Road type | Cost|
|-----------|-----------|-----|
| D | Dirt | 1 |  
| R | Asphalt | 3 |
| H | Sand | 5 |
| X | Cliff | - |
| S | Starting point | 0 |
| G | Goal point | 5 |
