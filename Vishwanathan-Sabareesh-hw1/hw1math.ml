type expr = Const of int
          | Var of string
          | Plus of {arg1: expr; arg2: expr}
          | Mult of {arg1: expr; arg2: expr}
          | Minus of {arg1: expr; arg2: expr}
          | Div of {arg1: expr; arg2: expr};;

let rec evaluate e = match e with
  | Const x -> x
  | Var x -> 0
  | Plus {arg1 = e1; arg2 = e2} -> (evaluate e1) + (evaluate e2)
  | Mult {arg1 = e1; arg2 = e2} -> (evaluate e1) * (evaluate e2)
  | Minus {arg1 = e1; arg2 = e2} -> (evaluate e1) - (evaluate e2)
  | Div {arg1 = e1; arg2 = e2} -> (evaluate e1) / (evaluate e2);;
