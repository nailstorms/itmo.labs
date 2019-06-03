%include "qol-macros.inc"

; ----- Stack combiners -----

native 'drop', drop
    pop rax
endnative

native swap, "swap"
    pop rax
    pop rdx
    push rax
    push rdx
endnative

native dup, "dup"
    push qword[rsp]
endnative