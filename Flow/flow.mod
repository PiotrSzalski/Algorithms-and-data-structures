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
printf "Max Flow: %g\n\n", flow; 
 
data; 
param n := 8; 
param : E : c :=
0 4 4
0 2 4
0 1 4
1 5 2
1 3 1
2 6 1
2 3 4
3 7 7
4 6 1
4 5 1
5 7 5
6 7 2
;end;
