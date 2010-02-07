package org.kar.grails.jira

import groovy.net.xmlrpc.XMLRPCServerProxy

/**
 * Service to create a proxy over an XMLRPC connection.
 * @author Kelly Robinson
 */
class RpcProxyService {

    boolean transactional = false
    def static final String XMLRPC = 'rpc/xmlrpc'

    /**
     * Connects to the Jira xmlrpc url at a specific host. The hostname may, but is not required to, already
     * contain the full url. 
     * @param hostname the host to connect to at the url {hostname}/XMLRPC
     */
    def XMLRPCServerProxy createProxy(hostname)
    {
        if(!hostname.endsWith(XMLRPC))
        {
            hostname = hostname.endsWith('/') ? hostname + XMLRPC : hostname + '/' + XMLRPC
        }
        def proxy = new XMLRPCServerProxy(hostname)
        return proxy
    }
}
