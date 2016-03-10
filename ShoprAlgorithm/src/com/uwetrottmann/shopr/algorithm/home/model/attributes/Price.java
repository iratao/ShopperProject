package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

public class Price extends GenericAttribute {

    private static UndirectedGraph<Price.Value, DefaultEdge> sSimilarValues;

    static {
        sSimilarValues = new SimpleGraph<Price.Value, DefaultEdge>(DefaultEdge.class);

        Value[] values = Value.values();
        for (Value value : values) {
            sSimilarValues.addVertex(value);
        }

        /**
         * Stores similar price values in an undirected graph. Price regions
         * right above or below are similar.
         */
        sSimilarValues.addEdge(Value.SUB_25, Value.BETWEEN_25_100);
        sSimilarValues.addEdge(Value.BETWEEN_25_100, Value.BETWEEN_100_750);
        sSimilarValues.addEdge(Value.BETWEEN_100_750, Value.BETWEEN_750_1000);
        sSimilarValues.addEdge(Value.BETWEEN_750_1000, Value.BETWEEN_1000_1500);
        sSimilarValues.addEdge(Value.BETWEEN_1000_1500, Value.BETWEEN_1500_2000);
        sSimilarValues.addEdge(Value.BETWEEN_1500_2000, Value.ABOVE_2000);
    }

    public static final String ID = "price";

    public enum Value implements AttributeValue {
        SUB_25("less than 25 RMB"),
        BETWEEN_25_100("25 to 100 RMB"),
        BETWEEN_100_750("100 to 750 RMB"),
        BETWEEN_750_1000("750 to 1000 RMB"),
        BETWEEN_1000_1500("1000 to 1500 RMB"),
        BETWEEN_1500_2000("1500 to 2000 RMB"),
        ABOVE_2000("2000 RMB or more");

        private String mDescriptor;

        Value(String descriptor) {
            mDescriptor = descriptor;
        }

        @Override
        public String descriptor() {
            return mDescriptor;
        }

        @Override
        public int index() {
            return ordinal();
        }

    }

    public Price() {
        int numValues = Value.values().length;
        mValueWeights = new double[numValues];
        Arrays.fill(mValueWeights, 1.0 / numValues);
    }

    public Price(BigDecimal price) {
        mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);

        // determine price range
        Value value;
        if (price.doubleValue() < 25.0) {
            value = Value.SUB_25;
        } else if (price.doubleValue() < 100.0) {
            value = Value.BETWEEN_25_100;
        } else if (price.doubleValue() < 750.0) {
            value = Value.BETWEEN_100_750;
        } else if (price.doubleValue() < 1000.0) {
            value = Value.BETWEEN_750_1000;
        } else if (price.doubleValue() < 1500.0) {
            value = Value.BETWEEN_1000_1500;
        } else if (price.doubleValue() < 2000.0) {
            value = Value.BETWEEN_1500_2000;
        } else {
            value = Value.ABOVE_2000;
        }

        mValueWeights[value.ordinal()] = 1.0;
        currentValue(value);
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public AttributeValue[] getValueSymbols() {
        return Value.values();
    }

    /**
     * Changes the behavior to also increase the weight of neighboring weights.
     * E.g. a user might like items priced in a little more expensive or a
     * little cheaper based on the price range he liked.
     */
    @Override
    public void likeValue(int indexLiked, double[] weights) {
        Value[] values = Value.values();
        Value valueLiked = values[indexLiked];
        List<Value> similarValues = Graphs.neighborListOf(sSimilarValues, valueLiked);

        // do regular like for liked value
        super.likeValue(indexLiked, weights);

        if (similarValues.isEmpty()) {
            // no similars: done!
            return;
        }

        // now do dampened like on similar values
        double increaseLiked = 1.0 / (weights.length - 1);
        double increaseSimilars = increaseLiked / 2;
        // per similar value increase
        double increaseSimilar = increaseSimilars / similarValues.size();
        // per non-similar and non-liked value decrease
        double decreaseOthers = increaseSimilars / (weights.length - similarValues.size() - 1);

        // actually add and subtract
        for (int i = 0; i < weights.length; i++) {
            if (i == indexLiked) {
                // skip liked value
                continue;
            }
            if (hasValueWithSameIndex(similarValues, i)) {
                // increase similar values
                weights[i] += increaseSimilar;
            } else {
                // decrease other values
                weights[i] -= decreaseOthers;
                // floor at 0.0
                if (weights[i] < 0) {
                    weights[i] = 0.0;
                }
            }
        }

        ensureSumBound(weights);
    }

    /**
     * Checks whether one of the values has the given index.
     */
    private boolean hasValueWithSameIndex(List<Value> values, int index) {
        for (Value value : values) {
            if (value.index() == index) {
                return true;
            }
        }
        return false;
    }

}