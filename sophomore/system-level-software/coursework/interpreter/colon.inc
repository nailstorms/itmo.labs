section .rodata
no_such_word: db "No such word: ", 0

section .bss
word_buf:  resq 512
user_dict:  resq 65536
section .data
here:  dq user_dict
fd: dq 0

%define COMPILING 1
%define INTERPRETING 0
state: dq INTERPRETING

const "here", here
const "last-word", last_word
const "is-compiling", state
const "input-fd", fd
const "stack-start", stack_start

%define sys_read_no 0
%define sys_write_no 1

colon "bye", bye
    dq xt_lit, 60
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_syscall


colon "sys-read", sys_read
    dq xt_to_rs
    dq xt_to_rs
    dq xt_to_rs
    dq xt_lit, sys_read_no
    dq xt_from_rs
    dq xt_from_rs
    dq xt_from_rs
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_syscall , xt_drop
endcolon

colon "sys-write", sys_write
    dq xt_to_rs
    dq xt_to_rs
    dq xt_to_rs
    dq xt_lit, sys_write_no
    dq xt_from_rs
    dq xt_from_rs
    dq xt_from_rs
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_lit, 0
    dq xt_syscall 
    dq xt_drop
endcolon


colon "stack-cell", stack_cell
    dq xt_lit, 0
    dq xt_sp
endcolon

colon "file-read-char", file_read_char
    dq xt_to_rs
    dq xt_stack_cell
    dq xt_from_rs
    dq xt_swap
    dq xt_lit, 1
    dq xt_sys_read
    branch0 .fail
    dq xt_lit, 1
    .fail :
        dq xt_exit

colon "file-write-char", file_write_char
    dq xt_swap
    dq xt_to_rs
    dq xt_to_rs
    
    dq xt_stack_cell
    dq xt_dup
    dq xt_from_rs
    dq xt_swap
    dq xt_write_char
    
    dq xt_from_rs
    dq xt_swap
    dq xt_lit, 1
    dq xt_sys_write
    dq xt_drop
    dq xt_drop
endcolon

colon "string-length", string_length
    dq xt_dup
    .loop:
        dq xt_dup
        dq xt_fetch_char
        branch0 .exit
        dq xt_inc
        branch .loop
    .exit:
        dq xt_swap
        dq xt_minus
endcolon

colon "print-cr", print_cr
    dq xt_lit, 1
    dq xt_lit, 10
    dq xt_file_write_char
endcolon

colon "print-string-counted", print_string_counted
    dq xt_to_rs
    dq xt_to_rs
    dq xt_lit, 1
    dq xt_from_rs
    dq xt_from_rs
    dq xt_sys_write
    dq xt_drop
endcolon

colon "print-string", print_string
    dq xt_dup
    dq xt_string_length
    dq xt_print_string_counted
endcolon

colon "char-is-space", char_is_space
    dq xt_to_rs
    dq xt_rs_fetch
    dq xt_lit,  ' '
    dq xt_equals
    
    dq xt_rs_fetch
    dq xt_lit, 10
    dq xt_equals
    dq xt_lor
    
    dq xt_rs_fetch
    dq xt_lit, 13
    dq xt_equals
    dq xt_lor
    
    dq xt_from_rs
    dq xt_lit, '\t'
    dq xt_equals
    dq xt_lor
endcolon

colon "string-terminate", string_terminate
    dq xt_lit, 0
    dq xt_swap
    dq xt_write_char
endcolon

colon "file-read-word", file_read_word
    dq xt_dup
    dq xt_to_rs
    dq xt_to_rs

    .read_space:
      dq xt_dup
      dq xt_file_read_char
      branch0 .eof

      dq xt_dup
      dq xt_char_is_space
      branch0 .commit

      dq xt_drop
      branch .read_space

    .read:
      dq xt_dup
      dq xt_file_read_char
      branch0 .eof

      dq xt_dup
      dq xt_char_is_space
      dq xt_not
      branch0 .end

    .commit:
      dq xt_rs_fetch
      dq xt_write_char
      dq xt_from_rs
      dq xt_inc
      dq xt_to_rs
      branch .read

    .end:
      dq xt_drop
    .eof:
      dq xt_drop
      dq xt_rs_fetch
      dq xt_string_terminate
      
      dq xt_from_rs
      dq xt_from_rs
      dq xt_minus
endcolon

colon ",", comma
    dq xt_here
    dq xt_fetch
    dq xt_write
    
    dq xt_here
    dq xt_fetch
    dq xt_lit, 8
    dq xt_plus
    
    dq xt_here
    dq xt_write
endcolon

colon "c,", char_comma
    dq xt_here
    dq xt_fetch
    dq xt_write_char
    
    dq xt_here
    dq xt_fetch
    dq xt_inc
    
    dq xt_here
    dq xt_write
endcolon

colon "2dup", ddup
    dq xt_to_rs
    dq xt_dup
    dq xt_rs_fetch
    dq xt_swap
    dq xt_from_rs
endcolon

colon "2inc", dinc
    dq xt_to_rs
    dq xt_inc
    dq xt_from_rs
    dq xt_inc
endcolon

colon "string-equals", string_equals
    .start:
      dq xt_ddup
      dq xt_fetch_char
      dq xt_swap
      dq xt_fetch_char
      dq xt_equals
      branch0 .no
      dq xt_dup
      dq xt_fetch_char
      branch0 .yes
      dq xt_dinc
      branch .start
    .yes:
      dq xt_drop
      dq xt_drop
      dq xt_lit, 1, xt_exit
    .no:
      dq xt_drop
      dq xt_drop
      dq xt_lit, 0
endcolon

colon "dict-word-name", dict_word_name
    dq xt_lit
    dq 9
    dq xt_plus
endcolon

colon "find-word", find_word
    dq xt_to_rs
    dq xt_last_word
    dq xt_fetch
    dq xt_state
    dq xt_fetch
    branch0 .loop
    dq xt_fetch
    .loop:
      dq xt_dup
      branch0 .not_found

      dq xt_dup
      dq xt_dict_word_name
      dq xt_rs_fetch
      dq xt_string_equals
      dq xt_not
      branch0 .found

      dq xt_fetch
      branch .loop
    .found:
      dq xt_lit, 1
    .not_found:
      dq xt_from_rs
      dq xt_drop
endcolon

colon "'", tick, 1
    dq xt_fd
    dq xt_fetch
    dq xt_lit
    dq word_buf
    dq xt_file_read_word
    branch0 .eof
    
    dq xt_lit
    dq word_buf
    dq xt_find_word
    branch0 .no_word
    
    dq xt_cfa
    dq xt_state
    branch0 .interp
    
    dq xt_lit
    dq xt_lit
    dq xt_comma
    dq xt_comma
    .interp:
        dq xt_exit
    .no_word:
        dq xt_lit
        dq no_such_word
        dq xt_print_string
        dq xt_print_string
        dq xt_print_cr
        dq xt_exit
    .eof:
        dq xt_drop
endcolon

colon "cfa", cfa
    dq xt_lit, 9
    dq xt_plus
    dq xt_dup
    dq xt_string_length
    dq xt_plus
    dq xt_lit, 2
    dq xt_plus
endcolon

; ( dest source - )
colon "string-copy", string_copy
    .loop:
        dq xt_ddup
        dq xt_fetch_char
        dq xt_to_rs
        dq xt_rs_fetch
        dq xt_swap
        dq xt_write_char
        dq xt_dinc
        dq xt_from_rs
        dq xt_not
        branch0 .loop
        dq xt_drop
        dq xt_drop
endcolon

colon "create", create
    dq xt_here
    dq xt_fetch 
    
    dq xt_last_word
    dq xt_fetch
    dq xt_comma
    
    dq xt_last_word
    dq xt_write
    
    dq xt_lit, 0
    dq xt_char_comma
    
    dq xt_dup
    dq xt_here
    dq xt_fetch
    dq xt_swap
    dq xt_string_copy
    
    dq xt_string_length
    dq xt_here
    dq xt_fetch
    dq xt_plus
    dq xt_inc
    dq xt_here
    dq xt_write
    dq xt_char_comma
endcolon

colon ":", colon
  .restart:
    dq xt_fd
    dq xt_fetch
    dq xt_lit
    dq word_buf
    dq xt_file_read_word
    branch0 .restart
    dq xt_lit, 0
    dq xt_lit
    dq word_buf
    dq xt_create
    dq xt_lit, COMPILING
    dq xt_state
    dq xt_write
    dq xt_lit
    dq i_docol
    dq xt_comma
endcolon

colon ";", semicolon, 1
    dq xt_lit, INTERPRETING
    dq xt_state
    dq xt_write
    
    dq xt_lit
    dq xt_exit
    dq xt_comma
endcolon

colon "char-is-digit", char_is_digit
    dq xt_to_rs
    dq xt_rs_fetch
    dq xt_lit, '9'+1
    dq xt_lesser
    dq xt_lit, '0'-1
    dq xt_rs_fetch
    dq xt_lesser
    dq xt_land
    
    dq xt_from_rs
    dq xt_drop
endcolon

colon "drop0", drop0
    dq xt_dup
    branch0 .is_zero
    dq xt_exit
    .is_zero:
        dq xt_drop
endcolon

colon "char-to-digit", char_to_digit
    dq xt_lit, '0'
    dq xt_minus
endcolon

colon "string-parse-unsigned-number", string_parse_unsigned_number
    dq xt_dup
    dq xt_to_rs
    
    dq xt_lit, 0
    dq xt_to_rs
    
    .loop:
      dq xt_dup
      dq xt_fetch_char
      dq xt_dup
      dq xt_char_is_digit
      
      branch0 .end
      dq xt_char_to_digit
      dq xt_lit, 10
      dq xt_from_rs
      dq xt_mul
      dq xt_plus
      dq xt_to_rs
      
      dq xt_inc
      branch .loop
    .end:
      dq xt_drop
      dq xt_from_rs
      dq xt_swap
      dq xt_from_rs
      dq xt_minus
      dq xt_drop0
endcolon

colon "neg", neg
    dq xt_lit, 0
    dq xt_swap
    dq xt_minus
endcolon

colon "inc", inc
    dq xt_lit, 1
    dq xt_plus
endcolon
    
colon "string-parse-number", string_parse_number
      dq xt_dup
      dq xt_fetch_char
      dq xt_dup
      dq xt_char_is_digit
      dq xt_not
      
      branch0 .unsigned
      dq xt_dup
      dq xt_lit, '-'
      dq xt_equals
      dq xt_not
      
      branch0 .negative

      dq xt_dup
      dq xt_lit, '+'
      dq xt_equals
      dq xt_not
      
      branch0 .positive

    .negative:
      dq xt_drop
      dq xt_inc
      dq xt_string_parse_unsigned_number
      
      dq xt_dup
      branch0 .error
      dq xt_inc
      
      dq xt_swap
      dq xt_neg
      dq xt_swap
      dq xt_exit
    .positive:
      dq xt_drop
      
      dq xt_inc
      dq xt_string_parse_unsigned_number
      dq xt_dup
      branch0 .error
      dq xt_inc
      dq xt_exit
    .unsigned:
      dq xt_drop
      dq xt_string_parse_unsigned_number
      dq xt_exit 
    .error:
      dq xt_drop
      dq xt_lit, 0
endcolon

colon "follows-branch", follows_branch
    dq xt_here
    dq xt_fetch
    dq xt_lit, 8
    dq xt_minus
    dq xt_fetch
    
    dq xt_dup
    dq xt_lit
    dq xt_branch0
    dq xt_equals
    dq xt_swap
    
    dq xt_lit
    dq xt_branch
    dq xt_equals
    dq xt_lor
endcolon

; (  fd -- )
colon "interpret-fd", interpret_fd
    dq xt_lit
    dq xt_fd
    dq xt_fetch
    dq xt_to_rs
    
    dq xt_fd
    dq xt_write

    .loop:
        dq xt_fd
        dq xt_fetch
        dq xt_lit
        dq word_buf
        dq xt_file_read_word
        
        branch0 .eof

    dq xt_lit
    dq word_buf
    dq xt_interpret

    branch .loop

    .eof:
        dq xt_from_rs
        dq xt_fd
        dq xt_write
endcolon

colon "xt-is-immediate", xt_is_immediate
    dq xt_lit, 1
    dq xt_minus
    dq xt_fetch_char
endcolon

colon "interpret", interpret
    .start:
        dq xt_dup
        dq xt_find_word
        
        branch0 .number
        
        dq xt_swap
        dq xt_drop
        dq xt_cfa

        dq xt_state
        dq xt_fetch
        branch0 .interpreting

        dq xt_dup
        dq xt_xt_is_immediate
        dq xt_not
        branch0 .interpreting

    .compiling:
        dq xt_comma
        dq xt_exit
    .interpreting:
        dq xt_execute
        dq xt_exit

    .number:
        dq xt_dup
        dq xt_string_parse_number
        branch0 .no_word


        dq xt_swap
        dq xt_drop
        dq xt_state
        dq xt_fetch
        branch0 .exit

        dq xt_follows_branch
        dq xt_not
        branch0 .is_branch

    .not_branch:
        dq xt_lit
        dq xt_lit
        dq xt_comma
    .is_branch:
        dq xt_comma
        dq xt_exit

    .no_word:
      dq xt_lit
        dq no_such_word
        dq xt_print_string
        dq xt_print_string
        dq xt_print_cr

    .exit:
endcolon

native "stack-restore", stack_restore
    mov rsp, [stack_start]
endnative

colon "trap", trap
    dq xt_lit, wh_trap + 9, xt_print_string, xt_print_cr
endcolon

init:
    dq xt_lit, 0, xt_interpret_fd, xt_bye
