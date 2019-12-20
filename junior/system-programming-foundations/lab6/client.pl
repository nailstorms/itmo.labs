#!/usr/bin/perl 
use strict;
use warnings;
use utf8;

use IO::Socket;
die "Usage: $0 server_name [directories...]\n" if @ARGV < 2;

my $host = shift;
my $port = 8344;

my $server = new IO::Socket::INET(
    Proto => 'tcp',
    PeerAddr => $host,
    PeerPort => $port,
) or die "Server name is incorrect. Please try again.";

die "Failed to connect: $!\n" unless $server;

while (my $arg = shift) {
    print $server $arg, " ";
}

print $server "\n";
while (my $answer = <$server>){
    print $answer;
}
close $server;
