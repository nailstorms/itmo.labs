#!/usr/bin/perl -T

use strict;
use warnings qw(FATAL all);

$ENV{'PATH'} = '/usr/bin';
$ENV{CDPATH}="";
$ENV{ENV}="";

my $pid = $$;
my $uid = $<; 
my @gr_list = split(/ /, $(); 
my $gid = $gr_list[0];

my $start_time = time();
my $run_time;

my @sys_load;

sub update_info {
	$run_time = time() - $start_time;
	@sys_load = split(/load average: /, qx(uptime));
    	@sys_load = split(', ', $sys_load[1]);
}

print("Server started.\n");

$SIG{HUP} = sub{ print "PID: $pid\n"; };
$SIG{INT} = sub{ print "UID: $uid\n"; };
$SIG{TERM} = sub{ print "GID: $gid\n"; };
$SIG{USR1} = sub{ print "Working time: $run_time\n"; };
$SIG{USR2} = sub{ print "Average server load time:\n \tFor last minute - $sys_load[0];\n \t
	For the last 5 minutes - $sys_load[1];\n \tFor the last 15 minutes - $sys_load[2]\n"; };

while(1) {
	update_info;
	sleep(1);
}