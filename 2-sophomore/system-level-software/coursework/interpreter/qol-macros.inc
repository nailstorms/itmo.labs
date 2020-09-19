%define __prev_word_ 0

%macro native 3
    section .data
    wh_ %+ %2 : dq __prev_word_     ; prev word pointer
    db 0                            ; word name start
    db %1, 0                        ; word name + word name finish
    db %3                           ; immediate flag
    
    %define __prev_word_ wh_%+ %2 
    xt_ %+ %2 :  dq i_ %+ %2 
    section .text
    i_ %+ %2:
%endmacro

%macro native 2
    native %1, %2, 0
%endmacro

%macro endnative 0
    jmp next                        ; return to interpreter
%endmacro

; --------------------------------------------------------------

%macro colon 3
    section .data 
    wh_ %+ %2 : dq __prev_word_
    
    %define __prev_word_ wh_ %+ %2 
    
    db 0                            
    str_ %+ %2:
    db %1, 0
    db %3
    
    xt_ %+ %2 : 
    dq i_docol
%endmacro

%macro colon 2
    colon %1, %2, 0
%endmacro

%macro endcolon 0
    dq xt_exit
%endmacro

; --------------------------------------------------------------

%macro const 2
    colon %1, %2
    dq xt_lit,  %2, xt_exit
%endmacro

; --------------------------------------------------------------

%macro branch 1
  dq xt_branch
  dq %1
%endmacro

%macro branch0 1
  dq xt_branch0
  dq %1
%endmacro

; --------------------------------------------------------------

%macro rspush 1
    sub rstack, 8
    mov qword [rstack], %1
%endmacro

%macro rspop 1
    mov %1, qword [rstack]
    add rstack, 8
%endmacro
