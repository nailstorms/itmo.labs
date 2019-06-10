colon ":", colon
   .restore_state:
        dq xt_buffer
        dq xt_read

    branch0 .restore_state
    dq xt_lit, 0          
    dq xt_buffer
    dq xt_create
    
    dq xt_lit, 1
    dq xt_state
    dq xt_write

    dq xt_lit
    dq i_docol
    dq xt_comma
endcolon

colon ';', semicolon, 1
    dq xt_lit, 0, xt_pushmode_addr
    dq xt_write
    dq xt_lit, xt_exit
    dq xt_comma
endcolon

native ",", comma
    mov rax, [here]
    pop qword [rax]
    add qword [here], 8
endnative

native "c,", char_comma
    mov rax, [here]
    pop rdx
    mov [rax], dl
    add qword[here], 1
endnative

; -------------------------- New word creation --------------------------

native "create", create
    mov rcx, [last_word]
    mov rsi, [here]
    mov [rsi], rcx
    mov [last_word], rsi
    add rsi, 8
    mov byte [rsi], 0
    inc rsi
    pop rdi
    push rsi
    call string_copy
    pop rsi
    push rsi
    mov rdi, rsi
    call string_length
    pop rsi
    add rsi, rax
    inc rsi
    pop rax
    mov [rsi], al
    inc rsi
    mov [here], rsi
    jmp next
    
; -------------------------- Word lookup and storage --------------------------

native "word", word
    pop rdi
    call read_word
    push rdx
endnative

native "number", number
    pop rdi
    call parse_int
    push rax
    push rdx
endnative

native "find", find
    mov rsi, [last_word]

    mov rax, [state]
    test rax, rax
    jz .loop
    mov rsi, [rsi]
    .loop:
        mov rdi, [rsp]
        push rsi
        add rsi, 9
        call string_equals
        pop rsi
        test rax, rax
        jnz .found

        mov rsi, [rsi]
        test rsi, rsi
        jnz .loop
    .not_found:
        mov qword [rsp], 0
        jmp next
    .found:
        mov [rsp], rsi
endnative
    
