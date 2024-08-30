package edu.robertob.olc1.vj24.Analysis;

public class AnalysisGenerator {
    public static void main(String[] args) {
        try {
            // Generate the Lexer with JFlex
            String path = "src/main/java/edu/robertob/olc1/vj24/Analysis/";
            String Flex[] = {path + "Lexer.flex", "-d", path};
            jflex.Main.generate(Flex);

            // Generate the Parser with Cup
            String Cup[] = {"-destdir", path, "-parser", "JCParser", path + "Parser.cup"};
            java_cup.Main.main(Cup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
