type bool_expr =
  | Lit of string
  | Not of bool_expr
  | And of bool_expr * bool_expr
  | Or of bool_expr * bool_expr

let rec truthOf a valA b valB = function
  | Lit x -> if x = a then valA else valB
  | Not e -> not(truthOf a valA b valB e)
  | And(c, d) -> truthOf a valA b valB c && truthOf a valA b valB d
  | Or(f, g) -> truthOf a valA b valB f || truthOf a valA b valB g;;

let truth_table a b expr =
  [(true, true, truthOf a true b true expr);
  (true, false, truthOf a true b false expr);
  (false, true, truthOf a false b true expr);
  (false, false, truthOf a false b false expr)];;
