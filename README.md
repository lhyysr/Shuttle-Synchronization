# Shuttle-Synchronization
Logan Airport has upgraded its fleet of shuttles serving its terminals to driverless electric shuttles.

# Description
  The shuttles simply go around the airport in a circle from terminal i to terminal (i+1) mod k, where k is the number of terminals to be served (K= 6 for Logan, given the stops at terminals A, B, C, D, and E, and at T station).  
  Each shuttle has a fixed capacity N, i.e., no more than N travelers can ride on the bus. When a shuttle arrives to a terminal stop, some of its riders may leave while others may board up to the capacity limit.  
  To discourage travelers from rushing to catch a shuttle that is already loading its passengers, anyone arriving to the shuttle terminal stop while the shuttle is boarding must wait for the next shuttle.  
  In order for the shuttle to close its doors and leave the terminal, some passenger must tell it to do so.  
  To save energy and avoid unnecessary stopping, the shuttle should not stop at a terminal stop unless at least one passenger needs to get on the shuttle or off the shuttle at that stop. 
  The passengers in this setting repeat the following steps forever: (1) pick a starting terminal, (2) wait for shuttle, (3) board shuttle, (4) pick a destination terminal, (5) wait for shuttle to arrive at destination, and (6) get off the shuttle.  
  Shuttle Synchronization should allow the airport to operate M > 1 shuttles. Clearly, a safety requirement is that there would never be more than a single shuttle loading or unloading passengers at any one terminal.  
  Implement your protocol using Java threads, allowing the shuttle thread to interact with passenger threads. You should assume N=10 and K=6, and you should assume that there are 50 passengers in the airport. You should also assume that the shuttle thread spends some amount of time to go from one station to the next (by having the thread sleep for some random amount of time). Show that your code works as specified above and that it is free of deadlocks.
