package gob.gamo.activosf.app.depre.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.job.DepreciationJobListener;
import gob.gamo.activosf.app.depre.model.FixedAsset;
import gob.gamo.activosf.app.depre.util.ProcessingList;
import gob.gamo.activosf.app.depre.util.ProcessingListImpl;
import lombok.RequiredArgsConstructor;

/**
 * Configuration for Depreciation batch process
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
@RequiredArgsConstructor
@Configuration
public class DepreciationJobConfig {

    private final JobBuilder jobBuilderFactory;
    private final StepBuilder stepBuilderFactory;
    private final ItemReader<FixedAsset> fixedAssetItemReader;

    /**
     * <p>
     * depreciationJob.
     * </p>
     *
     * @param depreciationJobListener a
     *                                {@link io.github.fasset.fasset.kernel.batch.depreciation.DepreciationJobListener}
     *                                object.
     * @return a {@link org.springframework.batch.core.Job} object.
     */
    @Bean("depreciationJob")
    public Job depreciationJob(DepreciationJobListener depreciationJobListener) {

        return jobBuilderFactory.preventRestart().incrementer(new RunIdIncrementer())
                .listener(depreciationJobListener).flow(depreciationStep1()).end().build();
    }

    /**
     * <p>
     * depreciationProcessor.
     * </p>
     *
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.batch.DepreciationProcessor}
     *         object.
     */
    @Bean
    public DepreciationProcessor depreciationProcessor() {

        return new DepreciationProcessor(new ProcessingListImpl<>());
    }

    /**
     * <p>
     * depreciationWriter.
     * </p>
     *
     * @return a
     *         {@link io.github.fasset.fasset.kernel.batch.depreciation.batch.DepreciationWriter}
     *         object.
     */
    @Bean
    public DepreciationWriter depreciationWriter() {
        return new DepreciationWriter();
    }

    /**
     * <p>
     * depreciationStep1.
     * </p>
     *
     * @return a {@link org.springframework.batch.core.Step} object.
     */
    @Bean
    public Step depreciationStep1() {
        return stepBuilderFactory.<FixedAsset, ProcessingList<DepreciationProceeds>>chunk(100)
                .reader(fixedAssetItemReader)
                .processor(depreciationProcessor())
                .writer(depreciationWriter())
                .build();
    }

}
