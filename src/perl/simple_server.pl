#!/usr/local/bin/perl -w
 
use strict;
use warnings;

use IO::Socket::INET;

my $listenSocket = IO::Socket::INET->new(
    'LocalPort' => $ARGV[0] || '12345',
    'Listen'    => SOMAXCONN,
    'Reuse'     => 1,
    'Proto'     => 'tcp',
) or die $@;
print "[simple_server][$$] Listening on " . to_string($listenSocket, 1) . "\n";

my $conSocket;
while (defined ($conSocket = $listenSocket->accept)) {
    print "[simple_server][$$] Connection accepted from " . to_string($conSocket) . "\n";
    
    my $rsp = q{};
    while ($rsp !~ /bye/i) {
        unless ($conSocket->sysread($rsp, 20)) {
            print STDERR "[simple_server][$$] $@\n";
            last;
        }
        print "[simple_server][$$] Recv: $rsp\n";
        unless ($conSocket->syswrite("You $rsp\n")) {
            print STDERR "[simple_server][$$] $@\n";
            last;
        }
    }
    $conSocket->close();
    print "[simple_server][$$] Connection closed.\n";
}

sub to_string
{
    my ($socket, $listen) = @_;
    my $str = $socket->sockhost() . ":" . $socket->sockport();
    if (!$listen) {
        $str = $str . " -> " . $socket->peerhost() . ":" . $socket->peerport();
    }
    return $str;
}
