/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package gob.gamo.activosf.app.depre.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listens for the start and completion for start and end of the depreciation batch after which it calls the appropriate job which is next in line
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Component("depreciationJobListener")
public class DepreciationJobListener implements JobExecutionListener {


    @Autowired
    private MonthlyDepreciationJobProxy monthlyDepreciationJobProxy;

    /**
     * {@inheritDoc}
     * <p>
     * Callback before a job executes.
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {

        //TODO poll job parameters and update the depreciationJobExecutionRepository
    }

    /**
     * {@inheritDoc}
     * <p>
     * Callback after completion of a job. Called after both both successful and failed executions. To perform logic on a particular status, use "if (jobExecution.getStatus() == BatchStatus.X)".
     */
    @Override
    public void afterJob(JobExecution jobExecution) {

        //TODO poll paramters and update the status of the depreciationJobExecutionRepository

        monthlyDepreciationJobProxy.initializeMonthlyDepreciationReporting();
    }

    /**
     * <p>Setter for the field <code>monthlyDepreciationJobProxy</code>.</p>
     *
     * @param monthlyDepreciationJobProxy a {@link io.github.fasset.fasset.kernel.batch.depreciation.report.MonthlyDepreciationJobProxy} object.
     * @return a {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationJobListener} object.
     */
    public DepreciationJobListener setMonthlyDepreciationJobProxy(MonthlyDepreciationJobProxy monthlyDepreciationJobProxy) {
        this.monthlyDepreciationJobProxy = monthlyDepreciationJobProxy;
        return this;
    }
}
