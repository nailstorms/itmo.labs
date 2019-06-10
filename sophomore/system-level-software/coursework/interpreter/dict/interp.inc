const buffer, input_buf
const mem, user_mem
const lw, lw
const state, state
const here, [here]
const in_fd, in_fd
const dp, dp
	
section .data
    stack_start: dq 0

native init, "init"
    mov qword [state], 0
    mov rstack, rstack_start
    mov pc, interpreter_stub
    cmp qword [stack_start], 0
    je .first
    mov rsp, [stack_start]
    jmp next
    .first:
        mov [stack_start], rsp
endnative

section .rodata
interpreter_stub:
    dq xt_lit, 0
    dq xt_interpret_fd
    dq xt_bye

colon "interpret-fd", interpret_fd
    dq xt_lit, in_fd 
    dq xt_fetch, xt_to_rs
    dq xt_lit, in_fd
    dq xt_write
    .loop:
        dq xt_buffer
        dq xt_word
        branch0 .end_of_input

    dq xt_buffer
    dq xt_interpret

    branch .loop

    .end_of_input:
        dq xt_from_rs
        dq xt_lit, in_fd, xt_write
endcolon


colon "interpret", interpret
.start:
    dq xt_dup
    dq xt_find 

    dq xt_dup
    branch0 .number
    dq xt_cfa

    dq xt_state
    dq xt_fetch
    branch0 .interpreting

    dq xt_dup
    dq xt_lit, 1, xt_minus
    dq xt_fetch_char
    dq xt_not
    branch0 .interpreting

    dq xt_swap, xt_drop
    dq xt_comma
    dq xt_exit

    .interpreting:
        dq xt_swap, xt_drop
        dq xt_execute
        dq xt_exit

    .number:
        dq xt_drop, xt_dup
        dq xt_number
        branch0 .no_word

    dq xt_swap, xt_drop
    dq xt_state, xt_fetch
    branch0 .exit

    dq xt_here, xt_lit, 8, xt_minus, xt_fetch

    dq xt_dup
    dq xt_lit, xt_branch0
    dq xt_equals, xt_not
    branch0 .is_branch

    dq xt_dup
    dq xt_lit, xt_branch
    dq xt_equals, xt_not
    branch0 .is_branch

    dq xt_lit, xt_lit, xt_comma

    .is_branch:
        dq xt_drop
        dq xt_comma
        dq xt_exit

    .no_word: 
        dq xt_drop
        dq xt_prints
        dq xt_lit, no_such_word, xt_prints
    .exit:
endcolon