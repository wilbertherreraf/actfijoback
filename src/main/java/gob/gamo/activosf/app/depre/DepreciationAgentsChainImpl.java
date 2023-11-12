package gob.gamo.activosf.app.depre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.agent.AccruedDepreciationAgent;
import gob.gamo.activosf.app.depre.agent.Agent;
import gob.gamo.activosf.app.depre.agent.DepreciationAgent;
import gob.gamo.activosf.app.depre.agent.NetBookValueAgent;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.time.YearMonth;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Object encapsulates a chain of Agents through which a depreciation item is
 * processed as a FixedAsset is passed through
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@RequiredArgsConstructor
@Component("depreciationAgentsChain")
public class DepreciationAgentsChainImpl {

    private final DepreciationAgent depreciationAgent;
    private final AccruedDepreciationAgent accruedDepreciationAgent;
    private final NetBookValueAgent netBookValueAgent;
    private final List<Agent> agents = new CopyOnWriteArrayList<>();

    @PostConstruct
    private void updateAgents() {
        agents.add(depreciationAgent);
        agents.add(accruedDepreciationAgent);
        agents.add(netBookValueAgent);
    }

    void addAgent(Agent agent) {
        agents.add(agent);
    }

    /**
     * <p>
     * execute.
     * </p>
     *
     * @param asset                a
     *                             {@link io.github.fasset.fasset.model.FixedAsset}
     *                             object.
     * @param month                a {@link java.time.YearMonth} object.
     * @param depreciationProceeds a
     *                             {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationProceeds}
     *                             object.
     */
    public void execute(FixedAsset asset, YearMonth month, DepreciationProceeds depreciationProceeds) {
        // invoke all agents
        for (Agent agent : agents) {
            agent.invoke(asset, month, depreciationProceeds);

        }

    }
}
