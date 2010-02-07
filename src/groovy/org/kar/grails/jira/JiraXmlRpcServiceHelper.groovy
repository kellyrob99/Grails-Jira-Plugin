package org.kar.grails.jira

import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
 * An abstraction to remove the need to pass in 'hostname' to every method call applying to the same Jira instance.
 * @author Kelly Robinson
 */
class JiraXmlRpcServiceHelper
{
    def hostname
    def JiraXmlRpcService service

    /**
     * Looks for the corresponding method on the JiraXmlRpcService and delegates, prepending the 'hostname' on all calls.
     * @throws NotImplementedException if the wrapped service doesn't have a like named method.
     */
    def methodMissing(String name, args)
    {
        assert hostname && service
        def method = service.metaClass.methods.find {it.name == name}
        if(!method)
        {
            throw new NotImplementedException("The $name method is not implemented for ${this.getClass()}")
        }
        
        def newArgs = [hostname]
        args.each{newArgs << it}
        return method.invoke(service, newArgs as Object[])
    }
}
