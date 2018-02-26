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


var set of WARRIOR: raid;
constraint card(raid) >= l /\ card(raid) <= u;
constraint not(dummy in raid);

constraint sum(person1,person2 in raid where person1 < person2)(hates[clan[person1],clan[person2]]) <=m;

var int: obj = sum(person in raid)(strength[person]);
solve maximize obj;

output ["raid = \(raid);\n"] ++
       ["obj = \(obj);\n"];
