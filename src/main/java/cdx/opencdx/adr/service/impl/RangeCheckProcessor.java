package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.service.IKMInterface;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RangeCheckProcessor implements OpenCDXANFProcessor {

    private final IKMInterface ikmInterface;

    public RangeCheckProcessor(IKMInterface ikmInterface) {
        this.ikmInterface = ikmInterface;
    }

    /**
     * Processes an AnfStatement by storing its information in the database.
     *
     * @param anfStatement The AnfStatement to process.
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {
        if(anfStatement.getPerformanceCircumstance() != null) {
            anfStatement.getPerformanceCircumstance().setResult( ikmInterface.syncConstraintRanges(anfStatement.getTopic().getConceptId(), anfStatement.getPerformanceCircumstance().getResult()));
        } else if (anfStatement.getRequestCircumstance() != null) {
            anfStatement.getRequestCircumstance().setRequestedResult(ikmInterface.syncConstraintRanges(anfStatement.getTopic().getConceptId(), anfStatement.getRequestCircumstance().getRequestedResult()));
        }
    }
}
