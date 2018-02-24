var 0..9: C;
var 0..9: A;
var 0..9: O;
var 0..9: Z;
var 0..9: H;
var 0..9: I;
var 0..9: P;
var 0..9: T;
var 0..9: E;
var 0..9: S;

constraint C != 0;
constraint Z != 0;
constraint T != 0;

constraint (C*100+A*10+O)*2 + Z*100+H*10+I + P*10+I = T*1000+E*100+S*10+T;

constraint P = 3*Z;
constraint O = 0;

include "alldifferent.mzn";
constraint alldifferent([C,A,O,Z,H,I,P,T,E,S]);

solve satisfy;

