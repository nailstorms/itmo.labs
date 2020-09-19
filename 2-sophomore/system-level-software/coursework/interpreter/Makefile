all:
	nasm -f elf64 -g main.asm
	ld -o forth *.o
   
loops: 
	cat lib/loops.frt - | ./forth

clean: 
	rm *.o
	rm forth
