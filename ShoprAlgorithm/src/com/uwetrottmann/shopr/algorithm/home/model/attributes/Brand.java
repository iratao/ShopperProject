package com.uwetrottmann.shopr.algorithm.home.model.attributes;

import java.util.Arrays;

import com.uwetrottmann.shopr.algorithm.model.GenericAttribute;
import com.uwetrottmann.shopr.algorithm.model.Attributes.AttributeValue;

public class Brand extends GenericAttribute{
	public static final String ID = "brand";

    public enum Value implements AttributeValue {
//    	TURRI("Turri"),
//    	Cornelio("Cornelio Cappellini"),
//    	Galimberti("Galimberti Lino"),
//    	JinBang("金榜"),
//    	HongYang("鸿扬"),
//    	BRETZ("bretz"),
//    	GOBBO("Gobbo Salotti"),
//    	YUANHENGLI("元亨利"),
//    	BAKER("Baker"),
//    	DENGYUANGUOJI("灯元国际"),
//    	FULANDISI("富兰蒂斯"),
//    	ZUOYOUBOLUOKE("左右波洛克"),
//    	MIELE("Miele"),
//    	YITUOFAN("意托梵"),
//    	LUOMANDIKA("罗曼迪卡"),
//    	LUYIFAJIA("路易法家"),
//    	BAWANGZHONGBIAO("霸王钟表"),
//    	SHIDAIXIANGCHUAN("世代相传"),
//    	YIDISENG("意迪森"),
//    	QUEZHILIN("雀之林"),
//    	FEITONG("非同"),
//    	DUSHA("杜莎"),
//    	CAV("CAV"),
//    	PULUODA("普罗达"),
//    	BEIMEIZHIJIA("北美之家"),
//    	HUASHIFU("华师傅"),
//    	FASHILAN("法诗兰"),
//    	HENYOU("恒友"),
//    	TAINUO("泰诺"),
//    	MEIJUN("美郡"),
//    	OUKEWEIER("奥克维尔"),
//    	ROLF("Rolf-benz"),
//    	BOSENGDAGUAN("柏森大观"),
//    	LVZHIDAO("绿之岛"),
//    	BAIQIANG("百强哈利木屋"),
//    	("美家华庭"),
//    	("名匠木坊"),
//    	("皇家爱菲"),
//    	("标致"),
//    	("柏森"),
//    	("宝恩"),
//    	("英伦华庄·简欧"),
//    	("瑞尔"),
//    	("雅格斯丹"),
//    	("东方百盛"),
//    	("时代腾迪"),
//    	("左右"),
//    	("金富丽"),
//    	("赖氏"),
//    	("阳光夏威夷"),
//    	("英伦华庄"),
//    	("卧王"),
//    	("艺家百年"),
//    	("威廉小镇"),
//    	("M&D"),
//    	("楷模"),
//    	("华日"),
//    	("奥立克"),
//    	("康耐登"),
//    	("亚美特"),
//    	("黎曼世家"),
//    	("红苹果"),
//    	("塞纳风情"),
//    	("晓月"),
//    	("奥爵"),
//    	("皇朝板式"),
//    	("皇佳彼德"),
//    	("漫趣"),
//    	("斯可馨"),
//    	("大鹏米克"),
//    	("强力"),
//    	("飞美"),
//    	("大自然"),
//    	("华意空间"),
//    	("爱依瑞斯"),
//    	("美度佳"),
//    	("ARES"),
//    	("爱丽舍宫"),
//    	("意风"),
//    	("小狗第一"),
//    	("欧瑞家具"),
//    	("TCL"),
//    	("华狮龙"),
//    	("柏森乌金木语"),
//    	("弘门"),
//    	("英耐特丝"),
//    	("皇朝家私"),
//    	("阳霸"),
//    	("Urban Archaeology"),
//    	("KD壁柜"),
//    	("春上"),
//    	("豪典工坊"),
//    	("蒙泰尼"),
//    	("雷士照明"),
//    	("马斯登"),
//    	("Lattoflex"),
//    	("御玺"),
//    	("慕月"),
//    	("又日新"),
//    	("POLTRONA FRAU"),
//    	("荣麟"),
//    	("百强松堡王国"),
//    	("史丹利"),
//    	("盛宴"),
//    	("万寿轩"),
//    	("居然之家"),
//    	("柚尊"),
//    	("Porada"),
//    	("博诺威"),
//    	("金海燕"),
//    	("皇家现代"),
//    	("南洋森林"),
//    	("interlubke"),
//    	("图柏年"),
//    	("圣蒂斯堡"),
//    	("写意空间"),
//    	("法兰芬蒂"),
//    	("generic"),
//    	("sub-zero"),
//    	("天上明珠"),
//    	("海尔"),
//    	("Committed Photography"),
//    	("Generic"),
    	YALANDISI("雅兰迪斯"),
    	VENJAKOB("Venjakob");

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

    @Override
    public String id() {
        return ID;
    }

    @Override
    public Value[] getValueSymbols() {
        return Value.values();
    }
}
