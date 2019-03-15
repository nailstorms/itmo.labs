: abs dup 0 < if -1 * then ;
: is_even 2 % abs not ;
: is_odd is_even not ;
: square dup * ;
: space dup . 32 emit ;

: is_prime
dup 1 > if
  dup 2 = not if
    dup is_odd if 
  1 ( i=3; [i^2 < N]; i+=2 ) 
  repeat
      2 +
      2dup square < if drop 1 exit then
      2dup % not if drop 0 exit then 0 ( n % i != 0 )
  until
    else 0 then ( all even numbers which are >2 are not prime )
  else 1 then ( 2 is prime )
else 0 then ( 1 is not prime ) 
;

: is_prime_allot is_prime 1 allot swap over c! dup c@ ." Result - " . ." , is stored at address - " . cr ;



: collatz
space
repeat
    dup is_odd if 3 * 1 +
    else 2 / then
    space
    dup 1 > not
until drop ;
