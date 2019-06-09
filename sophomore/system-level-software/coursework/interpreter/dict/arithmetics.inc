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