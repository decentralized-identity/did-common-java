// This class has been generated automatically
// from an SABNF grammar by Java APG, Verision 1.1.0.
// Copyright (c) 2021 Lowell D. Thomas, all rights reserved.
// Licensed under the 2-Clause BSD License.

package foundation.identity.did.parser;

import apg.Grammar;
import java.io.PrintStream;

public class DIDGrammar extends Grammar{

    // public API
    public static Grammar getInstance(){
        if(factoryInstance == null){
            factoryInstance = new DIDGrammar(getRules(), getUdts(), getOpcodes());
        }
        return factoryInstance;
    }

    // rule name enum
    public static int ruleCount = 19;
    public enum RuleNames{
        ALPHA("ALPHA", 16, 92, 3),
        DID("did", 1, 11, 5),
        DID_URL("did-url", 0, 0, 11),
        DIGIT("DIGIT", 17, 95, 1),
        FRAGMENT("fragment", 11, 57, 5),
        GEN_DELIMS("gen-delims", 14, 72, 8),
        HEXDIG("HEXDIG", 18, 96, 8),
        IDCHAR("idchar", 5, 29, 7),
        METHOD_CHAR("method-char", 3, 18, 3),
        METHOD_NAME("method-name", 2, 16, 2),
        METHOD_SPECIFIC_ID("method-specific-id", 4, 21, 8),
        PATH_ABEMPTY("path-abempty", 7, 40, 4),
        PCHAR("pchar", 9, 46, 6),
        PCT_ENCODED("pct-encoded", 6, 36, 4),
        QUERY("query", 10, 52, 5),
        RESERVED("reserved", 13, 69, 3),
        SEGMENT("segment", 8, 44, 2),
        SUB_DELIMS("sub-delims", 15, 80, 12),
        UNRESERVED("unreserved", 12, 62, 7);
        private String name;
        private int id;
        private int offset;
        private int count;
        RuleNames(String string, int id, int offset, int count){
            this.name = string;
            this.id = id;
            this.offset = offset;
            this.count = count;
        }
        public  String ruleName(){return name;}
        public  int    ruleID(){return id;}
        private int    opcodeOffset(){return offset;}
        private int    opcodeCount(){return count;}
    }

    // UDT name enum
    public static int udtCount = 0;
    public enum UdtNames{
    }

    // private
    private static DIDGrammar factoryInstance = null;
    private DIDGrammar(Rule[] rules, Udt[] udts, Opcode[] opcodes){
        super(rules, udts, opcodes);
    }

    private static Rule[] getRules(){
    	Rule[] rules = new Rule[19];
        for(RuleNames r : RuleNames.values()){
            rules[r.ruleID()] = getRule(r.ruleID(), r.ruleName(), r.opcodeOffset(), r.opcodeCount());
        }
        return rules;
    }

    private static Udt[] getUdts(){
    	Udt[] udts = new Udt[0];
        return udts;
    }

        // opcodes
    private static Opcode[] getOpcodes(){
    	Opcode[] op = new Opcode[104];
    	addOpcodes00(op);
        return op;
    }

    private static void addOpcodes00(Opcode[] op){
        {int[] a = {1,2,3,7}; op[0] = getOpcodeCat(a);}
        op[1] = getOpcodeRnm(1, 11); // did
        op[2] = getOpcodeRnm(7, 40); // path-abempty
        op[3] = getOpcodeRep((char)0, (char)1, 4);
        {int[] a = {5,6}; op[4] = getOpcodeCat(a);}
        {char[] a = {63}; op[5] = getOpcodeTls(a);}
        op[6] = getOpcodeRnm(10, 52); // query
        op[7] = getOpcodeRep((char)0, (char)1, 8);
        {int[] a = {9,10}; op[8] = getOpcodeCat(a);}
        {char[] a = {35}; op[9] = getOpcodeTls(a);}
        op[10] = getOpcodeRnm(11, 57); // fragment
        {int[] a = {12,13,14,15}; op[11] = getOpcodeCat(a);}
        {char[] a = {100,105,100,58}; op[12] = getOpcodeTls(a);}
        op[13] = getOpcodeRnm(2, 16); // method-name
        {char[] a = {58}; op[14] = getOpcodeTls(a);}
        op[15] = getOpcodeRnm(4, 21); // method-specific-id
        op[16] = getOpcodeRep((char)1, Character.MAX_VALUE, 17);
        op[17] = getOpcodeRnm(3, 18); // method-char
        {int[] a = {19,20}; op[18] = getOpcodeAlt(a);}
        op[19] = getOpcodeTrg((char)97, (char)122);
        op[20] = getOpcodeRnm(17, 95); // DIGIT
        {int[] a = {22,27}; op[21] = getOpcodeCat(a);}
        op[22] = getOpcodeRep((char)0, Character.MAX_VALUE, 23);
        {int[] a = {24,26}; op[23] = getOpcodeCat(a);}
        op[24] = getOpcodeRep((char)0, Character.MAX_VALUE, 25);
        op[25] = getOpcodeRnm(5, 29); // idchar
        {char[] a = {58}; op[26] = getOpcodeTls(a);}
        op[27] = getOpcodeRep((char)1, Character.MAX_VALUE, 28);
        op[28] = getOpcodeRnm(5, 29); // idchar
        {int[] a = {30,31,32,33,34,35}; op[29] = getOpcodeAlt(a);}
        op[30] = getOpcodeRnm(16, 92); // ALPHA
        op[31] = getOpcodeRnm(17, 95); // DIGIT
        {char[] a = {46}; op[32] = getOpcodeTls(a);}
        {char[] a = {45}; op[33] = getOpcodeTls(a);}
        {char[] a = {95}; op[34] = getOpcodeTls(a);}
        op[35] = getOpcodeRnm(6, 36); // pct-encoded
        {int[] a = {37,38,39}; op[36] = getOpcodeCat(a);}
        {char[] a = {37}; op[37] = getOpcodeTls(a);}
        op[38] = getOpcodeRnm(18, 96); // HEXDIG
        op[39] = getOpcodeRnm(18, 96); // HEXDIG
        op[40] = getOpcodeRep((char)0, Character.MAX_VALUE, 41);
        {int[] a = {42,43}; op[41] = getOpcodeCat(a);}
        {char[] a = {47}; op[42] = getOpcodeTls(a);}
        op[43] = getOpcodeRnm(8, 44); // segment
        op[44] = getOpcodeRep((char)0, Character.MAX_VALUE, 45);
        op[45] = getOpcodeRnm(9, 46); // pchar
        {int[] a = {47,48,49,50,51}; op[46] = getOpcodeAlt(a);}
        op[47] = getOpcodeRnm(12, 62); // unreserved
        op[48] = getOpcodeRnm(6, 36); // pct-encoded
        op[49] = getOpcodeRnm(15, 80); // sub-delims
        {char[] a = {58}; op[50] = getOpcodeTls(a);}
        {char[] a = {64}; op[51] = getOpcodeTls(a);}
        op[52] = getOpcodeRep((char)0, Character.MAX_VALUE, 53);
        {int[] a = {54,55,56}; op[53] = getOpcodeAlt(a);}
        op[54] = getOpcodeRnm(9, 46); // pchar
        {char[] a = {47}; op[55] = getOpcodeTls(a);}
        {char[] a = {63}; op[56] = getOpcodeTls(a);}
        op[57] = getOpcodeRep((char)0, Character.MAX_VALUE, 58);
        {int[] a = {59,60,61}; op[58] = getOpcodeAlt(a);}
        op[59] = getOpcodeRnm(9, 46); // pchar
        {char[] a = {47}; op[60] = getOpcodeTls(a);}
        {char[] a = {63}; op[61] = getOpcodeTls(a);}
        {int[] a = {63,64,65,66,67,68}; op[62] = getOpcodeAlt(a);}
        op[63] = getOpcodeRnm(16, 92); // ALPHA
        op[64] = getOpcodeRnm(17, 95); // DIGIT
        {char[] a = {45}; op[65] = getOpcodeTls(a);}
        {char[] a = {46}; op[66] = getOpcodeTls(a);}
        {char[] a = {95}; op[67] = getOpcodeTls(a);}
        {char[] a = {126}; op[68] = getOpcodeTls(a);}
        {int[] a = {70,71}; op[69] = getOpcodeAlt(a);}
        op[70] = getOpcodeRnm(14, 72); // gen-delims
        op[71] = getOpcodeRnm(15, 80); // sub-delims
        {int[] a = {73,74,75,76,77,78,79}; op[72] = getOpcodeAlt(a);}
        {char[] a = {58}; op[73] = getOpcodeTls(a);}
        {char[] a = {47}; op[74] = getOpcodeTls(a);}
        {char[] a = {63}; op[75] = getOpcodeTls(a);}
        {char[] a = {35}; op[76] = getOpcodeTls(a);}
        {char[] a = {91}; op[77] = getOpcodeTls(a);}
        {char[] a = {93}; op[78] = getOpcodeTls(a);}
        {char[] a = {64}; op[79] = getOpcodeTls(a);}
        {int[] a = {81,82,83,84,85,86,87,88,89,90,91}; op[80] = getOpcodeAlt(a);}
        {char[] a = {33}; op[81] = getOpcodeTls(a);}
        {char[] a = {36}; op[82] = getOpcodeTls(a);}
        {char[] a = {38}; op[83] = getOpcodeTls(a);}
        {char[] a = {39}; op[84] = getOpcodeTls(a);}
        {char[] a = {40}; op[85] = getOpcodeTls(a);}
        {char[] a = {41}; op[86] = getOpcodeTls(a);}
        {char[] a = {42}; op[87] = getOpcodeTls(a);}
        {char[] a = {43}; op[88] = getOpcodeTls(a);}
        {char[] a = {44}; op[89] = getOpcodeTls(a);}
        {char[] a = {59}; op[90] = getOpcodeTls(a);}
        {char[] a = {61}; op[91] = getOpcodeTls(a);}
        {int[] a = {93,94}; op[92] = getOpcodeAlt(a);}
        op[93] = getOpcodeTrg((char)65, (char)90);
        op[94] = getOpcodeTrg((char)97, (char)122);
        op[95] = getOpcodeTrg((char)48, (char)57);
        {int[] a = {97,98,99,100,101,102,103}; op[96] = getOpcodeAlt(a);}
        op[97] = getOpcodeRnm(17, 95); // DIGIT
        {char[] a = {65}; op[98] = getOpcodeTls(a);}
        {char[] a = {66}; op[99] = getOpcodeTls(a);}
        {char[] a = {67}; op[100] = getOpcodeTls(a);}
        {char[] a = {68}; op[101] = getOpcodeTls(a);}
        {char[] a = {69}; op[102] = getOpcodeTls(a);}
        {char[] a = {70}; op[103] = getOpcodeTls(a);}
    }

    public static void display(PrintStream out){
        out.println(";");
        out.println("; foundation.identity.did.apgparser.DIDGrammar");
        out.println(";");
        out.println("did-url = did path-abempty [ \"?\" query ] [ \"#\" fragment ]");
        out.println("");
        out.println("did                = \"did:\" method-name \":\" method-specific-id");
        out.println("method-name        = 1*method-char");
        out.println("method-char        = %x61-7A / DIGIT");
        out.println("method-specific-id = *( *idchar \":\" ) 1*idchar");
        out.println("idchar             = ALPHA / DIGIT / \".\" / \"-\" / \"_\" / pct-encoded");
        out.println("pct-encoded        = \"%\" HEXDIG HEXDIG");
        out.println("");
        out.println("path-abempty = *( \"/\" segment )");
        out.println("segment = *pchar");
        out.println("pchar = unreserved / pct-encoded / sub-delims / \":\" / \"@\"");
        out.println("query = *( pchar / \"/\" / \"?\" )");
        out.println("fragment = *( pchar / \"/\" / \"?\" )");
        out.println("unreserved = ALPHA / DIGIT / \"-\" / \".\" / \"_\" / \"~\"");
        out.println("reserved = gen-delims / sub-delims");
        out.println("gen-delims = \":\" / \"/\" / \"?\" / \"#\" / \"[\" / \"]\" / \"@\"");
        out.println("sub-delims = \"!\" / \"$\" / \"&\" / \"'\" / \"(\" / \")\" / \"*\" / \"+\" / \",\" / \";\" / \"=\"");
        out.println("");
        out.println("ALPHA =  %x41-5A / %x61-7A");
        out.println("DIGIT =  %x30-39");
        out.println("HEXDIG =  DIGIT / \"A\" / \"B\" / \"C\" / \"D\" / \"E\" / \"F\"");
    }
}
