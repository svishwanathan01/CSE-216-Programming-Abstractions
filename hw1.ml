(*Problem 1*)
let rec pow x n =
  if n = 0 then 1
  else x * pow x (n-1);;
(* pow(3,5);; *)

let rec float_pow x n =
  if n = 0 then 1.0
  else x *. float_pow x (n-1);;
(* float_pow(3.1,5.0);; *)

(*Problem 2*)
let rec compress = function
  | a::(b::_ as t) -> if a = b then compress t else a::compress t
  | string -> string ;;
(* compress ["a";"a";"a";"a";"b";"c";"c";"a";"a";"d";"e";"e";"e";"e"];; *)

(*Problem 3*)
let rec remove_if (lst: 'a list) (f: 'a -> bool): 'a list =
  match lst with
    | [] -> []
    | h::t -> if f h then remove_if t f else h::remove_if t f;;
(* remove_if [1;2;3;4;5] (fun x-> x mod 2 = 1);; *)

(*Problem 4*)
let slice list i k =
  if i > k then []
  else let rec keep n = function
        | [] -> []
        | h :: t -> if n = 0 then [] else h :: keep (n-1) t
      in
      let rec cut n = function
        | [] -> []
        | h :: t as l -> if n = 0 then l else cut (n-1) t
      in
      keep (k - i) (cut i list);;
(*slice ["a";"b";"c";"d";"e";"f";"g";"h"] 3 20;;*)

(*Problem 5*)
let rec equivs_help f a lst = match lst with
  | [] -> []
  | h::t -> if f a h then equivs_help f a t else h::equivs_help f a t;;

let rec equivs_helper f a lst = match lst with
  | [] -> []
  | h::t -> if f a h then h::equivs_helper f a t else equivs_helper f a t;;

let rec equivs f lst =
  match lst with
  | [] -> []
  | h::t -> let temp = equivs_help f h t in
  equivs_helper f h (h::t) :: equivs f temp;;

(*Problem 6*)
let prime n =
  let n = abs n in
  let rec noDivisor d =
    d * d > n || (n mod d <> 0 && noDivisor (d+1) ) in
  n <> 1 && noDivisor 2;;

let goldbachpair n =
  let rec aux d =
    if prime d && prime (n-d) then (d, n-d)
    else aux (d+1) in
  aux 2;;
(*goldbachpair 12*)

(*Problem 7*)
let rec equiv_on f g lst =
  match lst with
    | [] -> true
    | h::t -> if f h = g h then equiv_on f g t else false

(*Problem 8*)
let rec pairwisefilter cmp given_list =
  match given_list with
    | [] -> []
    | a::b::t -> let tail = pairwisefilter cmp t in
      cmp a b :: tail
    | head::[] -> [head];;

(*Problem 9*)
let rec polynomial a = fun f ->
  match a with
    | [] -> 0
    | (b, c) :: t -> let remaining = polynomial t in
      let x = float_pow (float_of_int f) c |> int_of_float in b * x + remaining f;;

(*Problem 10*)
let rec map fn lst = match lst with
  | [] -> []
  | h :: t -> (fn h) :: (map fn t);;

let rec powerset lst =
  match lst with
  | [] -> [[]]
  | h :: t -> let lst = powerset t in
    lst @ map (fun f -> h :: f) lst;;
