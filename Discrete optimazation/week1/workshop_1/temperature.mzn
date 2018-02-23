array[int] of int: readings;
int: start;
int: l = length(readings);

enum ACTION = {heat, strongly_heat, cool, strongly_cool, do_nothing};

set of int: TIME0 = 0..length(readings);
set of int: TIME1 = 1..length(readings);

array[ACTION] of int: adjust = [+1,+4,-2,-5,0];
array[ACTION] of int: c = [1,5,3,9,0];

array[TIME0] of var 25..30: temp;
array[TIME1] of var ACTION: choice;



% output [show(adjust[strongly_heat])];


constraint temp[0] = start;
constraint forall(t in 1..l)(temp[t] = (temp[t-1] + readings[t]) div 2 +
                                        adjust[choice[t]]); 
                                        
var int: cost = sum(t in TIME1)(c[choice[t]]);                                                                                
                                                                                                                                                                

solve minimize cost;

output ["temp = \(temp);\nchioce = \(choice); \ncost = \(cost);"];
