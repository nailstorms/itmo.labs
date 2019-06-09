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