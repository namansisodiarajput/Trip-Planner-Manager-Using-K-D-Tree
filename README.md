# Trip-Planner-Manager-Using-K-D-Tree
Problem Statement - program that creates a travel plan where you visit every continent from a given origin city and back using the shortest distance traveled.
There are 6 continents to be visited (excluding Antarctica) so you should create a plan where you visit 6 cities (including the origin) in any order. 
The trip should start and end at the origin city which will be the input of the program. 
The distance between the two cities can be calculated using the formula given in the first [answer](https://stackoverflow.com/a/27943/1986034) to this question. 
The cities.json file attached below has all the cities, their geolocation (latitude, longitude), and the continent they belong to (param name: contId).





Solution -:

#Requirment - 

##install import org.json.*  jar file if not available in system, else will give import error.

##Change json Data file location pointing  ReadContinentData.Class . my system is ubuntu so variable is set to private String JSON_DATA_PATH = "src/TravelPlanAlgorithm/cities.json" change location according to system.


TO RUN PROGRAM -: 
            Step 1 - Placed all the file in Folder
            Step 2 - Run TravelPlanMain.Class from Java Commend


Solution Description -: Using K-D Tree Search Implementation to Find The Shortest Path From Source City Visiting All the Continent

    Step 1 - Using ReadContinentData.Class to Read JSON Data & Removing unwanted Data that is Not needed for Computation like rank,dest,images,airports etc.
    Step 2 - Finding Average Mean Coordinate of All the Continent and Calculating Shortest Path Possible Via Continent Way.first calculating what is the shortest
             Path of Continent. like Asia -> North-America -> South - America -> .....
    Step 3 - Than Finding Shortest Distance City in the Continent from Given Point. Usine K-D Search Tree Algorithm to Find Closed City in Given Coordinate.


Sample Input & Output -:

Input - 
Enter Your Starting City Code - 
BOM

Output - 
Shortest Path Recommended From System - 
BOM ( Mumbai,asia )  --> OBY ( Ittoqqortoormiit,north-america )  --> CCK ( Home Island,oceania )  --> FEN ( Fernando de Noronha,south-america )  --> BSA ( Bosaso,africa )  --> OSW ( Orsk,europe )  --> Back To BOM ( Mumbai,asia ) 

Total Distance Travelled in Journey = 45865.19 KM 




Input - 
Enter Your Starting City Code - 
OBY

Output - 
Shortest Path Recommended From System - 
OBY ( Ittoqqortoormiit,north-america )  --> ROR ( Koror,oceania )  --> CKZ ( Çanakkale,asia )  --> CAY ( Cayenne,south-america )  --> TNG ( Tangier,africa )  --> IFJ ( Ísafjörður,europe )  --> Back To OBY ( Ittoqqortoormiit,north-america ) 

Total Distance Travelled in Journey = 63308.79 KM




Input - 
Enter Your Starting City Code - 
CAY

Output - 
Shortest Path Recommended From System - 
CAY ( Cayenne,south-america )  --> SFL ( São Filipe,africa )  --> FLW ( Flores Island,europe )  --> BJV ( Bodrum,asia )  --> TAB ( Tobago,north-america )  --> GMR ( Totegegie,oceania )  --> Back To CAY ( Cayenne,south-america ) 

Total Distance Travelled in Journey = 53548.62 KM 


Input - 
Enter Your Starting City Code - 
TAB

Output - 
Shortest Path Recommended From System - 
TAB ( Tobago,north-america )  --> AUQ ( Hiva Oa,oceania )  --> CKZ ( Çanakkale,asia )  --> MUN ( Maturín,south-america )  --> VXE ( São Vicente,africa )  --> FLW ( Flores Island,europe )  --> Back To TAB ( Tobago,north-america ) 

Total Distance Travelled in Journey = 62714.43 KM 


Input - 
Enter Your Starting City Code - 
FLW

Output - 
Shortest Path Recommended From System - 
FLW ( Flores Island,europe )  --> EUN ( Laayoune,africa )  --> CAY ( Cayenne,south-america )  --> CKZ ( Çanakkale,asia )  --> YYT ( St. John s,north-america )  --> AUQ ( Hiva Oa,oceania )  --> Back To FLW ( Flores Island,europe ) 

Total Distance Travelled in Journey = 59276.92 KM 





Used Reference - 
https://www.sanfoundry.com/java-program-find-nearest-neighbour-using-k-d-tree-search/
https://rosettacode.org/wiki/K-d_tree#Java
https://en.wikipedia.org/wiki/Nearest_neighbor_search
https://stackoverflow.com/questions/28028618/2d-kd-tree-and-nearest-neighbour-search




