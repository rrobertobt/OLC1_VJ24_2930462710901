package edu.robertob.olc1.vj24.Analysis;

public class AnalysisGenerator {
    public static void main(String[] args) {
        try {
            String path = "src/main/java/edu/robertob/olc1/vj24/Analysis/";
            String Flex[] = {path + "Lexer.flex", "-d", path};
            jflex.Main.generate(Flex);
            String Cup[] = {"-destdir", path, "-parser", "JCParser", path + "Parser.cup"};
            java_cup.Main.main(Cup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
