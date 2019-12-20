#!/home/s243867/lib/perl/bin/perl
use utf8;
use lib '/home/s243867/lib/perl5/i86pc-solaris';
use lib '/home/s243867/lib/perl5';
use IO::Socket::INET;
use AnyEvent;
use AnyEvent::Util;
$AnyEvent::Util::MAX_FORKS = 32;

$|++;

my $server = IO::Socket::INET->new(
    'Proto'     => 'tcp',
    'LocalAddr' => 'localhost',
    'LocalPort' => 8344,
    'Listen'    => SOMAXCONN,
    'Reuse'     => 1,
) or die "Server setup error: $!\n";

my $cond_var = AnyEvent->condvar;

my $w; $w = AnyEvent->io(
        fh   => \*{ $server }, 
        poll => 'r', 
        cb   => sub {
                   $cond_var->begin; 
                   fork_call \&return_dir_list, 
                        $server->accept,
                        sub {
		        	        my ($ans) = @_;
                        }
                }
);

print("Server started.\n");

$cond_var->recv;

sub return_dir_list {
    my ($client) =  @_;
    my $input = <$client>;
    my @req_directories = split(" ", $input);
    my $resp_string = "";
    foreach my $dir (@req_directories){
        my $flag = opendir(D, $dir);
        if (!$flag){
            $resp_string = $answer."$dir: $!\n";
            next;
        }
        $resp_string = $resp_string."$dir contents: \n";
        my @dir_contents = readdir(D);
        closedir(D);
        foreach my $f (@dir_contents){
            $resp_string = $resp_string."\t$f\n";
        }
    }
    print $client "$resp_string\n";
    $cond_var->end;
    return $resp_string;
}
