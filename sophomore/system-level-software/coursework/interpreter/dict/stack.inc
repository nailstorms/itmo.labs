native "drop", drop
    pop rax
endnative

native "swap", swap
    pop rax
    pop rdx
    push rax
    push rdx
endnative

native "dup", dup
    push qword[rsp]
endnative

native "rot", rot
    pop rax
    pop rdx
    pop rcx
    push rdx
    push rcx
    push rax
endnative

native 'buf', buf
    push qword user_buf
endnative

native ".", show_top
    pop rdi
    call print_int
endnative

native ".S", show_stack
    mov rcx, rsp
    .loop:
        cmp rcx, [stack_start] 
        jae next
        mov rdi, [rcx]
        push rcx
        call print_int
        call print_newline
        pop rcx
        add rcx, 8
        jmp .loop
       