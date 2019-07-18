/* number of nodes */ 
param n, integer, >= 2; 
/* set of nodes */ 
set V, default {0..(n-1)}; 
/* set of edges */ 
set E, within V cross V; 
/* c[i,j] - capacity of edge (i,j) */ 
param c{(i,j) in E}, > 0; 
/* source */ 
param s, symbolic, in V, default 0; 
/* target */ 
param t, symbolic, in V, != s, default (n-1); 
/* f[i,j] - flow through edge (i,j) */ 
var f{(i,j) in E}, >= 0, <= c[i,j]; 
/* total flow from s to t */ 
var flow, >= 0; 
/* node[i] - constraint for node i */ 
s.t. node{i in V}: 
/* sum of flow into node must be equal to sum of flow from node */ 
sum{(j,i) in E} f[j,i] + (if i = s then flow) = sum{(i,j) in E} f[i,j] + (if i = t then flow); 
/* maximize the total flow */ 
maximize obj: flow; 
solve; 
printf "\n"; 
printf "Max Matching: %g\n\n", flow; 
 
data; 
param n := 18; 
param : E : c :=
0 1 1
0 2 1
0 3 1
0 4 1
0 5 1
0 6 1
0 7 1
0 8 1
1 14 1
1 11 1
1 13 1
2 15 1
2 13 1
2 10 1
3 14 1
3 9 1
3 13 1
4 16 1
4 13 1
4 10 1
5 16 1
5 10 1
5 13 1
6 15 1
6 16 1
6 11 1
7 14 1
7 16 1
7 12 1
8 15 1
8 14 1
8 16 1
9 17 1
10 17 1
11 17 1
12 17 1
13 17 1
14 17 1
15 17 1
16 17 1
;end;
