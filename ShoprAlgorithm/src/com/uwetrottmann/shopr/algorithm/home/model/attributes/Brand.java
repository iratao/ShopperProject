package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.util.Arrays;

import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;
import com.uwetrottmann.shopr.algorithm.model.ClothingType.Value;

public class Brand extends GenericAttribute{
	public static final String ID = "brand";

    public enum Value implements AttributeValue {
    	A1("漫趣"),
    	A2("bretz"),
    	A3("雀之林"),
    	A4("亚美特"),
    	A5("瑞尔"),
    	A6("时代腾迪"),
    	A7("世代相传"),
    	A8("路易法家"),
    	A9("艺家百年"),
    	A10("法诗兰"),
    	A11("欧瑞家具"),
    	A12("爱丽舍宫"),
    	A13("罗曼迪卡"),
    	A14("皇朝板式"),
    	A15("皇朝家私"),
    	A16("爱依瑞斯"),
    	A17("强力"),
    	A18("非同"),
    	A19("Lattoflex"),
    	A20("华师傅"),
    	A21("飞美"),
    	A22("华意空间"),
    	A23("御玺"),
    	A24("塞纳风情"),
    	A25("名匠木坊"),
    	A26("泰诺"),
    	A27("普罗达"),
    	A28("Turri"),
    	A29("黎曼世家"),
    	A30("恒友"),
    	A31("慕月"),
    	A32("晓月"),
    	A33("百强哈利木屋"),
    	A34("北美之家"),
    	A35("奥克维尔"),
    	A36("又日新"),
    	A37("华日"),
    	A38("豪典工坊"),
    	A39("绿之岛"),
    	A40("POLTRONA FRAU"),
    	A41("美郡"),
    	A42("意风"),
    	A43("威廉小镇"),
    	A44("富兰蒂斯"),
    	A45("金富丽"),
    	A46("红苹果"),
    	A47("赖氏"),
    	A48("荣麟"),
    	A49("阳光夏威夷"),
    	A50("英伦华庄·简欧"),
    	A51("英伦华庄"),
    	A52("杜莎"),
    	A53("标致"),
    	A54("奥爵"),
    	A55("柏森"),
    	A56("奥立克"),
    	A57("皇佳彼德"),
    	A58("左右波洛克"),
    	A59("柚尊"),
    	A60("博诺威"),
    	A61("interlubke"),
    	A62("Cornelio Cappellini"),
    	A63("Baker"),
    	A64("万寿轩"),
    	A65("柏森乌金木语"),
    	A66("楷模"),
    	A67("恒泰"),
    	A68("意托梵"),
    	A69("hulsta"),
    	A70("百强松堡王国"),
    	A71("南洋森林"),
    	A72("Gobbo Salotti"),
    	A73("东方百盛"),
    	A74("柏森大观"),
    	A75("居然之家"),
    	A76("KD壁柜"),
    	A77("圣蒂斯堡"),
    	A78("金海燕"),
    	A79("盛宴"),
    	A80("蒙泰尼"),
    	A81("雅格斯丹"),
    	A82("美家华庭"),
    	A83("康耐登"),
    	A84("元亨利"),
    	A85("史丹利"),
    	A86("斯可馨"),
    	A87("小狗第一"),
    	A88("Porada"),
    	A89("Rolf-benz"),
    	A90("卧王"),
    	A91("美瑞兰"),
    	A92("图柏年"),
    	A93("宝恩"),
    	A94("皇家现代"),
    	A95("左右"),
    	A96("法兰芬蒂"),
    	A97("鸿扬"),
    	A98("皇家爱菲"),
    	A99("写意空间"),
    	A100("意迪森"),
    	A101("大鹏米克"),
    	A102("德尔地板"),
    	A103("春上"),
    	A104("北欧风情"),
    	A105("缘和"),
    	A106("Galimberti Lino"),
    	A107("Venjakob"),
    	A108("M&D"),
    	A109("Marvin"),
    	A110("Kettal"),
    	A111("雅兰迪斯"),
    	A112("美度佳"),
    	A113("爱格地板"),
    	A114("正山石材"),
        Other("Ohter");

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

    public Brand() {
        int numValues = Value.values().length;
        mValueWeights = new double[numValues];
        Arrays.fill(mValueWeights, 1.0 / numValues);
    }

    public Brand(Value value) {
        mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);
        mValueWeights[value.ordinal()] = 1.0;
        currentValue(value);
    }
    
    public Brand(String str) {
    	for (Value value : Value.values()) {
    	    if(value.descriptor().equals(str)){
    	    	mValueWeights = new double[Value.values().length];
    	        Arrays.fill(mValueWeights, 0.0);
    	        mValueWeights[value.ordinal()] = 1.0;
    	        currentValue(value);
    	        return;
    	    } 
    	}
    	mValueWeights = new double[Value.values().length];
        Arrays.fill(mValueWeights, 0.0);
        mValueWeights[Value.Other.ordinal()] = 1.0;
        currentValue(Value.Other);
    	System.out.println("***********************Value not found!!!!!!!!!!!!!*************");
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public Value[] getValueSymbols() {
        return Value.values();
    }
}
