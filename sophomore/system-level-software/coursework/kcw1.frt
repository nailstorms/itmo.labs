( n -- n[-n] )
: abs dup 0 < if -1 * then ;

( n -- res )
: is-even 2 % abs not ;

( x -- res )
: is-odd is-even not ;

( n -- res )
: square dup * ;

: space dup . 32 emit ;

( n -- res )
: is-prime
  dup 1 > if
   dup 2 = not if
    dup is-odd if 
  1 ( i=3; [i^2 < n]; i+=2 ) 
  repeat
      2 +
      2dup square < if drop 1 exit then ( n > i^2 )
      2dup % not if drop 0 exit then 0 ( n % i != 0 )
  until
      else 0 then ( all even numbers which are >2 are not prime )
    else 1 then ( 2 is prime )
  else 0 then ( 1 is not prime ) 
;

( n -- res )
: is-prime-allot is-prime 1 allot swap over c! dup c@ ." Result - " . ." , is stored at address - " . cr ;

( str-addr1 str-addr2 -- res )
: concat
  2dup
  over count dup >r ( push 1st string length to return stack )
  over count 1 + + ( check 1st string for null-terminator )
  heap-alloc >r ( push 1st string address to return stack )
  swap r@ ( get 1st string from return stack )
  swap string-copy ( copy 1st string )
  r> r> swap dup ( get 1st string address for resulting address calculation )
  >r + ( calculate address for 2nd string copying )
  swap string-copy ( copy 2nd string )
  r> ( return resulting string address )
  prints drop drop
  ;

( n -- res )
: collatz
  space
  repeat
    dup is-odd if 3 * 1 +
    else 2 / then
    space
    dup 1 > not
  until drop ;
