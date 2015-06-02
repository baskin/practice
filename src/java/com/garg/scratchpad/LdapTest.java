package com.garg.scratchpad;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapTest 
{
	public static void main(String[] args) 
	{
		Hashtable env = new Hashtable();
		
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://Invest1.hyd:1389");
//		env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
//		env.put(Context.SECURITY_CREDENTIALS, "password");
		

		try {
			DirContext context = new InitialDirContext(env);
//			context.create
		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}
}
