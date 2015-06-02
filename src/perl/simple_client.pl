#!/usr/local/bin/perl -w
use strict;
use warnings;

use IO::Socket::INET;

my $socket = IO::Socket::INET->new(
    'Proto'    => 'tcp',
    'PeerAddr' => $ARGV[0] || '0.0.0.0',
    'PeerPort' => $ARGV[1] || '12345',
) or die $@;

print "[simple_client][$$] Connected to server using socket " . to_string($socket) . "\n";
$socket->timeout(2);

while (1) {
    $socket->syswrite( "hello, server!\n") or die $!;

    my $rsp = q{};
    $socket->sysread( $rsp, 20 ) or die $!;
    print "[simple_client][$$] Recv:$rsp\n";
    sleep 1;
}

sub to_string
{
    my ($socket) = @_;
    return $socket->sockhost() . ":" . $socket->sockport() . " -> " . $socket->peerhost() . ":" . $socket->peerport();
}
