
package com.uwetrottmann.shopr.algorithm.model;

import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Arrays;
import java.util.List;

public class Color extends GenericAttribute {

    private static UndirectedGraph<Color.Value, DefaultEdge> sSimilarValues;

    static {
        sSimilarValues = new SimpleGraph<Color.Value, DefaultEdge>(DefaultEdge.class);

        Value[] values = Value.values();
        for (Value value : values) {
            sSimilarValues.addVertex(value);
        }

        /**
         * Stores similar price values in an undirected graph. This is rather
         * subjective, e.g. white is similar to grey. Red is similar to pink,
         * etc.
         */
        sSimilarValues.addEdge(Value.BLUE, Value.PURPLE);
        sSimilarValues.addEdge(Value.BLUE, Value.TURQUOISE);
        sSimilarValues.addEdge(Value.RED, Value.PURPLE);
        sSimilarValues.addEdge(Value.RED, Value.PINK);
        sSimilarValues.addEdge(Value.YELLOW, Value.ORANGE);
        sSimilarValues.addEdge(Value.BLACK, Value.GREY);
        sSimilarValues.addEdge(Value.WHITE, Value.BEIGE);
        sSimilarValues.addEdge(Value.WHITE, Value.GREY);
        sSimilarValues.addEdge(Value.BROWN, Value.BEIGE);
    }

    public static final String ID = "color";

    public enum Value implements AttributeValue {
//        BLUE("Blue"),
//        RED("Red"),
//        PINK("Pink"), // rosa zu Deutsch
//        PURPLE("Purple"),
//        YELLOW("Yellow"),
//        BROWN("Brown"),
//        COLORED("Colored"),
//        MIXED("Mixed"),
//        GREY("Grey"),
//        GREEN("Green"),
//        ORANGE("Orange"),
//        BLACK("Black"),
//        TURQUOISE("Turquoise"),
//        WHITE("White"),
//        BEIGE("Beige");
    	
    	GREY("grey"),
    	GREEN("green"),
    	WHITE("white"),
    	RED("red"),
    	BLACK("black"),
    	BEIGE("beige"),
    	BLUE("blue"),
    	PINK("pink"),
    	OLIV("oliv"),
    	MULTICOLOURED("multicoloured"),
    	TURQUOISE("turquoise"),
    	BROWN("brown"),
    	YELLOW("yellow"),
    	PURPLE("purple"),
    	PETROL("petrol"),
    	ORANGE("orange"),
    	GOLD("gold"),
    	WATERMELON("watermelon"),
    	SILVER("silver"),
    	GREEN_AQUA_MELANGE("green aqua melange"),
    	VANILLA_WHITE("vanilla white"),
    	SKYWAY("skyway"),
    	MAJOR_BROWN("major brown"),
    	ROSE("rose"),
    	OFFWHITE("offwhite"),
    	COCONUT("coconut"),
    	DARK_BLUE("dark blue"),
    	CHILI("chili"),
    	MOTTLED_GREY("mottled grey"),
    	COMBO("combo"),
    	FADED_WHITE("faded white"),
    	CORAL("coral"),
    	RED_BLUE("red blue"),
    	FOX("fox");


        String mDescriptor;

        Value(String name) {
            mDescriptor = name;
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

    public Color() {
        int numValues = Value.values().length;
        mValueWeights = new double[numValues];
        Arrays.fill(mValueWeights, 1.0 / numValues);
    }

    public Color(Value value) {
        setWeights(value);
    }

    /**
     * Tries to match the given string with a {@link Color.Value}.
     */
    public Color(String value) {
    	if ("grey".equals(value)) {
    		setWeights(Color.Value.GREY);
    	}else if ("green".equals(value)) {
    		setWeights(Color.Value.GREEN);
    	}else if ("white".equals(value)) {
    		setWeights(Color.Value.WHITE);
    	}else if ("red".equals(value)) {
    		setWeights(Color.Value.RED);
    	}else if ("black".equals(value)) {
    		setWeights(Color.Value.BLACK);
    	}else if ("beige".equals(value)) {
    		setWeights(Color.Value.BEIGE);
    	}else if ("blue".equals(value)) {
    		setWeights(Color.Value.BLUE);
    	}else if ("pink".equals(value)) {
    		setWeights(Color.Value.PINK);
    	}else if ("oliv".equals(value)) {
    		setWeights(Color.Value.OLIV);
    	}else if ("multicoloured".equals(value)) {
    		setWeights(Color.Value.MULTICOLOURED);
    	}else if ("turquoise".equals(value)) {
    		setWeights(Color.Value.TURQUOISE);
    	}else if ("brown".equals(value)) {
    		setWeights(Color.Value.BROWN);
    	}else if ("yellow".equals(value)) {
    		setWeights(Color.Value.YELLOW);
    	}else if ("purple".equals(value)) {
    		setWeights(Color.Value.PURPLE);
    	}else if ("petrol".equals(value)) {
    		setWeights(Color.Value.PETROL);
    	}else if ("orange".equals(value)) {
    		setWeights(Color.Value.ORANGE);
    	}else if ("gold".equals(value)) {
    		setWeights(Color.Value.GOLD);
    	}else if ("watermelon".equals(value)) {
    		setWeights(Color.Value.WATERMELON);
    	}else if ("silver".equals(value)) {
    		setWeights(Color.Value.SILVER);
    	}else if ("green aqua melange".equals(value)) {
    		setWeights(Color.Value.GREEN_AQUA_MELANGE);
    	}else if ("vanilla white".equals(value)) {
    		setWeights(Color.Value.VANILLA_WHITE);
    	}else if ("skyway".equals(value)) {
    		setWeights(Color.Value.SKYWAY);
    	}else if ("major brown".equals(value)) {
    		setWeights(Color.Value.MAJOR_BROWN);
    	}else if ("rose".equals(value)) {
    		setWeights(Color.Value.ROSE);
    	}else if ("offwhite".equals(value)) {
    		setWeights(Color.Value.OFFWHITE);
    	}else if ("coconut".equals(value)) {
    		setWeights(Color.Value.COCONUT);
    	}else if ("dark blue".equals(value)) {
    		setWeights(Color.Value.DARK_BLUE);
    	}else if ("chili".equals(value)) {
    		setWeights(Color.Value.CHILI);
    	}else if ("mottled grey".equals(value)) {
    		setWeights(Color.Value.MOTTLED_GREY);
    	}else if ("combo".equals(value)) {
    		setWeights(Color.Value.COMBO);
    	}else if ("faded white".equals(value)) {
    		setWeights(Color.Value.FADED_WHITE);
    	}else if ("coral".equals(value)) {
    		setWeights(Color.Value.CORAL);
    	}else if ("red blue".equals(value)) {
    		setWeights(Color.Value.RED_BLUE);
    	}else if ("fox".equals(value)) {
    		setWeights(Color.Value.FOX);
    	}
    	
    	
    	
//        if ("Blau".equals(value)) {
//            setWeights(Color.Value.BLUE);
//        }
//        else if ("Braun".equals(value)) {
//            setWeights(Color.Value.BROWN);
//        }
//        else if ("Bunt".equals(value)) {
//            setWeights(Color.Value.COLORED);
//        }
//        else if ("Gelb".equals(value)) {
//            setWeights(Color.Value.YELLOW);
//        }
//        else if ("Gemischt".equals(value)) {
//            setWeights(Color.Value.MIXED);
//        }
//        else if ("Grau".equals(value)) {
//            setWeights(Color.Value.GREY);
//        }
//        else if ("Grün".equals(value)) {
//            setWeights(Color.Value.GREEN);
//        }
//        else if ("Orange".equals(value)) {
//            setWeights(Color.Value.ORANGE);
//        }
//        else if ("Rosa".equals(value)) {
//            setWeights(Color.Value.PINK);
//        }
//        else if ("Rot".equals(value)) {
//            setWeights(Color.Value.RED);
//        }
//        else if ("Schwarz".equals(value)) {
//            setWeights(Color.Value.BLACK);
//        }
//        else if ("Türkis".equals(value)) {
//            setWeights(Color.Value.TURQUOISE);
//        }
//        else if ("Violett".equals(value)) {
//            setWeights(Color.Value.PURPLE);
//        }
//        else if ("Weiß".equals(value)) {
//            setWeights(Color.Value.WHITE);
//        }
//        else if ("Beige".equals(value)) {
//            setWeights(Color.Value.BEIGE);
//        }
    }

    private void setWeights(Value value) {
        mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);
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
