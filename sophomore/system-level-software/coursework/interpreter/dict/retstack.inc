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