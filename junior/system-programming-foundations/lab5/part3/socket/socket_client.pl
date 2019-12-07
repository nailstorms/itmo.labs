#!/usr/bin/perl -T
use strict;
use warnings qw(FATAL all);
use IO::Socket::UNIX;

my $SOCKET_PATH = '/tmp/lab5-socket';

my $client = IO::Socket::UNIX->new(
        Type => SOCK_STREAM(),
        Peer => $SOCKET_PATH,
  ) or die "Cannot connect to server\n";

while(<$client>) {
	print("$_");
}

close($client);
