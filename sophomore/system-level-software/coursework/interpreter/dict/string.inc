native "emit", emit
    pop rdi
    call print_char
endnative

colon "count", count
     dq xt_dup
    .loop:
         dq xt_dup
         dq xt_fetch_char
         branch0 .exit
         dq xt_lit, 1
         dq xt_plus
         branch .loop
    .exit:
         dq xt_swap
         dq xt_minus
endcolon

colon "printc", printc
    dq xt_to_rs
    .loop:
        dq xt_rs_fetch
        branch0 .exit
        dq xt_dup 
        dq xt_fetch_char
        dq xt_emit
        
        dq xt_lit, 1
        dq xt_plus
        dq xt_from_r
        dq xt_lit, 1
        dq xt_minus
        dq xt_to_r
        branch .loop

    .exit:
        dq xt_from_rs 
        dq xt_drop 
        dq xt_drop
endcolon

colon "prints", prints
     dq xt_dup
     dq xt_count
     dq xt_printc
endcolon