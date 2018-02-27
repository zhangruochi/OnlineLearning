% Beauty Trap
% Create the most magnificient feast 
enum DISH;
enum TASTE = {spicy, sour, salty, sweet, umame, bland};
enum TEMP = {hot, cold, warm};

array[DISH] of TASTE: taste;
array[DISH] of TEMP: temp;
array[DISH] of bool: heavy;
array[DISH] of int: value;

int: len; % length of banquet
set of int: COURSE = 1..len;

array[COURSE] of var DISH: dish;

include "alldifferent.mzn";
constraint alldifferent(dish);



%taste constraint
include "regular.mzn";
constraint regular([taste[dish[c]] | c in COURSE],7,card(TASTE), d, 1, {5});

array[1..7,TASTE] of 0..7: d= 
        [| 0,0,4,0,0,0
         | 0,0,0,5,0,7
         | 0,0,0,0,6,7
         | 2,3,0,5,6,7
         | 0,3,4,0,0,7
         | 2,3,4,5,0,7 
         | 2,3,4,5,6,0 |];

%temp constraint
constraint regular([temp[dish[c]] | c in COURSE], 3, card(TEMP), e, 2, 1..3);
array[1..3,TEMP] of 0..3: e =
     [| 1,0,3
      | 1,2,3
      | 1,2,3 |];

constraint forall(i in 1..len-2)
                  (heavy[dish[i]] /\ heavy[dish[i+1]] -> 
                    not heavy[dish[i+2]]);



var int: obj = sum(i in 1..len)(value[dish[i]]) 
               + len-1 + 
               sum(i in 1..len-1)(temp[dish[i]] != temp[dish[i+1]]) + 
               sum(i in 1..len-1)(heavy[dish[i]] != heavy[dish[i+1]]);

solve maximize obj;

output ["dish = \(dish);\nobj = \(obj);\n"];




 