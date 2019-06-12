<<<<<<< HEAD
global _start
%include "qol-macros.inc"
%include "lib.inc"

=======
>>>>>>> parent of 9034aab... sls cw: testing, add makefile
%define pc     r15 
%define w      r14
%define rstack r13

; ---------------------------------------------------------------

<<<<<<< HEAD
section .text

%include "dictionary.inc"
%include "colon.inc"

; ---------------------------------------------------------------

section .bss

resq 1023
rstack_start: resq 1
user_mem: resq 65536

; ---------------------------------------------------------------

section .data 
last_word: dq __prev_word_
stack_start: dq 0
dp: dq user_mem
 
=======
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

>>>>>>> parent of 9034aab... sls cw: testing, add makefile
; ---------------------------------------------------------------


global _start

_start: 
    mov rstack, rstack_start
    mov [stack_start], rsp
    mov pc, init

next:
    mov w, pc
    add pc, 8
    mov w, [w]
    jmp [w]
