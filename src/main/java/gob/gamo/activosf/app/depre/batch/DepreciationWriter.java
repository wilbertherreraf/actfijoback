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
package gob.gamo.activosf.app.depre.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gob.gamo.activosf.app.depre.DepreciationProceeds;
import gob.gamo.activosf.app.depre.exception.DepreciationExecutionException;
import gob.gamo.activosf.app.depre.service.DepreciationService;
import gob.gamo.activosf.app.depre.util.ProcessingList;

import java.util.List;

/**
 * Writes depreciation items to the database
 *
 * @author edwin.njeru
 * @version $Id: $Id
 */
public class DepreciationWriter implements ItemWriter<ProcessingList<DepreciationProceeds>> {

    private static final Logger log = LoggerFactory.getLogger(DepreciationWriter.class);

    @Autowired
    @Qualifier("depreciationService")
    private DepreciationService depreciationService;

    /**
     * {@inheritDoc}
     * <p>
     * Process the supplied data element. Will not be called with any null items in normal operation.
     */
    @Override
    public void write(Chunk<? extends ProcessingList<DepreciationProceeds>> depreciationProceedsLists) throws Exception {

        log.info("Writing : {} DepreciationLists to the depreciationRepository", depreciationProceedsLists.size());

        try {
            depreciationProceedsLists.forEach(list -> {
                log.info("Saving to repository : {} depreciation items", list.size());
                depreciationService.saveAllDepreciationProceeds(list);
            });

        } catch (Throwable e) {
            String message =
                String.format("Exception encountered while persisting depreciation items" + "passed in the list to the depreciation writer. These are the items : %s", depreciationProceedsLists);
            throw new DepreciationExecutionException(message, e);
        }

    }

    //@Override
    public void write0(Chunk<? extends ProcessingList<DepreciationProceeds>> chunk) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'write'");
    }
}
