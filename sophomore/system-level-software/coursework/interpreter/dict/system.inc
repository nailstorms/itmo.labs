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