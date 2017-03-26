#!groovy
import jenkins.model.*
import hudson.security.*

/**
 * Security configuration script
 */

def instance = Jenkins.getInstance()

if ( ! instance.isUseSecurity()) {

    // Users declaration
    def hudsonRealm = new HudsonPrivateSecurityRealm(false)
    {% for account in jenkins_accounts %}
        hudsonRealm.createAccount('{{ account.user }}', '{{ account.password }}')
    {% endfor %}

    // Strategy declaration
    def strategy = new FullControlOnceLoggedInAuthorizationStrategy()


    instance.setSecurityRealm(hudsonRealm)
    instance.setAuthorizationStrategy(strategy)
    instance.save()
}