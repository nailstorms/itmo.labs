%include "qol-macros.inc"

; ----- Stack combiners -----

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



; ----- Arithmetic operators -----

native "+", plus
    pop rax
    add [rsp], rax
endnative

native "*", mul
    pop rax
    pop rdx
    imul rdx
    push rax
endnative

native "/", div
    pop rcx
    pop rax
    xor rdx, rdx
    idiv rcx
    push rax
endnative

native "%", mod
    pop rcx
    pop rax
    xor rdx, rdx
    idiv rcx
    push rdx
endnative

native "-", minus
    pop rax
    sub [rsp], rax
endnative

native "=", equals
    pop rax
    pop rdx
    cmp rax, rdx
    sete al
    movzx rax, al
    push rax
endnative

native "<", lesser
    pop rdx
    pop rax
    cmp rax, rdx
    setl al
    movzx rax, al
    push rax
endnative



; ----- Logical operators -----

native "not", not
    pop rax
    test rax, rax
    setz al
    movzx rax, al
    push rax
endnative

native "and", and
    pop rax
    and [rsp], rax
endnative

native "or", or
    pop rax
    or [rsp], rax
endnative

native "land", land
    pop rax
    pop rdx
    test rax, rax
    jz .no
    push rdx
    jmp next
    
   .no:
    push rax
endnative

native "lor", lor
    pop rax
    pop rdx
    test rax, rax
    jnz .yes
    push rdx
    jmp next
    
   .yes:
    push rax
endnative



; ----- Return stack operators -----

native ">r", to_rs
    pop rax
    rspush rax
endnative

native "r>", from_rs
    rspop rax
    push rax
endnative

native "r@", rs_fetch
    push qword [rstack]
endnative




; ----- Memory management operators -----

native "@", fetch
    pop rax
    push qword[rax]
    jmp next

native "!", write
    pop rax
    pop rdx
    mov [rax], rdx
endnative

native "c!", write_char
    pop rax
    pop rdx
    mov [rax], dl
endnative

native "c@", fetch_char
    pop rax
    movzx rax, byte [rax]
    push rax
endnative

native "execute", execute
    pop rax
    mov w, rax
    jmp [rax]
    


; ----- Execution management operators -----

native "docol", docol
    rspush pc
    add w, 8
    mov pc, w
endnative

native "branch", branch
    mov pc, [pc]
endnative

native "branch0", branch0
    pop rax
    test rax, rax
    jnz .skip
    mov pc, [pc]
    jmp next
    
  .skip:
    add pc, 8
endnative

native "exit", exit
    rspop pc
endnative



; ----- Service operators -----

native "lit", lit
    push qword [pc]
    add pc, 8
endnative

native "syscall", syscall
    pop r9
    pop r8
    pop r10
    pop rdx
    pop rsi
    pop rdi
    pop rax
    syscall
    push rax
    push rdx
endnative

native "bye", bye
    call system_exit