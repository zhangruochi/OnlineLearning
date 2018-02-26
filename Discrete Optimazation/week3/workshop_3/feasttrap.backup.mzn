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
constraint taste[dish[1]] = salty;
constraint taste[dish[len]] = sweet;

constraint forall(i in 1..len-1)
                 (taste[dish[i]] != taste[dish[i+1]]);
constraint forall(i in 1..len-1)
                 (taste[dish[i]] = spicy -> taste[dish[i+1]] in {bland, sweet});
constraint forall(i in 1..len-1)
                 (taste[dish[i]] = sour -> taste[dish[i+1]] in {bland, umame});
constraint forall(i in 1..len-1)
                 (taste[dish[i]] = sweet -> taste[dish[i+1]] in {sour, salty, bland});

%temp constraint
constraint forall(i in 1..len-1)
                (temp[dish[i]] = hot -> 
                 forall(j in i+1..len)
                       (temp[dish[j]] = cold ->
                       exists(k in i+1..j-1)(temp[dish[k]] = warm)));


constraint forall(i in 1..len-2)
                  (heavy[dish[i]] /\ heavy[dish[i+1]] -> 
                    not heavy[dish[i+2]]);



var int: obj = sum(i in 1..len)(value[dish[i]]) 
               + len-1 + 
               sum(i in 1..len-1)(temp[dish[i]] != temp[dish[i+1]]) + 
               sum(i in 1..len-1)(heavy[dish[i]] != heavy[dish[i+1]]);

solve maximize obj;

output ["dish = \(dish);\nobj = \(obj);\n"];




 