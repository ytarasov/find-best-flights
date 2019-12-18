# High Level Solution
1. Load all airports, provide every airport unique index and create related hash table.
2. Load all flights, index them by airport index and by date index - create related complex hash map.
3. Based on airports and flights hash tables - create flights graph. It should be able to give us possibility find all available flights from current airport for specified time frame.
4. Create a data structure to store N cheapest routes.
5. Use DFS : move recursively through airports, marking the ones you have already visited, and if you reach your destination, check if this route is in the top routes.


