global _start
%include "qol-macros.inc"
%include "lib.inc"


%define pc     r15 
%define w      r14
%define rstack r13

; ---------------------------------------------------------------

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
