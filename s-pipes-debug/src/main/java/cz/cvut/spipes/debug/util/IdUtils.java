package cz.cvut.spipes.debug.util;

import static cz.cvut.spipes.Vocabulary.s_c_pipeline_comparison;
import static cz.cvut.spipes.Vocabulary.s_c_transformation;

public class IdUtils {
    public static String getExecutionIdFromIri(String iri) {
        int startIndex = iri.lastIndexOf("/") + 1;
        int endIndex = iri.length();
        return iri.substring(startIndex, endIndex);
    }

    public static String getTransformationIriFromId(String executionId){
        return s_c_transformation + "/" + executionId;
    }

    public static String generatePipelineComparisonIri(){
        return s_c_pipeline_comparison + "/" + generateId();
    }

    private static String generateId() {
        long millis = System.currentTimeMillis();
        return Long.toHexString(millis);
    }
}
