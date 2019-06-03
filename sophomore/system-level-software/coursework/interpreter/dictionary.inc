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
    pop rax
    pop rdx
    cmp rax, rdx
    setl al
    movzx rax, al
    push rax
endnative

native ">", greater
    pop rax
    pop rdx
    cmp rax, rdx
    setg al
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

; todo land & lor (wth is this even)



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