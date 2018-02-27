% composition problem
int: n;
set of int: NOTE = 1..n;
set of int: ORDER = 1..n;

set of int: NOTE1= 1..n-1;
set of int: ORDER1 = 1..n-1;

array[ORDER] of var NOTE: order;
array[ORDER1] of var NOTE1: diffs;

constraint forall(i in ORDER1)(diffs[i] = abs(order[i] - order[i+1]));

include "alldifferent.mzn";
constraint alldifferent(order);
constraint alldifferent(diffs);

solve satisfy;

output ["order = ",show(order),";\n"];