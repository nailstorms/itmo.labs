global _start

section .text

; string_length - возвращает длину нуль-терминированной строки
; 
; Входные данные:
; rdi - указатель на первый символ строки
; 
; Выходные данные:
; rax - длина строки

string_length:
    xor rax, rax
    loops:
      cmp byte[rdi + rax], 0
      je strlen_end
      inc rax
      jmp loops
    strlen_end:  
  ret

; print_string - возвращает нуль-терминированную строку
; 
; Входные данные:
; rdi - указатель на первый символ строки

print_string:
  xor rax, rax
  call string_length 
  mov rsi, rdi    	; rsi - указатель на буфер
  mov rdx, rax    	; rdx - количество символов в строке
  mov rdi, 1    		
  mov rax, 1   		
  syscall
    ret

; print_char - возвращает один символ
; 
; Входные данные:
; dil - символ для вывода

print_char:
    dec rsp
    mov [rsp], dil
    mov rsi, rsp
    mov rdx, 1
    mov rdi, 1
    mov rax, 1
    syscall
    inc rsp
    ret

; print_newline - вовзращает служебный символ для перехода на новую строку (0хА)

print_newline:
    xor rax, rax
    mov dil, 0xa
    call print_char
    ret

; print_uint - возвращает беззнаковое 64-разрядное число
; 
; Входные данные:
; rdi - число для конверсии

print_uint:
    push r12      ; callee-saved
    push r13      ; регистры
    mov r12, rsp
    mov rax, rdi
    mov r13, 10
    dec rsp      	; производим push вручную, т.к. push работает только с 8-байтовыми
        		; словами, а нам необходима только одна ячейка на 1 байт
    mov byte [rsp], 0
    prnt_uint_loop:
  xor rdx, rdx    	; rdx - старшие 64 бита числа, всегда равные 0
  div r13
  add rdx, 48    	; в ASCII кодировки чисел начинаются с 48
  dec rsp
  mov [rsp], dl    	; перемещаем байты числа в низ стека (деление на основание
        			; системы счисления вовзращает его цифры в обратном порядке)
  cmp rax, 0    		; выход из цикла если частное = 0
  je prnt_uint_end
  jmp prnt_uint_loop
    prnt_uint_end:
  mov rdi, rsp
  call print_string
  mov rsp, r12
  pop r13
  pop r12
  ret

; print_int - возвращает знаковое 64-разрядное число
; 
; Входные данные:
; rdi - число для конверсии

print_int:
    xor rax, rax
    test rdi, rdi
    js neg
    call print_uint
    jmp prnt_int_end
    neg:
  push rdi
  mov rdi, '-'
  call print_char
  pop rdi
  neg rdi
  call print_uint
    prnt_int_end:
  ret

; read_char - считывает один символ из потока ввода в регистр al
; 
; Выходные данные:
; al - считанный символ из потока ввода

read_char:
    xor rax, rax
    mov rax, 0
    mov rdi, 0
    mov rdx, 1      ; считываем символ
    dec rsp      ; выделяем один байт в стеке для буфера
    mov rsi, rsp    ; rsp - указатель на буфер, где хранится наш символ
    syscall
    test rax, rax
    jz read_char_end    ; если количество прочитанных символов = 0
        ; то конец ввода
    xor rax, rax
    mov al, [rsp]
    read_char_end:
  inc rsp
  ret 

; read_word - считывает слово посимвольно из потока ввода
; 
; Входные данные:
; rdi - указатель на начало буфера со словом
; rsi - максимальное количество символов в слове
;
; Выходные данные:
; rax - указатель на начало буфера (если размер слова удовлетворяет макс. кол-ву символов)
; rdx - количество символов в слове (0 если размер слова превосходит макс. размер)

read_word:
    push r13
    push r14
    xor r14, r14
    mov r10, rsi
    mov r13, rdi
    read_loop:
      call read_char
      cmp al, 32
      jne write_char
      jmp read_loop
    read_further:
      call read_char
      cmp r14, r10
      je read_out
      cmp al, 32     ; пробел
      je read_success
    write_char:
      cmp al, 9     ; табуляция
      je read_success
      cmp al, 0     ; конец ввода
      je read_success
      mov byte [r13+r14], al
      inc r14
      jmp read_further
    read_success:
      mov rax, r13
      mov byte [r13+r14], 0
     mov rdx, r14     ; длина строки
      pop r14
      pop r13
      ret
    read_out:
      mov rax, 0
      xor rdx, rdx     ; длина строки = 0
      pop r14
      pop r13
      ret

global _start

section .text

; string_length - возвращает длину нуль-терминированной строки
; 
; Входные данные:
; rdi - указатель на первый символ строки
; 
; Выходные данные:
; rax - длина строки

string_length:
    xor rax, rax
    loops:
      cmp byte[rdi + rax], 0
      je strlen_end
      inc rax
      jmp loops
    strlen_end:  
  ret

; print_string - возвращает нуль-терминированную строку
; 
; Входные данные:
; rdi - указатель на первый символ строки

print_string:
  xor rax, rax
  call string_length 
  mov rsi, rdi    ; rsi - указатель на буфер
  mov rdx, rax    ; rdx - количество символов в строке
  mov rdi, 1    ;
  mov rax, 1    ; 
  syscall
    ret

; print_char - возвращает один символ
; 
; Входные данные:
; dil - символ для вывода

print_char:
    xor rax, rax
    mov rax, 1
    push rdi
    mov rdi, rsp
    call print_string
    pop rdi
    ret

; print_newline - вовзращает служебный символ для перехода на новую строку (0хА)

print_newline:
    xor rax, rax
    mov dil, 0xa
    call print_char
    ret

; print_uint - возвращает беззнаковое 64-разрядное число
; 
; Входные данные:
; rdi - число для конверсии

print_uint:
    push r12      ; callee-saved
    push r13      ; регистры
    mov r12, rsp
    mov rax, rdi
    mov r13, 10
    dec rsp     	 ; производим push вручную, т.к. push работает только с 8-байтовыми
       		 ; словами, а нам необходима только одна ячейка на 1 байт
    mov byte [rsp], 0
    prnt_uint_loop:
  xor rdx, rdx    ; rdx - старшие 64 бита числа, всегда равные 0
  div r13
  add rdx, 48    ; в ASCII кодировки чисел начинаются с 48
  dec rsp
  mov [rsp], dl    ; перемещаем байты числа в низ стека (деление на основание
        ; системы счисления возвращает его цифры в обратном порядке)
  cmp rax, 0    ; выход из цикла если частное = 0
  je prnt_uint_end
  jmp prnt_uint_loop
    prnt_uint_end:
  mov rdi, rsp
  call print_string
  mov rsp, r12
  pop r13
  pop r12
  ret

; print_int - возвращает знаковое 64-разрядное число
; 
; Входные данные:
; rdi - число для конверсии

print_int:
    xor rax, rax
    test rdi, rdi
    js neg
    call print_uint
    jmp prnt_int_end
    neg:
  push rdi
  mov rdi, '-'
  call print_char
  pop rdi
  neg rdi
  call print_uint
    prnt_int_end:
  ret

; read_char - считывает один символ из потока ввода в регистр al
; 
; Выходные данные:
; al - считанный символ из потока ввода

read_char:
    xor rax, rax
    mov rax, 0
    mov rdi, 0
    mov rdx, 1      ; считываем символ
    dec rsp      ; выделяем один байт в стеке для буфера
    mov rsi, rsp    ; rsp - указатель на буфер, где хранится наш символ
    syscall
    test rax, rax
    jz read_char_end    ; если количество прочитанных символов = 0
        ; то конец ввода
    xor rax, rax
    mov al, [rsp]
    read_char_end:
  inc rsp
  ret 

; read_word - считывает слово посимвольно из потока ввода
; 
; Входные данные:
; rdi - указатель на начало буфера со словом
; rsi - максимальное количество символов в слове
;
; Выходные данные:
; rax - указатель на начало буфера (если размер слова удовлетворяет макс. кол-ву символов)
; rdx - количество символов в слове (0 если размер слова превосходит макс. размер)

read_word:
    push r13
    push r14
    xor r14, r14
    mov r10, rsi
    mov r13, rdi
    read_loop:
      call read_char
      cmp al, 32
      jne write_char
      jmp read_loop
    read_further:
      call read_char
      cmp r14, r10
      je read_out
      cmp al, 32     ; пробел
      je read_success
    write_char:
      cmp al, 9     ; табуляция
      je read_success
      cmp al, 0     ; конец ввода
      je read_success
      mov byte [r13+r14], al
      inc r14
      jmp read_further
    read_success:
      mov rax, r13
      mov byte [r13+r14], 0
     mov rdx, r14     ; длина строки
      pop r14
      pop r13
      ret
    read_out:
      mov rax, 0
      xor rdx, rdx     ; длина строки = 0
      pop r14
      pop r13
      ret

; parse_uint - преобразовывает строку до первого
; нечислового символа в 64-разрядное беззнаковое число
;
; Входные данные:
; rdi - указатель на строку
;
; Выходные данные:
; rax - обработанное число
; rdx - количество обработанных символов

parse_uint:
    xor rax, rax
    xor rsi, rsi
    mov r8, 10
    xor rcx, rcx
    xor rdx, rdx
    parse_uint_loop:
  mov sil, [rdi+rcx]
  cmp sil, 48
  jl parse_uint_end
  cmp sil, 57
  jg parse_uint_end
  inc rcx
  sub sil, 48
  mul r8
  add rax, rsi
  jmp parse_uint_loop
    parse_uint_end:
  mov rdx, rcx
  ret

; parse_int - преобразовывает строку до первого
; нечислового символа в 64-разрядное знаковое число
;
; Входные данные:
; rdi - указатель на строку
;
; Выходные данные:
; rax - обработанное число
; rdx - количество обработанных символов

parse_int:
    xor rax, rax
    cmp byte [rdi], 45
    je negative
    call parse_uint
    ret
    negative:
  inc rdi
  call parse_uint
  cmp rdx, 0
  je parse_int_end
  neg rax
  inc rdx
    parse_int_end:
  ret 

; string_equals - сравнивает две строки, возвращает 1 если они равны и 0 в противоположном
; случае
;
; Входные данные:
; rdi - адрес начала первой строки
; rsi - адрес начала второй строки
;
; Выходные данные:
; rax - показатель равенства/неравенства строк

string_equals:
    xor rcx, rcx
    streq_loop:
  mov al, byte[rdi+rcx]    ; начинаем посимвольно сравнивать строки
  mov dl, byte[rsi+rcx]
  cmp al, dl
  jne not_eq
  inc rcx        ; если равны, продолжаем дальше
  cmp al, 0      ; проверяeм, не конец ли это строки
  je eq
  jmp streq_loop
    not_eq:
      mov rax, 0
      ret
    eq:
      mov rax, 1
      ret  

; string_copy - копирует переданную строку в указанный буфер
;
; Входные данные:
; rdi - указатель на буфер-источник
; rsi - указатель на буфер-"получатель"
; rdx - максимальный размер буфера
;
; Выходные данные:
; rax - указатель на начало буфера, в котором содержится нужная строка

string_copy:
    xor rcx, rcx
    copy_loop:
  cmp rcx, rdx
  je copy_oob
    mov rax, [rdi+rcx]
    mov [rsi+rcx], rax
    cmp byte [rdi+rcx], 0
  je copy_end
  inc rcx
  jmp copy_loop
    copy_end:
  mov rax, rsi
    copy_oob:
  mov rax, 0
  ret
