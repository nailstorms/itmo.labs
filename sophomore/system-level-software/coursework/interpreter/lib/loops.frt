: IMMEDIATE last-word @ cfa 1 - dup c@ 1 or swap c! ;

: if ' branch0 , here_ind 0 , ; IMMEDIATE
: else ' branch , here_ind 0 , swap here_ind swap ! ; IMMEDIATE
: then here_ind swap ! ; IMMEDIATE
: endif ' then execute ; IMMEDIATE

: repeat here_ind ; IMMEDIATE
: until ' branch0 , , ; IMMEDIATE

: rot >r swap r> swap ;
: -rot swap >r swap  r> ;

: over >r dup r> swap ;
: 2dup over over ;

: abs dup 0 < if -1 * then ;

: is-even 2 % abs not ;
: is-odd is-even not ;

: square dup * ;
: is-prime
  dup 1 > if
   dup 2 = not if
    dup is-odd if 
  1
  repeat
      2 +
      2dup square < if drop 1 exit then
      2dup % not if drop 0 exit then 0
  until
      else 0 then
    else 1 then
  else 0 then
;

: for
  ' swap , 
  ' >r , 
  ' >r ,
  here_ind 
  ' r> , 
  ' r> ,
  ' 2dup , 
  ' >r , 
  ' >r , 
  ' < ,
  ' branch0 ,
  here_ind 0 , 
  swap
; IMMEDIATE

: endfor
  ' r> , 
  ' lit , 1 , 
  ' + , 
  ' >r ,
  ' branch , 
           , here_ind swap ! 
  ' r> ,
  ' drop , 
  ' r> , 
  ' drop ,
; IMMEDIATE
