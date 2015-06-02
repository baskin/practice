
use Net::LDAP;

sub to_string
{
    my ($ldap) = @_;
    return $ldap->sockhost() . ":" . $ldap->sockport() . " -> " . $ldap->peerhost() . ":" . $ldap->peerport();
}

{
# my $ldap = Net::LDAP->new("localhost", port => 12345, timeout => 2);
my $ldap = Net::LDAP->new("sxvm2.nyc", port => 389, timeout => 2);

use Data::Dumper;
print Dumper($ldap) . "\n";

my $sock = $ldap->socket()->sockport();

print "\nAfter c'tor: " . to_string($ldap->socket()) . "\n";
my $cmd = "netstat | grep $sock";
system($cmd);

$ldap->bind();
print "\nAfter bind: " . to_string($ldap->socket()) . "\n";
system($cmd);

$ldap->unbind();
print "\nAfter unbind: " . to_string($ldap->socket()) . "\n";
system($cmd);

$ldap->disconnect();
print "\nAfter disconnect:\n"; # . to_string($ldap->socket()) . "\n";
system($cmd);
}

print "\nDone\n";
system($cmd);




