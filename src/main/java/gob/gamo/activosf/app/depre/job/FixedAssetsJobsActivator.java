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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import gob.gamo.activosf.app.depre.service.FixedAssetService;

import java.time.LocalDateTime;

/**
 * Used to bootstrap launch of another batch process in the backend
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@Component("fixedAssetsJobsActivator")
public class FixedAssetsJobsActivator {

    private static final Logger log = LoggerFactory.getLogger(FixedAssetsJobsActivator.class);

    /**
     * <p>
     * bootstrap.
     * </p>
     *
     * @param jobLauncher       a
     *                          {@link org.springframework.batch.core.launch.JobLauncher}
     *                          object.
     * @param jobLauncher       a
     *                          {@link org.springframework.batch.core.launch.JobLauncher}
     *                          object.
     * @param job               a {@link org.springframework.batch.core.Job} object.
     * @param fixedAssetService a
     *                          {@link io.github.fasset.fasset.service.FixedAssetService}
     *                          object.
     * @param progressListener  a
     *                          {@link io.github.fasset.fasset.kernel.util.WorkInProgressListener}
     *                          object.
     * @throws io.github.fasset.fasset.kernel.util.BatchJobExecutionException if
     *                                                                        any.
     */
   /*  public void bootstrap(JobLauncher jobLauncher, Job job, FixedAssetService fixedAssetService) throws RuntimeException {

        bootstrap(jobLauncher, job, fixedAssetService);
    }
 */
    /**
     * <p>
     * bootstrap.
     * </p>
     *
     * @param jobLauncher       a
     *                          {@link org.springframework.batch.core.launch.JobLauncher}
     *                          object.
     * @param jobLauncher       a
     *                          {@link org.springframework.batch.core.launch.JobLauncher}
     *                          object.
     * @param job               a {@link org.springframework.batch.core.Job} object.
     * @param fixedAssetService a
     *                          {@link io.github.fasset.fasset.service.FixedAssetService}
     *                          object.
     * @throws io.github.fasset.fasset.kernel.util.BatchJobExecutionException if
     *                                                                        any.
     */
    public void bootstrap(JobLauncher jobLauncher, Job job, FixedAssetService fixedAssetService)
            throws RuntimeException {

        int numberOfAssets = fixedAssetService.getPoll();

        log.info("Another fixedAssets job initiated running for {} individual assets", numberOfAssets);

        bootstrap(
                new JobParametersBuilder().addString("no_of_assets", String.valueOf(numberOfAssets))
                        .addString("starting_time", LocalDateTime.now().toString()).toJobParameters(),
                jobLauncher, job,
                fixedAssetService);
    }

    private void bootstrap0(JobParameters jobParameters, JobLauncher jobLauncher, Job job,
            FixedAssetService fixedAssetService)//, WorkInProgressListener progressListener)
            throws RuntimeException {

        int numberOfAssets = fixedAssetService.getPoll();
        LocalDateTime startingTime = LocalDateTime.now();

        JobExecution jobExecution;

        log.info("Depreciation has begun with {} items at time: {}", numberOfAssets, startingTime);

        try {
            jobExecution = jobLauncher.run(job, jobParameters);
        } catch (Throwable e) {

            String message = String.format(
                    "Exception encountered %s caused by %s,while launching job" + "nomenclature %s at time %s",
                    e.getMessage(), e.getCause(), job.getName(),
                    jobParameters.getString("starting_time"));

            throw new RuntimeException(message, e);

        }

 /*        if (progressListener != null) {
            log.debug("Checking is job execution is running...");
            if (!jobExecution.getStatus().isRunning()) {
                progressListener.isWorkStillInProgress(false);
                log.debug("Job execution has stopped, updating listener...");
            }
        } */

    }

    /**
     * <p>
     * bootstrap.
     * </p>
     *
     * @param jobParameters     a
     *                          {@link org.springframework.batch.core.JobParameters}
     *                          object.
     * @param jobLauncher       a
     *                          {@link org.springframework.batch.core.launch.JobLauncher}
     *                          object.
     * @param jobParameters     a
     *                          {@link org.springframework.batch.core.JobParameters}
     *                          object.
     * @param jobLauncher       a
     *                          {@link org.springframework.batch.core.launch.JobLauncher}
     *                          object.
     * @param job               a {@link org.springframework.batch.core.Job} object.
     * @param fixedAssetService a
     *                          {@link io.github.fasset.fasset.service.FixedAssetService}
     *                          object.
     */
    public void bootstrap(JobParameters jobParameters, JobLauncher jobLauncher, Job job,
            FixedAssetService fixedAssetService) {
        try {
            bootstrap(jobParameters, jobLauncher, job, fixedAssetService);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
