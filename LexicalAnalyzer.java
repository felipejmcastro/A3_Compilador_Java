import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    // Definição de mapas para palavras reservadas e tabela de símbolos
    private static final Map<String, TokenType> keywords;
    private static final Map<String, String> symbolTable = new HashMap<>(); // Tabela de símbolos

    // Bloco estático para inicializar o mapa de palavras reservadas
    static {
        keywords = new HashMap<>();
        keywords.put("sera", TokenType.IF);
        keywords.put("naosera", TokenType.ELSE);
        keywords.put("durante", TokenType.WHILE);
        keywords.put("texto", TokenType.STRING);
        keywords.put("grande", TokenType.INT);

        keywords.put("for", TokenType.FOR);
        keywords.put("float", TokenType.FLOAT);
        keywords.put("double", TokenType.DOUBLE);
        keywords.put("char", TokenType.CHAR);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("return", TokenType.RETURN);
        
    }

    // Função principal
    public static void main(String[] args) {
        try {
            // Lê o arquivo de código-fonte
            BufferedReader br = new BufferedReader(new FileReader("codigo_teste.txt"));
            String line;

            // Loop para ler cada linha do arquivo
            while ((line = br.readLine()) != null) {
                
                Pattern pattern = Pattern.compile("\\w+|[(),;{}]");
                Matcher matcher = pattern.matcher(line);

                
                while (matcher.find()) {
                    String token = matcher.group();

                    if (isDelimiter(token)) {
                        System.out.println("Delimitador: " + token);
                    } else {
                        TokenType tokenType = keywords.get(token);
                        if (tokenType != null) {
                            System.out.println("Palavra reservada: " + token);
                        } else if (isIdentifier(token)) {
                            
                            if (!symbolTable.containsKey(token)) {
                                
                                String dataType = askForDataType(token);
                                symbolTable.put(token, dataType); 
                            }
                            System.out.println("Identificador: " + token + ", Tipo: " + symbolTable.get(token));
                        } else if (isIntegerLiteral(token)) {
                            System.out.println("Literal Inteiro: " + token);
                        } else if (isFloatLiteral(token)) {
                            System.out.println("Literal de Ponto Flutuante: " + token);
                        } else if (isCharLiteral(token)) {
                            System.out.println("Literal de Caractere: " + token);
                        } else if (isStringLiteral(token)) {
                            System.out.println("Literal de String: " + token);
                        } else if (isOperator(token)) {
                            System.out.println("Operador: " + token);
                        } else {
                            System.out.println("Token Desconhecido: " + token);
                        }
                    }
                }
            }
            br.close();
            System.out.println("\nTabela de Símbolos:");
            for (Map.Entry<String, String> entry : symbolTable.entrySet()) {
                System.out.println("Identificador: " + entry.getKey() + ", Tipo: " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isDelimiter(String token) {
        
        return token.equals(",") || token.equals(";") || token.equals("{") || token.equals("}");
    }

    
    private static boolean isIdentifier(String token) {
                return !keywords.containsKey(token);
    }

    
    private static boolean isIntegerLiteral(String token) {
        
        return token.matches("\\d+");
    }

    
    private static boolean isFloatLiteral(String token) {
       
        return token.matches("\\d+\\.\\d+");
    }

    
    private static boolean isCharLiteral(String token) {
        
        return token.matches("'.'");
    }

    
    private static boolean isStringLiteral(String token) {
        
        return token.matches("\".*\"");
    }

    
    private static boolean isOperator(String token) {
        
        return token.matches("[+\\-*/]");
    }

    // Função para solicitar ao usuário o tipo de dado de um identificador
    private static String askForDataType(String identifier) {
        if (isIntegerLiteral(identifier)) {
            return "int";
        } else if (isFloatLiteral(identifier)) {
            return "float";
        } else if (isCharLiteral(identifier)) {
            return "char";
        } else if (isStringLiteral(identifier)) {
            return "string";
            
        } else if (identifier.equalsIgnoreCase("true") || identifier.equalsIgnoreCase("false")) {
            return "boolean";
        } else {
            return "indefinido";
        }

    }

    
    enum TokenType {
        IF, ELSE, WHILE, FOR, INT, FLOAT, DOUBLE, CHAR, STRING, TRUE, FALSE, RETURN, IDENTIFIER
    }
}
