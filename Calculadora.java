/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocalculadora;

/**
 *
 * @author rdo12
 */
public class Calculadora {
    
    //este método revisa si el carácter es operador o no
    public boolean isOperator(char a){
        boolean resp=true;
        
        switch(a){
            case'+':
            case'-':
            case'/':
            case '*':
            case'(':
            case ')':
                break;
            default:
                resp=false;
                break;
        }
        return resp;
    }
    
    public boolean isOperando(char a){
        boolean resp=true;
        
        switch(a){
            case'1':
            case'2':
            case'3':
            case'4':
            case'5':
            case'6':
            case'7':
            case'8':
            case'9':
            case'0':
            case'.':
                break;
            default:
                resp=false;
                break;
        }
        return resp;
    }
    
    //verifica parentesis
    public boolean verificaParentesis(String algo) {

        boolean resp = true;
        PilaADT<Character> pila = new Pila();

        int i = 0;

        while (i < algo.length() && resp) {
            if (algo.charAt(i) == '(') {
                pila.push(algo.charAt(i));
            } else {
                if (algo.charAt(i) == ')') {
                    if (!pila.isEmpty()) {
                        pila.pop();
                    } else {
                        resp = false;
                    }
                }
                i++;
            }
            if (resp && pila.isEmpty()) { //si estan balanceados continua
                //despues verifica que no haya operadores juntos
                i = 0;
                while (i < algo.length() && resp) {
                    if (algo.charAt(i) == '+' || algo.charAt(i) == '-' || algo.charAt(i) == '*' || algo.charAt(i) == '/' || algo.charAt(i) == '.') {
                        if (!pila.isEmpty()) {
                            resp = false;
                        } else {
                            pila.push(algo.charAt(i));
                        }
                    } else {
                        if (algo.charAt(i) != '(' && algo.charAt(i) != ')') { //ignora los parentesis
                            if (algo.charAt(i) == '0' && algo.charAt(i - 1) == '/') {
                                resp = false;
                            } else {
                                try {
                                    pila.pop();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
        if (resp && pila.isEmpty()) {
            resp = true;
        }
        return resp;
    }
    
    
    public boolean jerarquiaOperandos(char operando, char tope) {
        boolean resp = true;
        switch (operando) {
            case '+':
                if (tope == '+') {
                    resp = false;
                }
                break;
            case '*':
                if (tope != '^') {
                    resp = false;
                }
                break;
            case '/':
                if (tope != '^') {
                    resp = false;
                }
                break;
            case '^':
                resp = false;
                break;
            default:
                resp = false;
                break;
        }
        return resp;
    }
        
    
    
}
