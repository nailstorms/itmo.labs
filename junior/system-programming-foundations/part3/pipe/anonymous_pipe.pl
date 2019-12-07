#!/usr/bin/perl -T
use strict;
use warnings qw(FATAL all);

$ENV{'PATH'} = '/usr/bin';
$ENV{CDPATH}="";
$ENV{ENV}="";

if (scalar(@ARGV) != 1) { die "Only one file is supported.\n"; }
my $filename = shift(@ARGV);
open (my $file, '<', $filename) 
	or die "Error while opening $filename.\n";
pipe (my $pipe_read, my $pipe_write) 
	or die "Error while creating pipe.\n";

my $wc_process = fork();
if ($wc_process < 0) { die "Error while creating child process.\n"; }
if ($wc_process == 0) {
	# child
	close ($pipe_write) 
		or die "Error while closing write end of the pipe.\n";
    open (STDIN, '<&', $pipe_read) 
		or die "Error while replacing STDIN with the pipe.\n"; 
	print "Character count:\n";
  	exec ("wc -c") 
	  	or die "Error while running wc.\n";
	print "\n";
} else {
	# parent
	close ($pipe_read) 
		or die "Error while closing read end of the pipe.\n";
	my $buffer = "";
  	while (read ($file, $buffer, 2)) {
   	 	print $pipe_write substr($buffer, 1, 1); 
 	}
}
