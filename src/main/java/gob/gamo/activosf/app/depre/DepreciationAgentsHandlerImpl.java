package gob.gamo.activosf.app.depre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.agent.Agent;
import gob.gamo.activosf.app.depre.model.FixedAsset;

import java.time.YearMonth;

/**
 * This object encapsulates the AgentsDepreciationChain and manages the addition
 * of an Agent to the chain and sends a depreciation request to the chain for
 * processing
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Component("depreciationAgentsHandler")
public class DepreciationAgentsHandlerImpl implements DepreciationAgentsHandler {

    private DepreciationAgentsChainImpl depreciationAgentsChain;

    /**
     * <p>
     * setDepreciationAgentsChain
     * </p>
     * This method exists to assist in setting up the depreciationAgentsChain
     * without using constructor injection. This is because doing so would lead to
     * circular dependency problems making for an
     * unstable startup. The spring container tends to create this dependencies and
     * related dependencies at the startup at the same time. Since this object
     * depends on the DepreciationChain which
     * depends on the DepreciationExecutor which transitively depends on the
     * DepreciationChainHandler, it would break the application start up.
     *
     * @param depreciationAgentsChain This is the object containing a chain of
     *                                executable Agents for the depreciation process
     */
    @Autowired
    public void setDepreciationAgentsChain(final DepreciationAgentsChainImpl depreciationAgentsChain) {
        this.depreciationAgentsChain = depreciationAgentsChain;
    }

    public void setDepreciationAgent(Agent agent) {
        depreciationAgentsChain.addAgent(agent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendRequest(FixedAsset asset, YearMonth month, DepreciationProceeds depreciationProceeds) {
        depreciationAgentsChain.execute(asset, month, depreciationProceeds);
    }
}
