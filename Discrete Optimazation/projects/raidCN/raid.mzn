% assignment 2
% select a set of raiding warrior
enum WARRIOR;
WARRIOR: dummy; 
enum CLAN; % the clan they belong too

int: l; % minimum raid party size
int: u; % maximum raid party size
int: m; % limit of hatreds

array[WARRIOR] of int: strength;
array[WARRIOR] of CLAN: clan;

array[CLAN,CLAN] of 0..1: hates;

array[1..u] of var WARRIOR: raid;
constraint forall(i in 1..u-1)(raid[i]+(raid[i]!= dummy) <= raid[i+1]);
constraint sum(i in 1..l)(raid[i] != dummy) >= 1;



constraint sum(person1,person2 in raid where person1 < person2)(hates[clan[person1],clan[person2]]) <=m;

var int: obj = sum(person in raid)(strength[person]);
solve maximize obj;

output ["raid = {"] ++ [if fix(raid[i]) != dummy then show(raid[i])
++ if i < u /\ fix(raid[i+1]) != dummy then ", " else "" endif else "" endif | i in 1..u ] ++ ["};\n"] ++ ["obj = \(obj);\n"];

