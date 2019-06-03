%define pc     r15 
%define w      r14
%define rstack r13

; ---------------------------------------------------------------

section .data

err_msg: db "Not found: ", 0

last_word: dq __prev_word_
here: dq dictionary
pointer: dq mem   

; ---------------------------------------------------------------

section .bss

resq 1023
rstack_start: resq 1

dictionary: resq 65536                  
mem: resq 65536                  
state: resq 1 
word_buf: resb 1024 

; ---------------------------------------------------------------

section .text

%include "dictionary.inc"

global _start

program:
    dq xt_lit, 8, xt_lit, 2, xt_div, xt_dot, xt_lit, 6, xt_lit, 1, xt_or, xt_dot, xt_bye

_start:
    mov rstack, rstack_start
    mov [stack_start], rsp
    mov pc, program
    jmp next    

next:
    mov w, [pc]
    add pc, 8
    mov w, [w]
    jmp [w]
