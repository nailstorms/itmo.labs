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

native "emit", emit
    pop rdi
    call print_char
endnative

native "execute", execute
    pop rax
    mov w, rax
    jmp [rax]

native "here", here
    push qword here
endnative

native "here_ind", here_ind
    push qword [here]
endnative
